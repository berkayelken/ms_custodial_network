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

	@GetMapping ("/2022-06-09/collections")
	NftCollections getAllCollections(@RequestHeader(API_KEY_HEADER) String apiKey);

	@GetMapping ("/2022-06-09/collections/{collectionId}")
	NftCollection getCollection(@RequestHeader(API_KEY_HEADER) String apiKey, @PathVariable("collectionId") String collectionID);

	@PostMapping("/2022-06-09/collections")
	NftCollection createCollection(@RequestHeader(API_KEY_HEADER) String apiKey, @RequestBody CollectionRequestModel requestModel);

	@PostMapping("/v1-alpha1/wallets")
	Wallet createWallet(@RequestHeader(API_KEY_HEADER) String apiKey, @RequestBody WalletRequestModel requestModel);

	@GetMapping("/v1-alpha1/wallets")
	List<Wallet> getWalletsOfUser(@RequestHeader(API_KEY_HEADER) String apiKey, @RequestParam String email);

	// idetifier email:<email_address>:<chain> or <chain>:<address>
	@GetMapping("/2022-06-09/wallets/{identifier}/nfts")
	List<Nft> getNftFromWallet(@RequestHeader(API_KEY_HEADER) String apiKey, @PathVariable String identifier, @RequestParam int page, @RequestParam int perPage);

	@PostMapping("/v1-alpha1/wallets")
	NftTransferResponse transferNft(@RequestHeader(API_KEY_HEADER) String apiKey, @RequestBody NftTransferModel requestModel);

	@PostMapping("/2022-06-09/collections/{collectionId}/nfts")
	NftTransferResponse mintNft(@RequestHeader(API_KEY_HEADER) String apiKey, @PathVariable String collectionId, @RequestBody NftMintModel requestModel);
	
}
