package io.github.berkayelken.custodial.network.domain.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserAuthenticationResponseTest {
	private static final String TEST_STR = UUID.randomUUID().toString();

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new UserAuthenticationResponse());
	}

	@Test
	public void testAllArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new UserAuthenticationResponse(UserType.ARTIST, TEST_STR));
	}

	@Test
	public void testBuilder() {
		Assertions.assertNotNull(UserAuthenticationResponse.builder().build());
	}

	@Test
	public void testAccessors() {
		UserAuthenticationResponse response = new UserAuthenticationResponse();

		Assertions.assertNull(response.getRole());
		Assertions.assertNull(response.getEmail());
	}

	@Test
	public void testMutators() {
		UserAuthenticationResponse response = new UserAuthenticationResponse();

		response.setEmail(TEST_STR);
		response.setRole(UserType.COLLECTOR);

		Assertions.assertEquals(TEST_STR, response.getEmail());
		Assertions.assertEquals(UserType.COLLECTOR, response.getRole());
	}
}
