package com.expense.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJwtApplication.class, args);
	}

	// Configure CORS globally
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Allow all endpoints
						.allowedOrigins("http://localhost:4200") // Replace with your frontend's URL
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
						.allowedHeaders("*") // Allow all headers
						.allowCredentials(true); // Allow cookies or credentials
			}
		};

	}
}
