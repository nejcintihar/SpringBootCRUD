package com.dev.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Product application.
 * Serves as the entry point for running the Spring Boot application.
 */
@SpringBootApplication
public class ProductApplication {

	/**
	 * The main method that starts the Spring Boot application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}