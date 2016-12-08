package com.brianthi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class DiscoverSpApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoverSpApplication.class, args);
	}
}
