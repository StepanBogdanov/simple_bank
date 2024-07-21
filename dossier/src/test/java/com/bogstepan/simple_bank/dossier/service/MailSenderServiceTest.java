package com.bogstepan.simple_bank.dossier.service;

import com.bogstepan.simple_bank.clients.dto.*;
import com.bogstepan.simple_bank.clients.enums.Theme;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MailSenderServiceTest {

    @Mock
    JavaMailSender javaMailSender;
    @Mock
    TemplateEngine templateEngine;
    @Mock
    DealFeignClient dealFeignClient;

    @InjectMocks
    MailSenderService mailSenderService;

    @Test
    public void sendFinishRegistrationMail() {
        EmailMessageDto messageDto = new EmailMessageDto(
                "test@test.com",
                Theme.FINISH_REGISTRATION,
                UUID.randomUUID().toString()
        );
        Mockito.when(dealFeignClient.getStatement(messageDto.getStatementId())).thenReturn(new StatementDto(
                new ClientDto("Ivan", "Ivanov", null, null, null,null, null, 0, null, null, null),
                null, null, null, null, "123456"
        ));
        Mockito.when(templateEngine.process(eq("finish_registration_email"), any(Context.class))).thenReturn("htmlContent");
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        mailSenderService.setFrom("from@test.ru");

        mailSenderService.sendFinishRegistrationMail(messageDto);

        Mockito.verify(dealFeignClient, times(1)).getStatement(messageDto.getStatementId());
        Mockito.verify(templateEngine, times(1)).process(eq("finish_registration_email"), any(Context.class));
        Mockito.verify(javaMailSender, times(1)).send(any(MimeMessage.class));

    }

}