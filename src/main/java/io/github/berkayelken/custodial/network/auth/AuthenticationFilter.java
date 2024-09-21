package io.github.berkayelken.custodial.network.auth;

import io.github.berkayelken.custodial.network.domain.auth.UserAuthenticationResponse;
import io.github.berkayelken.custodial.network.domain.auth.UserType;
import io.github.berkayelken.custodial.network.exception.InvalidAuthenticationTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AuthenticationFilter implements WebFilter {
	private static final String AUTHORIZATION_DELIMITER = ":";
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTH_HEADER_PREFIX = "Basic ";
	private static final String HEADER_PREFIX = "Bearer ";
	private static final String EMAIL_HEADER = "email";
	private static final String PASSWORD_HEADER = "password";

	private static final String AUTH_ENDPOINT_PREFIX = "/api/auth";
	private static final String ARTIST_ENDPOINT_PREFIX = "/api/artist";

	private final AuthTokenService tokenService;

	public AuthenticationFilter(AuthTokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String requestPath = exchange.getRequest().getURI().getPath();
		if (requestPath.startsWith(AUTH_ENDPOINT_PREFIX)) {
			String[] userInfo = checkAndGetAuthorizationEndpoint(exchange);

			return chain.filter(exchange.mutate().request(
							exchange.getRequest().mutate().header(EMAIL_HEADER, userInfo[0]).header(PASSWORD_HEADER, userInfo[1]).build())
					.build());
		}

		UserAuthenticationResponse response = checkAndGetEndpointAuthentication(exchange);

		return chain.filter(
				exchange.mutate().request(exchange.getRequest().mutate().header(EMAIL_HEADER, response.getEmail()).build())
						.build());
	}

	private UserAuthenticationResponse checkAndGetEndpointAuthentication(ServerWebExchange exchange) {
		String token = resolveToken(exchange);
		UserAuthenticationResponse response = tokenService.doAuthentication(token);

		if (!StringUtils.hasText(response.getEmail())) {
			throw new InvalidAuthenticationTokenException("Authentication context is invalid",
					HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}

		String requestPath = exchange.getRequest().getURI().getPath();
		if (requestPath.startsWith(ARTIST_ENDPOINT_PREFIX) && response.getRole() != UserType.ARTIST) {
			throw new InvalidAuthenticationTokenException("Authentication context is invalid",
					HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}

		return response;
	}

	private String[] checkAndGetAuthorizationEndpoint(ServerWebExchange exchange) {
		String token = resolveToken(exchange, AUTH_HEADER_PREFIX);
		byte[] decodedTokenArr = Base64.getDecoder().decode(token);
		String decodedToken = new String(decodedTokenArr, StandardCharsets.UTF_8);
		if (!decodedToken.contains(AUTHORIZATION_DELIMITER)) {
			throw new InvalidAuthenticationTokenException("Authentication context is invalid",
					HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}

		return decodedToken.split(AUTHORIZATION_DELIMITER);
	}

	private String resolveToken(ServerWebExchange exchange) {
		return resolveToken(exchange, HEADER_PREFIX);
	}

	private String resolveToken(ServerWebExchange exchange, String headerPrefix) {
		String token = exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER).get(0);

		if (StringUtils.hasText(token) && token.startsWith(headerPrefix)) {
			return token.substring(headerPrefix.length());
		}

		throw new InvalidAuthenticationTokenException("Header parameter auth-token cannot be " + "found or resolved as Bearer.",
				HttpStatus.BAD_REQUEST);
	}
}
