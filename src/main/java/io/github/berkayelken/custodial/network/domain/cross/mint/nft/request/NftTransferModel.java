package io.github.berkayelken.custodial.network.domain.cross.mint.nft.request;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NftTransferModel {
	private String chain;
	private String from;
	private String to;
	private String contractAddress;
	private String tokenId;
	private String quantity;

	public void handleChain() {
		chain = CrossMintConstants.CHAIN;
	}
}
