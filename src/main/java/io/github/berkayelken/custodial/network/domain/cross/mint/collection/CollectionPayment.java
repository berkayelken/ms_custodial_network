package io.github.berkayelken.custodial.network.domain.cross.mint.collection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CollectionPayment {
	private String price;
	private String recipientAddress;
	private String currency;
}
