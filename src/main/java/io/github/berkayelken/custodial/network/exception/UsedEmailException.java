package io.github.berkayelken.custodial.network.exception;

public class UsedEmailException extends RuntimeException {
	public UsedEmailException() {
		super("The email in-use.");
	}
}
