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

    private String transliterate(String text) {
        Transliterator toCyrillicTrans = Transliterator.getInstance(LATIN_TO_CYRILLIC);
        var rsl = toCyrillicTrans.transliterate(text);
        if (!rsl.isEmpty()) {
            rsl = Character.toUpperCase(rsl.charAt(0)) + rsl.substring(1);
        }
        return rsl;
    }

    public void createLoanDocuments(String statementId) {
        var statement = dealFeignClient.getStatement(statementId);
        try (InputStream fileInputStream = ClassLoader.getSystemResourceAsStream("dossier/src/main/resources/templates/loan_documents_template.docx");
             OutputStream out = new FileOutputStream("tmp/loan_documents/loan_" + statementId + ".docx")) {
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
            report.process(context, out);
            log.info("Loan documents for statement with id {} was created", statementId);
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
        }
    }
}
