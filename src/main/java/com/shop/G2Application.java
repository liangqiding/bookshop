package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass=true)
@SpringBootApplication
public class G2Application {

	public static void main(String[] args) {
		SpringApplication.run(G2Application.class, args);
	}

}
