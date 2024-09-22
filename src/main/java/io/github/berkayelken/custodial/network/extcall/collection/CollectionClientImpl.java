package io.github.berkayelken.custodial.network.extcall.collection;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintMetaData;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollection;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollections;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.request.CollectionRequestModel;
import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.Wallet;
import io.github.berkayelken.custodial.network.extcall.CrossMintFeignClient;
import io.github.berkayelken.custodial.network.extcall.wallet.WalletClient;
import io.github.berkayelken.custodial.network.properties.CrossMintProperties;
import org.springframework.stereotype.Service;

@Service
public class CollectionClientImpl implements CollectionClient {
	private final CrossMintFeignClient feignClient;
	private final CrossMintProperties properties;
	private final WalletClient walletClient;

	public CollectionClientImpl(CrossMintFeignClient feignClient, CrossMintProperties properties, WalletClient walletClient) {
		this.feignClient = feignClient;
		this.properties = properties;
		this.walletClient = walletClient;
	}

	@Override
	public NftCollections getAllCollections() {
		return feignClient.getAllCollections(properties.getApiKey());
	}

	@Override
	public NftCollection getCollection(String collectionID) {
		return feignClient.getCollection(properties.getApiKey(), collectionID);
	}

	@Override
	public NftCollection createCollection(CrossMintMetaData metadata, String price, String email, int supplyLimit) {
		Wallet wallet = walletClient.getWalletOfUser(email);
		return feignClient.createCollection(properties.getApiKey(),
				new CollectionRequestModel(metadata, price, wallet.getPublicKey(), supplyLimit));
	}

}
