package io.github.berkayelken.custodial.network.auth;

import io.github.berkayelken.custodial.network.domain.UserEntityTest;
import io.github.berkayelken.custodial.network.domain.auth.UserType;
import io.github.berkayelken.custodial.network.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@ExtendWith (MockitoExtension.class)
public class JwtTokenProviderTest {
	private static final String AUTHORITIES_KEY = "roles";
	private static final String TEST_STR = UUID.randomUUID().toString();
	private static final Authentication TEST_AUTH = createDefaultAuth();

	@Mock
	private JwtProperties properties;

	private JwtTokenProvider createProvider() {
		Mockito.when(properties.getSecretKey()).thenReturn(TEST_STR);
		return new JwtTokenProvider(properties);
	}

	@Test
	public void testCreateToken() {
		JwtTokenProvider provider = createProvider();

		Assertions.assertNotNull(provider.createToken(TEST_AUTH, UserEntityTest.createInstance()));
	}

	@Test
	public void testGetAuthentication() {
		JwtTokenProvider provider = createProvider();

		Assertions.assertNotNull(provider.getAuthentication(createToken()));
	}

	@Test
	public void testGetClaims() {
		JwtTokenProvider provider = createProvider();

		Assertions.assertNotNull(provider.getClaims(createToken()));
	}

	@Test
	public void testValidateToken() {
		JwtTokenProvider provider = createProvider();

		Assertions.assertTrue(provider.validateToken(createToken()));
	}

	private static Authentication createDefaultAuth() {
		Object authoritiesClaim = UserType.ARTIST.getAuthorityRole();

		String token = createToken();

		Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null
				? AuthorityUtils.NO_AUTHORITIES
				: AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

		return new UsernamePasswordAuthenticationToken(TEST_STR, token, authorities);
	}

	private static SecretKey createDefaultSecretKey() {
		String secret = Base64.getEncoder().encodeToString(TEST_STR.getBytes());
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	private static String createToken() {
		Claims claims = Jwts.claims().setSubject(TEST_STR).setId(TEST_STR);

		claims.put(AUTHORITIES_KEY, UserType.ARTIST.getAuthorityRole());

		return Jwts.builder().setClaims(claims).setIssuedAt(new Date())
				.setExpiration(Date.from(Instant.now().plusSeconds(100000)))
				.signWith(createDefaultSecretKey(), SignatureAlgorithm.HS256).compact();
	}
}
