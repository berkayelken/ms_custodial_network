package io.github.berkayelken.custodial.network.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
	private String email;
	private String name;
	private String surname;
	private String location;
	private boolean artist;
	private int order;
	private Map<String, String> socialAccounts;
	private Map<String, Boolean> interests;
	private String wallet;
	private boolean registrationCompleted;
	private String token;
	private long expireAt;

}
