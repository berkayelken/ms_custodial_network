package io.github.berkayelken.custodial.network.repository;

import io.github.berkayelken.custodial.network.domain.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, String> {
	Mono<UserEntity> findByEmail(String email);

	Mono<Boolean> existsByEmail(String email);
}
