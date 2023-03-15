package com.project.cricket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class CricketApplication {

	public static void main(String[] args) {
		System.out.println("Main class hit");
		SpringApplication.run(CricketApplication.class, args);
//		System.out.prntln(Arrays.asList(applicationContext.getBeanDefinitionNames()));
	}

}
