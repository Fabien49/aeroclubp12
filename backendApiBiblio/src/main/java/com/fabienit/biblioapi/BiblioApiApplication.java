package com.fabienit.biblioapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BiblioApiApplication implements CommandLineRunner{

	@Value( "${biblio.mode}" )
	private String biblioMode;

	public static void main(String[] args) {

		SpringApplication.run(BiblioApiApplication.class, args);
	}





	public void run(String... args) throws Exception{
		System.out.println("The mode is : *****************" + biblioMode);
		}



}
