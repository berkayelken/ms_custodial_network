package io.github.berkayelken.custodial.network.domain.cross.mint.nft;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NftMetaData {
	private String name;
	private String description;
	private String image;
	private List<NftAttribute> attributes;
}
