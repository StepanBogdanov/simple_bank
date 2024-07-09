package com.bogstepan.simple_bank.dossier.service;

import com.bogstepan.simple_bank.calculator_client.dto.EmailMessageDto;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import com.ibm.icu.text.Transliterator;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final static String LATIN_TO_CYRILLIC = "Latin-Russian/BGN";

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final DealFeignClient dealFeignClient;

    public void sendMail(String to, String subject, String text) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String transliterate(String text) {
        Transliterator toCyrillicTrans = Transliterator.getInstance(LATIN_TO_CYRILLIC);
        var rsl = toCyrillicTrans.transliterate(text);
        rsl = Character.toUpperCase(rsl.charAt(0)) + rsl.substring(1);
        return rsl;
    }

    public void sendFinishRegistrationMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterate(statement.getClient().getLastName()));
        var html = templateEngine.process("finish_registration_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html);
    }

    public void sendCreateDocumentsMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterate(statement.getClient().getLastName()));
        context.setVariable("statementId", emailMessageDto.getStatementId());
        var html = templateEngine.process("create_documents_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html);
    }

    public void sendLoanDocumentsMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterate(statement.getClient().getLastName()));
        context.setVariable("statementId", emailMessageDto.getStatementId());
        var html = templateEngine.process("loan_documents_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html);
    }

    public void sendSignDocumentsMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterate(statement.getClient().getLastName()));
        context.setVariable("sesCode", statement.getSesCode());
        context.setVariable("statementId", emailMessageDto.getStatementId());
        var html = templateEngine.process("loan_documents_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html);
    }

    public void sendCreditIssuedMail(EmailMessageDto emailMessageDto) {
        var statement = dealFeignClient.getStatement(emailMessageDto.getStatementId());
        var context = new Context();
        context.setVariable("name", transliterate(statement.getClient().getFirstName()));
        context.setVariable("surname", transliterate(statement.getClient().getLastName()));
        var html = templateEngine.process("credit_issued_email", context);
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                html);
    }
}
