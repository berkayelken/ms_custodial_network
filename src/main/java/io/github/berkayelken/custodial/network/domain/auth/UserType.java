package io.github.berkayelken.custodial.network.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserType {
	COLLECTOR("ROLE_COLLECTOR", 1),
	ARTIST("ROLE_ARTIST", 3);

	private final String authorityRole;
	private final int validOperations;

	public static UserType findUserType(String authorityRole) {
		return Arrays.stream(UserType.values()).filter(type -> authorityRole.equalsIgnoreCase(type.authorityRole)).findFirst()
				.orElse(COLLECTOR);
	}

	public static UserType findUserType(int validOperations) {
		return Arrays.stream(UserType.values()).filter(type -> validOperations == type.validOperations).findFirst()
				.orElse(COLLECTOR);
	}

}
