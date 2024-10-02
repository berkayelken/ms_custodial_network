package io.github.berkayelken.custodial.network.repository;

import io.github.berkayelken.custodial.network.domain.hook.NftCreationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenCreationRepository extends CrudRepository<NftCreationEntity, String> {
	NftCreationEntity findByWalletAddress(String walletAddress);
}
