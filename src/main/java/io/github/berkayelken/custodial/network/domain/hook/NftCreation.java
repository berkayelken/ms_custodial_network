package io.github.berkayelken.custodial.network.domain.hook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NftCreation {
	private String actionId;
	private NftCreationData data;

	public NftCreationEntity createDocument() {
		return data.createDocument();
	}

	public String getTokenId() {
		return data.getTokenId();
	}
}
