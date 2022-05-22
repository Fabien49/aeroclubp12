package com.fabienit.biblioweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;


/**
 * WebConfig
 */
@Configuration
public class WebConfig {

    /**
     * Set message source to display by thymleaf in html pages, set source message.properties file src/main/resources folder
     */
    @Bean
    @Description("Spring Message Resolver")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

}