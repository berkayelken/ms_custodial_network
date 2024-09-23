package io.github.berkayelken.custodial.network.domain.cross.mint.collection;

import io.github.berkayelken.custodial.network.domain.cross.mint.Chain;
import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintMetaData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NftCollection {
	private String id;
	private CrossMintMetaData metadata;
	private String fungibility;
	private Chain onChain;
	private int supplyLimit;
	private CollectionPayment payments;

	public String getWalletAddress() {
		if (payments == null) {
			return null;
		}
		return payments.getRecipientAddress();
	}
}
