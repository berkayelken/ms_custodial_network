package io.github.berkayelken.custodial.network.domain.cross.mint.nft.request;

import io.github.berkayelken.custodial.network.domain.cross.mint.nft.NftMetaData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NftMintModel {
	private String recipient;
	private NftMetaData metadata;
}
