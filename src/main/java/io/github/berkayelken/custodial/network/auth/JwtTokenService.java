package io.github.berkayelken.custodial.network.auth;

import io.github.berkayelken.custodial.network.domain.auth.UserAuthenticationResponse;
import io.github.berkayelken.custodial.network.domain.auth.UserType;
import io.github.berkayelken.custodial.network.exception.InvalidAuthenticationTokenException;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JwtTokenService implements AuthTokenService {
	private static final String AUTHORITIES_KEY = "roles";

	private final JwtTokenProvider jwtTokenProvider;

	public JwtTokenService(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public UserAuthenticationResponse doAuthentication(String token) {
		checkAuthentication(token);

		Claims claims = jwtTokenProvider.getClaims(token);
		String role = claims.get(AUTHORITIES_KEY, String.class);

		return UserAuthenticationResponse.builder().role(UserType.findUserType(role)).email(claims.getSubject()).build();
	}

	private void checkAuthentication(String token) {
		checkTokenValidation(token);
		checkAuthenticationContext(token);
	}

	private void checkAuthenticationContext(String token) {
		Authentication auth = jwtTokenProvider.getAuthentication(token);

		if (auth == null)
			throw new InvalidAuthenticationTokenException("Authentication context is invalid", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private void checkTokenValidation(String token) {
		checkTokenNullity(token);
		if (!jwtTokenProvider.validateToken(token))
			throw new InvalidAuthenticationTokenException("Token is invalid.", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
	}

	private void checkTokenNullity(String token) {
		if (!StringUtils.hasText(token))
			throw new InvalidAuthenticationTokenException("Token is not found. Request is required auth-token.",
					HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
	}
}
