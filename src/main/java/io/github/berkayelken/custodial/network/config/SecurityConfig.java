package io.github.berkayelken.custodial.network.config;

import io.github.berkayelken.custodial.network.auth.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.List;

import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHENTICATION;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
class SecurityConfig implements WebFluxConfigurer {
	@Bean
	public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http, AuthenticationFilter tokenFilter) {
		return http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(ServerHttpSecurity.CsrfSpec::disable)
				.addFilterAt(tokenFilter, AUTHENTICATION).build();
	}

	private CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOriginPattern("*");
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT",
				"DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setMaxAge(3600L);
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}