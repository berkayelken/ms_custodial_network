package io.github.berkayelken.custodial.network.rest.auth;

import io.github.berkayelken.custodial.network.auth.UserAuthenticationManager;
import io.github.berkayelken.custodial.network.domain.UserEntity;
import io.github.berkayelken.custodial.network.domain.auth.LoginResponse;
import io.github.berkayelken.custodial.network.domain.auth.UserType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@CrossOrigin
@RequestMapping ("/api/auth")
@RestController
public class AuthController {
	private final UserAuthenticationManager authenticationManager;

	public AuthController(UserAuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping ("/register")
	public LoginResponse register(@RequestHeader ("email") String email, @RequestHeader ("password") String password) {
		return authenticationManager.doRegister(email, password);
	}

	@PostMapping ("/login")
	public LoginResponse login(@RequestHeader ("email") String email, @RequestHeader ("password") String password) {
		return authenticationManager.doLogin(email, password);
	}

	@PutMapping
	public LoginResponse updateUserContext(@RequestHeader ("email") String email, @RequestHeader ("password") String password,
			@RequestHeader ("user-type") UserType type, @RequestBody UserEntity requestEntity) {
		return authenticationManager.updateUser(email, password, type, requestEntity);
	}

}
