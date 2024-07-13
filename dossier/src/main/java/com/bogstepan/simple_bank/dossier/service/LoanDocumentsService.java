package com.bogstepan.simple_bank.dossier.service;

import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoanDocumentsService {

    private final DealFeignClient dealFeignClient;
    private final TransliterateService transliterateService;

    public void createLoanDocuments(String statementId) {
        var statement = dealFeignClient.getStatement(statementId);
        try (FileInputStream fileInputStream = new FileInputStream("dossier/src/main/resources/templates/loan_documents_template.docx");
             OutputStream out = new FileOutputStream("dossier/src/main/resources/loan_documents/loan_" + statementId + ".docx")) {
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(fileInputStream, TemplateEngineKind.Freemarker);
            IContext context = report.createContext();
            FieldsMetadata metadata = report.createFieldsMetadata();

            Map<String, Object> data = new HashMap<>();
            data.put("statementId", statementId);
            data.put("statementCreationDate", statement.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            data.put("firstName", transliterateService.transliterate(statement.getClient().getFirstName()));
            data.put("lastName", transliterateService.transliterate(statement.getClient().getLastName()));
            data.put("middleName", transliterateService.transliterate(statement.getClient().getMiddleName()));
            data.put("amount", statement.getCredit().getAmount());
            data.put("term", statement.getCredit().getTerm());
            data.put("accountNumber", statement.getClient().getAccountNumber());
            data.put("rate", statement.getCredit().getRate());
            data.put("passportSeries", statement.getClient().getPassport().getSeries());
            data.put("passportNumber", statement.getClient().getPassport().getNumber());
            data.put("passportIssueDate", statement.getClient().getPassport().getIssueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            data.put("passportIssueBranch", transliterateService.transliterate(statement.getClient().getPassport().getIssueBranch()));
            data.put("payments", statement.getCredit().getPaymentSchedule());

            for (Map.Entry<String, Object> entry : data.entrySet()) {
                metadata.addFieldAsTextStyling(entry.getKey(), "none");
                context.put(entry.getKey(), entry.getValue());
            }
            report.process(context, out);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
