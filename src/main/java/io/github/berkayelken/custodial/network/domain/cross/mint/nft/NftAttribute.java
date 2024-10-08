package io.github.berkayelken.custodial.network.domain.cross.mint.nft;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NftAttribute {
	private String value;
	@JsonProperty ("trait_type")
	private String traitType;
}
