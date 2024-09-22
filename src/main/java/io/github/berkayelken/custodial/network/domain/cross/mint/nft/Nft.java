package io.github.berkayelken.custodial.network.domain.cross.mint.nft;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintMetaData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Nft {
	private String chain;
	private String mintHash;
	private NftMetaData metadata;
	private String locator;
}
