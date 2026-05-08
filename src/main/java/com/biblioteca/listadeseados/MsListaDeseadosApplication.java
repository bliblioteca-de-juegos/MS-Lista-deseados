package com.biblioteca.listadeseados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsListaDeseadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsListaDeseadosApplication.class, args);
	}

}
