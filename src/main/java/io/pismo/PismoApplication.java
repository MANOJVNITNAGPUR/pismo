package io.pismo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = {"io.pismo"})
public class PismoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PismoApplication.class, args);
	}

}
