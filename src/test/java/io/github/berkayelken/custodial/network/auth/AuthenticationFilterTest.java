package io.github.berkayelken.custodial.network.auth;

import io.github.berkayelken.custodial.network.domain.auth.UserAuthenticationResponse;
import io.github.berkayelken.custodial.network.domain.auth.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import java.net.URI;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AuthenticationFilterTest {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TEST_STR = UUID.randomUUID().toString();
	private static final String AUTHORIZATION_VALUE = "Bearer " + TEST_STR;

	@Mock
	private AuthTokenService tokenService;
	@Mock
	private WebFilterChain chain;
	@Mock
	private ServerWebExchange exchange;
	@Mock
	private ServerHttpRequest httpRequest;
	@Mock
	private ServerHttpRequest.Builder requestBuilder;
	@Mock
	private ServerWebExchange.Builder exchangeBuilder;

	private AuthenticationFilter createFilter() {
		return new AuthenticationFilter(tokenService);
	}

	@Test
	public void testFilter() {
		AuthenticationFilter filter = createFilter();

		Mockito.when(tokenService.doAuthentication(ArgumentMatchers.anyString())).thenReturn(UserAuthenticationResponse.builder().email(TEST_STR).role(
				UserType.ARTIST).build());
		Mockito.when(exchange.getRequest()).thenReturn(httpRequest);
		Mockito.when(httpRequest.getURI()).thenReturn(URI.create("/api/artist/test"));
		Mockito.when(httpRequest.mutate()).thenReturn(requestBuilder);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION_HEADER, AUTHORIZATION_VALUE);
		Mockito.when(httpRequest.getHeaders()).thenReturn(httpHeaders);
		Mockito.when(requestBuilder.header(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(requestBuilder);
		Mockito.when(requestBuilder.build()).thenReturn(httpRequest);
		Mockito.when(exchange.mutate()).thenReturn(exchangeBuilder);
		Mockito.when(exchangeBuilder.request(ArgumentMatchers.any(ServerHttpRequest.class))).thenReturn(exchangeBuilder);

		Assertions.assertDoesNotThrow(() -> filter.filter(exchange, chain));
	}
}
