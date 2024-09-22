package io.github.berkayelken.custodial.network.domain.cross.mint;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CrossMintMetaData {
	private String name;
	private String description;
	private String imageUrl;
	private String symbol;
}
