package io.github.berkayelken.custodial.network.rest.user;

import io.github.berkayelken.custodial.network.auth.UserAuthenticationManager;
import io.github.berkayelken.custodial.network.domain.UserEntity;
import io.github.berkayelken.custodial.network.domain.auth.LoginResponse;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/user")
public class UserController {
	private final UserAuthenticationManager authenticationManager;

	public UserController(UserAuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PutMapping
	public LoginResponse updateUserContext(@RequestHeader ("email") String email, @RequestBody UserEntity requestEntity) {
		return authenticationManager.updateUser(email, requestEntity);
	}
}
