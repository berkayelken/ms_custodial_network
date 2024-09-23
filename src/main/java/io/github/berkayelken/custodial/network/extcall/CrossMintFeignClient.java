package io.github.berkayelken.custodial.network.extcall;

import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollection;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollections;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.request.CollectionRequestModel;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.Nft;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.NftTransferResponse;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftMintModel;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftTransferModel;
import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.Wallet;
import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.WalletRequestModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient (name = "${feign-client.cross-mint.name}", url = "${feign-client.cross-mint.url}")
public interface CrossMintFeignClient {
	String API_KEY_HEADER = "X-API-KEY";
	String APPLICATION_TYPE = "application/json";

	@GetMapping (value = "/2022-06-09/collections", consumes = APPLICATION_TYPE, produces = APPLICATION_TYPE)
	NftCollections getAllCollections(@RequestHeader(API_KEY_HEADER) String apiKey);

	@GetMapping (value = "/2022-06-09/collections/{collectionId}", consumes = APPLICATION_TYPE, produces = APPLICATION_TYPE )
	NftCollection getCollection(@RequestHeader(API_KEY_HEADER) String apiKey, @PathVariable("collectionId") String collectionID);

	@PostMapping(value = "/2022-06-09/collections", consumes = APPLICATION_TYPE, produces = APPLICATION_TYPE)
	NftCollection createCollection(@RequestHeader(API_KEY_HEADER) String apiKey, @RequestBody CollectionRequestModel requestModel);

	@PostMapping(value = "/v1-alpha1/wallets", consumes = APPLICATION_TYPE, produces = APPLICATION_TYPE)
	Wallet createWallet(@RequestHeader(API_KEY_HEADER) String apiKey, @RequestBody WalletRequestModel requestModel);

	@GetMapping(value = "/v1-alpha1/wallets", consumes = APPLICATION_TYPE, produces = APPLICATION_TYPE)
	List<Wallet> getWalletsOfUser(@RequestHeader(API_KEY_HEADER) String apiKey, @RequestParam String email);

	@GetMapping(value = "/2022-06-09/wallets/{identifier}/nfts", consumes = APPLICATION_TYPE, produces = APPLICATION_TYPE )
	List<Nft> getNftFromWallet(@RequestHeader(API_KEY_HEADER) String apiKey, @PathVariable String identifier, @RequestParam int page, @RequestParam int perPage);

	@PostMapping(value = "/v1-alpha1/wallets", consumes = APPLICATION_TYPE, produces = APPLICATION_TYPE )
	NftTransferResponse transferNft(@RequestHeader(API_KEY_HEADER) String apiKey, @RequestBody NftTransferModel requestModel);

	@PostMapping(value = "/2022-06-09/collections/{collectionId}/nfts", consumes = APPLICATION_TYPE, produces = APPLICATION_TYPE )
	NftTransferResponse mintNft(@RequestHeader(API_KEY_HEADER) String apiKey, @PathVariable String collectionId, @RequestBody NftMintModel requestModel);

}
