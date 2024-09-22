package io.github.berkayelken.custodial.network.domain.cross.mint.collection;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CollectionPayment {
	private String price;
	private String recipientAddress;
	private String currency;

	public CollectionPayment(String recipientAddress, String price) {
		this.price = price;
		this.recipientAddress = recipientAddress;
		this.currency = CrossMintConstants.MAIN_CURRENCY;
	}
}
