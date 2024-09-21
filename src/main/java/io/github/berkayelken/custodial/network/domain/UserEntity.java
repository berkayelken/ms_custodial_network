package io.github.berkayelken.custodial.network.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document(collation = "user")
public class UserEntity {
	@MongoId
	private String id;
	private String email;
	private String password;
}
