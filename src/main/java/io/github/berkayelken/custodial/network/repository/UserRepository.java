package io.github.berkayelken.custodial.network.repository;

import io.github.berkayelken.custodial.network.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
	UserEntity findByEmail(String email);

	boolean existsByEmail(String email);
}
