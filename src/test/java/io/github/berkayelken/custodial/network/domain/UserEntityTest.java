package io.github.berkayelken.custodial.network.domain;

import io.github.berkayelken.custodial.network.domain.auth.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserEntityTest {
	private static final String TEST_STR = UUID.randomUUID().toString();
	private static final int DEFAULT_INT = 0;

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new UserEntity());
	}

	@Test
	public void testAllArgsConstructor() {
		Assertions.assertDoesNotThrow(() -> new UserEntity(TEST_STR, TEST_STR, TEST_STR, DEFAULT_INT));
	}

	@Test
	public void testAccessors() {
		UserEntity entity = new UserEntity();

		Assertions.assertNull(entity.getId());
		Assertions.assertNull(entity.getEmail());
		Assertions.assertNull(entity.getPassword());
		Assertions.assertEquals(DEFAULT_INT, entity.getRole());
	}

	@Test
	public void testMutators() {
		UserEntity entity = new UserEntity();

		entity.setId(TEST_STR);
		entity.setEmail(TEST_STR);
		entity.setPassword(TEST_STR);
		entity.setRole(UserType.ARTIST.getValidOperations());

		Assertions.assertEquals(TEST_STR, entity.getId());
		Assertions.assertEquals(TEST_STR, entity.getEmail());
		Assertions.assertEquals(TEST_STR, entity.getPassword());
		Assertions.assertEquals(UserType.ARTIST.getValidOperations(), entity.getRole());
	}

	public static UserEntity createInstance() {
		UserEntity entity = new UserEntity();

		entity.setRole(UserType.ARTIST.getValidOperations());
		entity.setId(TEST_STR);
		entity.setEmail(TEST_STR);
		entity.setPassword(TEST_STR);

		return entity;
	}
}
