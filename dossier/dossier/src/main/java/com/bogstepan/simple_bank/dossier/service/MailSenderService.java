package com.bogstepan.simple_bank.dossier.service;

import com.bogstepan.simple_bank.calculator_client.dto.EmailMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendFinishRegistrationMail(EmailMessageDto emailMessageDto) {
        var text = MailTemplateReader.readEmailTemplateFromResources("templates/finish_registration.txt");
        sendMail(emailMessageDto.getAddress(),
                emailMessageDto.getTheme().toString(),
                text);
    }
}
