package io.github.berkayelken.custodial.network.extcall.nft;

import io.github.berkayelken.custodial.network.domain.cross.mint.nft.Nft;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.NftTransferResponse;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftMintModel;
import io.github.berkayelken.custodial.network.domain.cross.mint.nft.request.NftTransferModel;

import java.util.List;

public interface NftClient {
	List<Nft> getSpecificUserNft(String email);

	List<Nft> getAllNft();

	List<Nft> getSpecificCollectionNft(String collectionId);

	NftTransferResponse transferNft(NftTransferModel requestModel);

	NftTransferResponse mintNft(NftMintModel requestModel);
}
