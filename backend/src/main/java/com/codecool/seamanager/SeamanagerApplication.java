package com.codecool.seamanager;

import com.codecool.seamanager.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SeamanagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SeamanagerApplication.class, args);
	}
}
