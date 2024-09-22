package io.github.berkayelken.custodial.network.domain.cross.mint.collection.request;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintConstants;
import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintMetaData;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.CollectionPayment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CollectionRequestModel {
	private String chain;
	private CrossMintMetaData metadata;
	private String fungibility;
	private int supplyLimit;
	private CollectionPayment payments;
	private boolean reuploadLinkedFiles;

	public CollectionRequestModel(CrossMintMetaData metadata, String price, String recipientAddress, int supplyLimit) {
		chain = CrossMintConstants.CHAIN;
		this.metadata = metadata;
		fungibility = CrossMintConstants.FUNGIBILITY;
		this.supplyLimit = supplyLimit;
		payments = new CollectionPayment(recipientAddress, price);
		reuploadLinkedFiles = true;
	}
}
