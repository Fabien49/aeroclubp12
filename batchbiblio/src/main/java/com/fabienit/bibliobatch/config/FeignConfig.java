package com.fabienit.bibliobatch.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FeignConfig
 */
@Configuration
public class FeignConfig {

    /**
     * Send username and password to API for Basic Auth for each request
     * 
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("fabien", "123456");
    }
    
}