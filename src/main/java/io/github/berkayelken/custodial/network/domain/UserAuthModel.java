package io.github.berkayelken.custodial.network.domain;

import io.github.berkayelken.custodial.network.domain.auth.UserType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class UserAuthModel extends User {
	private List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

	public UserAuthModel(UserEntity userEntity) {
		super(userEntity.getId(), userEntity.getEmail(), getAuthorities(userEntity));
	}

	private static List<GrantedAuthority> getAuthorities(UserEntity userEntity) {
		UserType userType = UserType.findUserType(userEntity.getRole());
		return Collections.singletonList(new SimpleGrantedAuthority(userType.getAuthorityRole()));
	}
}
