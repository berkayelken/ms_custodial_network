package io.github.berkayelken.custodial.network.domain.cross.mint.wallet;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Wallet {
	private String chain;
	private String publicKey;
}
