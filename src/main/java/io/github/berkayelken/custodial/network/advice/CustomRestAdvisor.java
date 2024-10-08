package io.github.berkayelken.custodial.network.advice;

import io.github.berkayelken.custodial.network.exception.AuthorizationException;
import io.github.berkayelken.custodial.network.exception.InvalidAuthenticationTokenException;
import io.github.berkayelken.custodial.network.exception.UsedEmailException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@Order (Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomRestAdvisor extends ResponseEntityExceptionHandler {
	@ExceptionHandler ({ AuthorizationException.class, InvalidAuthenticationTokenException.class })
	public ResponseEntity<?> handleAuthorizationException() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@ExceptionHandler ({ UsedEmailException.class })
	public ResponseEntity<?> handleUsedEmailException() {
		return ResponseEntity.badRequest().body("Email in use!");
	}
}
