package io.github.berkayelken.custodial.network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsCustodialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCustodialNetworkApplication.class, args);
	}

}
