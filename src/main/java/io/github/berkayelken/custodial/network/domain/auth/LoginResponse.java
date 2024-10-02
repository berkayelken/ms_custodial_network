package io.github.berkayelken.custodial.network.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
	private String email;
	private String token;
	private long expireAt;
	private String wallet;
	private boolean artist;
	private int order;
}
