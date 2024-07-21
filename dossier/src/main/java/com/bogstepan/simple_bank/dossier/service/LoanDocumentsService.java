package com.bogstepan.simple_bank.dossier.service;

import com.bogstepan.simple_bank.dossier.exception.RequestException;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import com.ibm.icu.text.Transliterator;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanDocumentsService {

    private final static String LATIN_TO_CYRILLIC = "Latin-Russian/BGN";

    private final DealFeignClient dealFeignClient;

    protected String transliterate(String text) {
        Transliterator toCyrillicTrans = Transliterator.getInstance(LATIN_TO_CYRILLIC);
        var rsl = toCyrillicTrans.transliterate(text);
        if (!rsl.isEmpty()) {
            rsl = Character.toUpperCase(rsl.charAt(0)) + rsl.substring(1);
        }
        return rsl;
    }

    public File createLoanDocuments(String statementId) {
        var statement = dealFeignClient.getStatement(statementId);
        File tempFile = null;
        try (InputStream fileInputStream = ClassLoader.getSystemResourceAsStream("templates/loan_documents_template.docx")) {
            tempFile = File.createTempFile("loan_" + statementId, ".docx");
            try (OutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                IXDocReport report = XDocReportRegistry.getRegistry().loadReport(fileInputStream, TemplateEngineKind.Freemarker);
                IContext context = report.createContext();
                FieldsMetadata metadata = report.createFieldsMetadata();

                Map<String, Object> data = new HashMap<>();
                data.put("statementId", statementId);
                data.put("statementCreationDate", statement.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                data.put("firstName", transliterate(statement.getClient().getFirstName()));
                data.put("lastName", transliterate(statement.getClient().getLastName()));
                data.put("middleName", transliterate(statement.getClient().getMiddleName()));
                data.put("amount", statement.getCredit().getAmount());
                data.put("term", statement.getCredit().getTerm());
                data.put("accountNumber", statement.getClient().getAccountNumber());
                data.put("rate", statement.getCredit().getRate());
                data.put("passportSeries", statement.getClient().getPassport().getSeries());
                data.put("passportNumber", statement.getClient().getPassport().getNumber());
                data.put("passportIssueDate", statement.getClient().getPassport().getIssueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                data.put("passportIssueBranch", transliterate(statement.getClient().getPassport().getIssueBranch()));
                data.put("payments", statement.getCredit().getPaymentSchedule());

                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    metadata.addFieldAsTextStyling(entry.getKey(), "none");
                    context.put(entry.getKey(), entry.getValue());
                }
                report.process(context, fileOutputStream);
                log.info("Loan documents for statement with id {} was created", statementId);
            }
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
        }
        return tempFile;
    }
}
