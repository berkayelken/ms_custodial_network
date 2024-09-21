package io.github.berkayelken.custodial.network.auth;

import io.github.berkayelken.custodial.network.domain.UserEntity;
import io.github.berkayelken.custodial.network.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import static java.util.stream.Collectors.joining;

@Service
public class JwtTokenProvider {
	private static final String AUTHORITIES_KEY = "roles";

	private final JwtProperties properties;
	private final SecretKey secretKey;

	public JwtTokenProvider(JwtProperties properties) {
		this.properties = properties;
		String secret = Base64.getEncoder().encodeToString(properties.getSecretKey().getBytes());
		secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String createToken(Authentication authentication, UserEntity user) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Claims claims = Jwts.claims().setSubject(user.getEmail()).setId(user.getId());

		if (!authorities.isEmpty()) {
			claims.put(AUTHORITIES_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
		}

		return Jwts.builder().setClaims(claims).setIssuedAt(new Date()).setExpiration(properties.createAndGetExpireAtDate())
				.signWith(secretKey, SignatureAlgorithm.HS256).compact();

	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

		Object authoritiesClaim = claims.get(AUTHORITIES_KEY);

		String email = claims.get("sub", String.class);
		Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null
				? AuthorityUtils.NO_AUTHORITIES
				: AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

		return new UsernamePasswordAuthenticationToken(email, token, authorities);
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		} catch (Throwable t) {
			return false;
		}

	}

}
