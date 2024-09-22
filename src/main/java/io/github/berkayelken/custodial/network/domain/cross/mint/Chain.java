package io.github.berkayelken.custodial.network.domain.cross.mint;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Chain {
	private String chain;
	private String type;
	private String contractAddress;
}
