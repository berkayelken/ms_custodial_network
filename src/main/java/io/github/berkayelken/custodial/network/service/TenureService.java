package io.github.berkayelken.custodial.network.service;

import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.Wallet;
import io.github.berkayelken.custodial.network.domain.hook.NftCreationEntity;
import io.github.berkayelken.custodial.network.extcall.wallet.WalletClient;
import io.github.berkayelken.custodial.network.properties.MembershipProperties;
import io.github.berkayelken.custodial.network.repository.TokenCreationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TenureService {
	private final TokenCreationRepository repository;
	private final MembershipProperties properties;
	private final WalletClient walletClient;

	public TenureService(TokenCreationRepository repository, MembershipProperties properties, WalletClient walletClient) {
		this.repository = repository;
		this.properties = properties;
		this.walletClient = walletClient;
	}

	public int calculateOrder(String email) {
		Wallet wallet = walletClient.getWalletOfUser(email);
		return calculateOrder(wallet);
	}

	public int calculateOrder(Wallet wallet) {
		List<NftCreationEntity> nftList = repository.findByWalletAddress(wallet.getPublicKey());
		if (CollectionUtils.isEmpty(nftList)) {
			return 0;
		}

		return properties.findTotalOrder(nftList.stream().map(NftCreationEntity::getCollectionId).toList());
	}
}
