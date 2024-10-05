package io.github.berkayelken.custodial.network.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserType {
	PLAIN_MEMBER("ROLE_PLAIN_MEMBER", 0),
	COLLECTOR("ROLE_COLLECTOR", 1),
	ARTIST("ROLE_ARTIST", 2),
	GALLERIST("ROLE_GALLERIST", 4);

	private final String authorityRole;
	private final int validOperations;

	public static UserType findUserType(String authorityRole) {
		return Arrays.stream(UserType.values()).filter(type -> authorityRole.equalsIgnoreCase(type.authorityRole)).findFirst()
				.orElse(PLAIN_MEMBER);
	}

	public static UserType findUserType(int validOperations) {
		return Arrays.stream(UserType.values()).filter(type -> validOperations == type.validOperations).findFirst()
				.orElse(PLAIN_MEMBER);
	}

	public static boolean isArtistType(int validOperations) {
		UserType type = findUserType(validOperations);
		return ARTIST == type || GALLERIST == type;
	}

}
