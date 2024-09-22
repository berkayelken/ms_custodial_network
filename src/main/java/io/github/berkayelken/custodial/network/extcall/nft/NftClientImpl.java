package io.github.berkayelken.custodial.network.extcall.nft;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintConstants;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollection;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollections;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.Nft;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.NftTransferResponse;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftMintModel;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftTransferModel;
import io.github.berkayelken.custodial.network.extcall.CrossMintFeignClient;
import io.github.berkayelken.custodial.network.extcall.collection.CollectionClient;
import io.github.berkayelken.custodial.network.properties.CrossMintProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class NftClientImpl implements NftClient {
	private static final String WALLET_NFT_TEMPLATE = "email:%s:%s";
	private static final String COLLECTION_NFT_TEMPLATE = "%s:%s";
	private static final int NFT_COUNT_PER_PAGE = 20;

	private final CrossMintFeignClient feignClient;
	private final CrossMintProperties properties;
	private final CollectionClient collectionClient;

	public NftClientImpl(CrossMintFeignClient feignClient, CrossMintProperties properties, CollectionClient collectionClient) {
		this.feignClient = feignClient;
		this.properties = properties;
		this.collectionClient = collectionClient;
	}

	@Override
	public List<Nft> getSpecificUserNft(String email) {
		return getNftList(String.format(WALLET_NFT_TEMPLATE, email, CrossMintConstants.CHAIN));
	}

	@Override
	public List<Nft> getAllNft() {
		NftCollections collections = collectionClient.getAllCollections();
		return collections.getResults().stream().map(NftCollection::getWalletAddress)
				.map(address -> getNftList(String.format(COLLECTION_NFT_TEMPLATE, address, CrossMintConstants.CHAIN)))
				.flatMap(Collection::stream).collect(Collectors.toList());
	}

	@Override
	public List<Nft> getSpecificCollectionNft(String collectionId) {
		NftCollection nftCollection = collectionClient.getCollection(collectionId);
		return getNftList(String.format(COLLECTION_NFT_TEMPLATE, nftCollection.getWalletAddress(), CrossMintConstants.CHAIN));
	}

	@Override
	public NftTransferResponse transferNft(NftTransferModel requestModel) {
		return feignClient.transferNft(properties.getApiKey(), requestModel);
	}

	@Override
	public NftTransferResponse mintNft(NftMintModel requestModel) {
		NftCollection collection = collectionClient.getAllCollections().getResults().stream()
				.filter(c -> c.getWalletAddress().equalsIgnoreCase(requestModel.getRecipient())).findFirst()
				.orElseThrow(() -> new RuntimeException("User should create collection before minting."));
		return feignClient.mintNft(properties.getApiKey(), collection.getId(), requestModel);
	}

	private List<Nft> getNftList(String identifier) {
		AtomicInteger page = new AtomicInteger();
		List<Nft> allNfts = new ArrayList<>();
		List<Nft> tempNftList = mergeAndGetNftList(allNfts, identifier, page);
		while (!CollectionUtils.isEmpty(tempNftList)) {
			tempNftList = mergeAndGetNftList(allNfts, identifier, page);
		}

		return allNfts;
	}

	private List<Nft> mergeAndGetNftList(List<Nft> allNfts, String identifier, AtomicInteger page) {
		List<Nft> tempNftList = feignClient.getNftFromWallet(properties.getApiKey(), identifier, page.incrementAndGet(),
				NFT_COUNT_PER_PAGE);
		if (CollectionUtils.isEmpty(tempNftList)) {
			return Collections.emptyList();
		}
		allNfts.addAll(tempNftList);
		return tempNftList;
	}
}
