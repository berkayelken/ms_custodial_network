package io.github.berkayelken.custodial.network;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@EnableFeignClients
@SpringBootApplication
public class MsCustodialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCustodialNetworkApplication.class, args);
	}

	@Bean
	public Decoder decoder() {
		return new JacksonDecoder();
	}

	@Bean
	public Encoder encoder() {
		return new JacksonEncoder();
	}

}
