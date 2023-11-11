package com.example.BaseProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BaseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseProjectApplication.class, args);
	}

}
