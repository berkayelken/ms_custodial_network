package io.github.berkayelken.custodial.network.rest.nft;

import io.github.berkayelken.custodial.network.domain.cross.mint.nft.Nft;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.NftTransferResponse;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftTransferModel;
import io.github.berkayelken.custodial.network.extcall.nft.NftClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/nft")
public class NftController {
	private final NftClient client;

	public NftController(NftClient client) {
		this.client = client;
	}

	@GetMapping
	public List<Nft> getUserNft(@RequestHeader String email) {
		return client.getSpecificUserNft(email);
	}

	@GetMapping ("/all")
	public List<Nft> getAllNft() {
		return client.getAllNft();
	}

	@GetMapping ("/collection/{collectionId}")
	public List<Nft> getSpecificCollectionNft(@PathVariable String collectionId) {
		return client.getSpecificCollectionNft(collectionId);
	}

	@PostMapping ("/transfer")
	public NftTransferResponse transferNft(@RequestBody NftTransferModel requestModel) {
		return client.transferNft(requestModel);
	}
}
