package com.bogstepan.simple_bank.dossier.service;

import com.bogstepan.simple_bank.clients.dto.EmailMessageDto;
import com.bogstepan.simple_bank.dossier.exception.RequestException;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import com.ibm.icu.text.Transliterator;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MailSenderService {

    private final static String LATIN_TO_CYRILLIC = "Latin-Russian/BGN";

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final DealFeignClient dealFeignClient;

    private String transliterate(String text) {
        Transliterator toCyrillicTrans = Transliterator.getInstance(LATIN_TO_CYRILLIC);
        var rsl = toCyrillicTrans.transliterate(text);
        if (!rsl.isEmpty()) {
            rsl = Character.toUpperCase(rsl.charAt(0)) + rsl.substring(1);
        }
        return rsl;
    }

    private void sendMail(String to, String subject, String text, String attachmentFilePath) {
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
            throw new RequestException(e.getMessage());
        }
    }

    private Context prepareContext(String statementId) {
        var statement = dealFeignClient.getStatement(statementId);
        var context = new Context();
        context.setVariable("name", transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterate(statement.getClient().getLastName()));
        context.setVariable("statementId", statementId);
        context.setVariable("sesCode", statement.getSesCode());
        return context;
    }

    public void sendFinishRegistrationMail(EmailMessageDto emailMessageDto) {
        var context = prepareContext(emailMessageDto.getStatementId());
        var html = templateEngine.process("finish_registration_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                null);
        log.info("Finish registration mail for statement with id {} was sent to client",
                emailMessageDto.getStatementId());
    }

    public void sendCreateDocumentsMail(EmailMessageDto emailMessageDto) {
        var context = prepareContext(emailMessageDto.getStatementId());
        var html = templateEngine.process("create_documents_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                null);
        log.info("Create documents mail for statement with id {} was sent to client",
                emailMessageDto.getStatementId());
    }

    public void sendLoanDocumentsMail(EmailMessageDto emailMessageDto) {
        var context = prepareContext(emailMessageDto.getStatementId());
        var html = templateEngine.process("loan_documents_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                "tmp/loan_documents/loan_" + emailMessageDto.getStatementId() + ".docx");
        log.info("Your loan documents mail for statement with id {} was sent to client",
                emailMessageDto.getStatementId());
    }

    public void sendSignDocumentsMail(EmailMessageDto emailMessageDto) {
        var context = prepareContext(emailMessageDto.getStatementId());
        var html = templateEngine.process("ses_code_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                null);
        log.info("Sign documents with ses code mail for statement with id {} was sent to client",
                emailMessageDto.getStatementId());
    }

    public void sendCreditIssuedMail(EmailMessageDto emailMessageDto) {
        var context = prepareContext(emailMessageDto.getStatementId());
        var html = templateEngine.process("credit_issued_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                null);
        log.info("Credit issued mail for statement with id {} was sent to client",
                emailMessageDto.getStatementId());
    }

    public void sendStatementDeniedMail(EmailMessageDto emailMessageDto) {
        var context = prepareContext(emailMessageDto.getStatementId());
        var html = templateEngine.process("statement_denied_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html,
                null);
        log.info("Statement denied mail for statement with id {} was sent to client",
                emailMessageDto.getStatementId());
    }
}
