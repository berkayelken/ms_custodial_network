package io.github.berkayelken.custodial.network.domain.cross.mint.wallet;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WalletRequestModel {
	private String chain;
	private String email;

	public WalletRequestModel(String email) {
		chain = CrossMintConstants.CHAIN;
		this.email = email;
	}
}
