package io.github.berkayelken.custodial.network.auth;

import io.github.berkayelken.custodial.network.domain.auth.UserAuthenticationResponse;

public interface AuthTokenService {
	UserAuthenticationResponse doAuthentication(String token);
}
