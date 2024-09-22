package io.github.berkayelken.custodial.network.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("cross-mint")
public class CrossMintProperties {
	private String apiKey;
	private String appIdentifier;
	private String appCollection;
}
