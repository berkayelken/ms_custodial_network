package io.github.berkayelken.custodial.network.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTest {
	@Mock
	private JwtTokenProvider provider;

	private AuthTokenService createService() {
		return new JwtTokenService(provider);
	}

	@Test
	public void testDoAuthentication() {
		AuthTokenService service = createService();

		Mockito.when(provider.getAuthentication(ArgumentMatchers.anyString())).thenReturn(JwtTokenProviderTest.TEST_AUTH);
		Mockito.when(provider.validateToken(ArgumentMatchers.anyString())).thenReturn(true);
		Mockito.when(provider.getClaims(ArgumentMatchers.anyString())).thenReturn(JwtTokenProviderTest.createClaims());
		Assertions.assertNotNull(service.doAuthentication(JwtTokenProviderTest.createToken()));
	}
}
