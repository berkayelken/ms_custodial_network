package io.github.berkayelken.custodial.network.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

	MembershipProperty addProjectId(String projectId) {
		this.projectId = projectId;
		return this;
	}
}
