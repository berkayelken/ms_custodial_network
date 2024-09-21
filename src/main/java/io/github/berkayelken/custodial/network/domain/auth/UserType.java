package io.github.berkayelken.custodial.network.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
	COLLECTOR("ROLE_COLLECTOR",1),
	ARTIST("ROLE_ARTIST",3);

	private final String authorityRole;
	private final int validOperations;
}
