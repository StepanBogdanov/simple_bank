package com.bogstepan.simple_bank.dossier.service;

import com.bogstepan.simple_bank.clients.dto.EmailMessageDto;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final DealFeignClient dealFeignClient;
    private final TransliterateService transliterateService;

    public void sendMail(String to, String subject, String text, String attachmentFilePath) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            if (attachmentFilePath != null && Files.exists(Paths.get(attachmentFilePath))) {
                helper.addAttachment("yourLoanDocuments.docx", new FileDataSource(attachmentFilePath));
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendFinishRegistrationMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterateService.transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterateService.transliterate(statement.getClient().getLastName()));
        var html = templateEngine.process("finish_registration_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                null);
    }

    public void sendCreateDocumentsMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterateService.transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterateService.transliterate(statement.getClient().getLastName()));
        context.setVariable("statementId", emailMessageDto.getStatementId());
        var html = templateEngine.process("create_documents_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                null);
    }

    public void sendLoanDocumentsMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterateService.transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterateService.transliterate(statement.getClient().getLastName()));
        context.setVariable("statementId", emailMessageDto.getStatementId());
        var html = templateEngine.process("loan_documents_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                "dossier/src/main/resources/loan_documents/loan_" + emailMessageDto.getStatementId() + ".docx");
    }

    public void sendSignDocumentsMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterateService.transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterateService.transliterate(statement.getClient().getLastName()));
        context.setVariable("sesCode", statement.getSesCode());
        context.setVariable("statementId", emailMessageDto.getStatementId());
        var html = templateEngine.process("loan_documents_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                null);
    }

    public void sendCreditIssuedMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterateService.transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterateService.transliterate(statement.getClient().getLastName()));
        var html = templateEngine.process("credit_issued_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                null);
    }
}
