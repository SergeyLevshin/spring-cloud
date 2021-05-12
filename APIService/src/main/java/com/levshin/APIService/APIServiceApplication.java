package com.levshin.APIService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.retry.annotation.EnableRetry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableRetry
@EnableDiscoveryClient
@EnableSwagger2
@SpringBootApplication
public class APIServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(APIServiceApplication.class, args);
	}

}
