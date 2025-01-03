package com.br.alumind;

import com.br.alumind.services.DataLoaderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlumindApplication {
	public static void main(String[] args) {
		DataLoaderService dataLoaderService = new DataLoaderService();
		//dataLoaderService.load();
		SpringApplication.run(AlumindApplication.class, args);
	}

}
