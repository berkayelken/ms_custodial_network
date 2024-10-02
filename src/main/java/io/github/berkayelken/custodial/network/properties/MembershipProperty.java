package io.github.berkayelken.custodial.network.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MembershipProperty {
	private String collectionId;
	private String name;
	private String description;
	private String image;
	private String price;
	private String currency;
	private int order;
	private String projectId;
	private List<DummyNftProperty> nftList;

	MembershipProperty addProjectId(String projectId) {
		this.projectId = projectId;
		return this;
	}
}
