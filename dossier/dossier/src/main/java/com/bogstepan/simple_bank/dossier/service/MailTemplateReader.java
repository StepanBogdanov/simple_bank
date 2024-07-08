package com.bogstepan.simple_bank.dossier.service;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MailTemplateReader {

    public static String readEmailTemplateFromResources(String templateFileName) {
        try (InputStream inputStream = MailTemplateReader.class.getClassLoader().getResourceAsStream(templateFileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Email template file not found: " + templateFileName);
            }
            byte[] bytes = inputStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading email template file: " + templateFileName, e);
        }
    }
}
