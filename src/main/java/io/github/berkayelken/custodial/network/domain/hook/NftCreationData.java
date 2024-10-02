package io.github.berkayelken.custodial.network.domain.hook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class NftCreationData {
	private Map<String, String> recipient;
	private Map<String, String> collection;
	private Token token;
	private String txId;

	NftCreationEntity createDocument() {
		NftCreationEntity entity = new NftCreationEntity();

		entity.setId(getTokenId());
		entity.setTxId(txId);
		entity.setCollectionId(collection.get("id"));
		entity.setWalletAddress(recipient.get("walletAddress"));
		entity.setNftContractAddress(token.getMintHash());

		return entity;
	}

	String getTokenId() {
		return token.getId();
	}
}
