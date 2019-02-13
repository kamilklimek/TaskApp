package com.java.ee.task.config;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailValidatorConfiguration {

    @Bean
    public EmailValidator getEmailValidator() {
        return EmailValidator.getInstance(EmailValidatorMode.NO_ALLOW_LOCAL.getMode());
    }
}
