package io.github.berkayelken.custodial.network.repository;

import io.github.berkayelken.custodial.network.domain.hook.NftCreationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenCreationRepository extends CrudRepository<NftCreationEntity, String> {
	List<NftCreationEntity> findByWalletAddress(String walletAddress);
}
