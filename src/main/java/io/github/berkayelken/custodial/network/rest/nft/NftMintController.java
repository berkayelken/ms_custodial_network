package io.github.berkayelken.custodial.network.rest.nft;

import io.github.berkayelken.custodial.network.domain.cross.mint.nft.NftTransferResponse;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftMintModel;
import io.github.berkayelken.custodial.network.extcall.nft.NftClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/artist/nft")
public class NftMintController {
	private final NftClient client;

	public NftMintController(NftClient client) {
		this.client = client;
	}

	@PostMapping ("/mint")
	public NftTransferResponse mintNft(@RequestBody NftMintModel requestModel) {
		return client.mintNft(requestModel);
	}
}
