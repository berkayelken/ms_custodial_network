package io.github.berkayelken.custodial.network.domain;

import io.github.berkayelken.custodial.network.domain.auth.UserType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document(collation = "user")
public class UserEntity {
	@MongoId(targetType = FieldType.STRING)
	private String id;
	private String email;
	private String password;
	private int role;
}
