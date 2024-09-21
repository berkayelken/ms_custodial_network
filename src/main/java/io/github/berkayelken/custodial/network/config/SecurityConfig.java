package io.github.berkayelken.custodial.network.config;

import io.github.berkayelken.custodial.network.auth.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
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
		return http.cors(ServerHttpSecurity.CorsSpec::disable).csrf(ServerHttpSecurity.CsrfSpec::disable)
				.addFilterAt(tokenFilter, AUTHENTICATION).build();
	}

	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(List.of("*"));
		corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
		corsConfig.setAllowedHeaders(List.of("*"));
		corsConfig.setMaxAge(8000L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsWebFilter(source);
	}
}