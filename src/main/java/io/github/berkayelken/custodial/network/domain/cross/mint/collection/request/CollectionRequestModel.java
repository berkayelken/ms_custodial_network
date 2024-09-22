package io.github.berkayelken.custodial.network.domain.cross.mint.collection.request;

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
}
