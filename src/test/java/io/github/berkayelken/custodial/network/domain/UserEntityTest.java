package io.github.berkayelken.custodial.network.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserEntityTest {
	private static final String TEST_STR = UUID.randomUUID().toString();

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(UserEntity::new);
	}

	@Test
	public void testAccessors() {
		UserEntity entity = new UserEntity();

		Assertions.assertNull(entity.getId());
		Assertions.assertNull(entity.getEmail());
		Assertions.assertNull(entity.getPassword());
	}

	@Test
	public void testMutators() {
		UserEntity entity = new UserEntity();

		entity.setId(TEST_STR);
		entity.setEmail(TEST_STR);
		entity.setPassword(TEST_STR);

		Assertions.assertEquals(TEST_STR, entity.getId());
		Assertions.assertEquals(TEST_STR, entity.getEmail());
		Assertions.assertEquals(TEST_STR, entity.getPassword());
	}
}
