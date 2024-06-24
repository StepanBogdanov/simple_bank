package com.bogstepan.simple_bank.calculator_client.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class Props {

    private static Props instance = new Props();
    private Properties properties = new Properties();

    private Props() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                properties.load(in);
            }
        } catch (IOException e) {
        }
    }

    public static String getProperty(String key) {
        return instance.properties.getProperty(key);
    }

}
