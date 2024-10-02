package io.github.berkayelken.custodial.network.auth;

import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.Wallet;
import io.github.berkayelken.custodial.network.domain.hook.NftCreationEntity;
import io.github.berkayelken.custodial.network.properties.MembershipProperties;
import io.github.berkayelken.custodial.network.repository.TokenCreationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TenureService {
	private final TokenCreationRepository repository;
	private final MembershipProperties properties;

	public TenureService(TokenCreationRepository repository, MembershipProperties properties) {
		this.repository = repository;
		this.properties = properties;
	}

	int calculateOrder(Wallet wallet) {
		List<NftCreationEntity> nftList = repository.findByWalletAddress(wallet.getPublicKey());
		if (CollectionUtils.isEmpty(nftList)) {
			return 0;
		}

		return properties.findTotalOrder(nftList.stream().map(NftCreationEntity::getCollectionId).toList());
	}
}
