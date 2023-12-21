package com.fabienit.flyingclub.config;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FeignConfig
 */
@Configuration
public class FeignConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("fabien", "123456");
    }



        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

}