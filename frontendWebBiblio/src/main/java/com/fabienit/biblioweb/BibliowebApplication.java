package com.fabienit.biblioweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BibliowebApplication {

	private static final Logger logger = LoggerFactory.getLogger(BibliowebApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BibliowebApplication.class, args);
	}

}
