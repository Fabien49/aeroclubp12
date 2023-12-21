package com.fabienit.flyingclub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AeroclubwebApplication {

	private static final Logger logger = LoggerFactory.getLogger(AeroclubwebApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AeroclubwebApplication.class, args);
	}

}
