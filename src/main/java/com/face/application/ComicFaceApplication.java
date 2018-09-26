package com.face.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.face.dao")
@EntityScan("com.face.entity")
@ComponentScan("com.face")
public class ComicFaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComicFaceApplication.class, args);
	}
}
