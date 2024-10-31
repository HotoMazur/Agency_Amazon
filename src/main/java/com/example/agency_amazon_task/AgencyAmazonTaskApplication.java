package com.example.agency_amazon_task;

import com.example.agency_amazon_task.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class AgencyAmazonTaskApplication implements CommandLineRunner {

	@Autowired
	private DataImportService dataImportService;


	public static void main(String[] args) {
		SpringApplication.run(AgencyAmazonTaskApplication.class, args);
	}

	@Override
	public void run(String... args) {
		dataImportService.importData();
	}
}
