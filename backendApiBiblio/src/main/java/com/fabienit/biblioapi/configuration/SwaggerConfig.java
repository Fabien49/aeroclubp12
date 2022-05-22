package com.fabienit.biblioapi.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Set displayed data by the micro service documentation
     * @return
     */
    @Bean
    public Docket api() {

        logger.debug("Set Swagger2 documentation");

        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.fabienit.biblioapi.web.controllers"))
            .paths(PathSelectors.any())
            .build();
        
    }    
}