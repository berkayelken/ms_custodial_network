package io.github.berkayelken.custodial.network.extcall.nft;

import io.github.berkayelken.custodial.network.domain.cross.mint.nft.Nft;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.NftTransferResponse;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftMintModel;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftTransferModel;

import java.util.List;

public interface NftClient {
	// idetifier email:<email_address>:<chain> or <chain>:<address>
	List<Nft> getNftFromWallet(String identifier);

	NftTransferResponse transferNft(NftTransferModel requestModel);

	NftTransferResponse mintNft(NftMintModel requestModel);
}
