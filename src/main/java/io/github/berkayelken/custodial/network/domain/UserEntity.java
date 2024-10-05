package io.github.berkayelken.custodial.network.domain;

import io.github.berkayelken.custodial.network.domain.auth.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document (collection = "userEntity")
public class UserEntity {
	@MongoId (targetType = FieldType.STRING)
	private String id;
	private String email;
	private String password;
	private String name;
	private String surname;
	private String location;
	private Map<String, String> socialAccounts;
	private Map<String, Boolean> interests;
	private int role;

	public void updateUser(UserEntity entity, UserType type) {
		name = entity.getName();
		surname = entity.getSurname();
		location = entity.getLocation();
		socialAccounts = entity.getSocialAccounts();
		interests = entity.getInterests();
		role = type.getValidOperations();
	}
}
