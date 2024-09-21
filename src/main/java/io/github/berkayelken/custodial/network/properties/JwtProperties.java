package io.github.berkayelken.custodial.network.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Getter
@Setter
@Configuration
@ConfigurationProperties (prefix = "jwt")
public class JwtProperties {
	private String secretKey;
	private long validityInMs;

	public long createAndGetExpireAt() {
		return Instant.now().plusMillis(validityInMs).toEpochMilli();
	}
}
