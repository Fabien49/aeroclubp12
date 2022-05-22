package com.fabienit.bibliobatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringConfig
 */
@Configuration
@EnableScheduling
public class SpringConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

    
}