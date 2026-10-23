package com.api.Fitzone;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class FitzoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitzoneApplication.class, args);
	}

}
