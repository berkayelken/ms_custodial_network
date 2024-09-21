package io.github.berkayelken.custodial.network.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class JwtPropertiesTest {
	private static final long DEFAULT_LONG = 0;
	private static final String TEST_STR = UUID.randomUUID().toString();

	@Test
	public void testNoArgsConstructor() {
		Assertions.assertDoesNotThrow(JwtProperties::new);
	}

	@Test
	public void testAccessors() {
		JwtProperties properties = new JwtProperties();

		Assertions.assertNull(properties.getSecretKey());
		Assertions.assertEquals(DEFAULT_LONG, properties.getValidityInMs());
	}

	@Test
	public void testMutators() {
		JwtProperties properties = new JwtProperties();

		properties.setSecretKey(TEST_STR);
		properties.setValidityInMs(Long.MAX_VALUE);

		Assertions.assertEquals(TEST_STR, properties.getSecretKey());
		Assertions.assertEquals(Long.MAX_VALUE, properties.getValidityInMs());
	}

	@Test
	public void testCreateAndGetExpireAt() {
		JwtProperties properties = new JwtProperties();

		Assertions.assertNotEquals(DEFAULT_LONG, properties.createAndGetExpireAt());
	}
}
