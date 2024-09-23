package io.github.berkayelken.custodial.network.domain.cross.mint.nft;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Nft {
	private String chain;
	private String owner;
	private String mintHash;
	private NftMetaData metadata;
	private String locator;
	private String price;

	public Nft addOwner(String address) {
		owner = address;
		price = "0.1 SOL";
		return this;
	}
}
