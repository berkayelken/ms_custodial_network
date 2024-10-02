package io.github.berkayelken.custodial.network.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties ("membership-properties")
public class MembershipProperties {
	private String projectId;
	private Map<String, MembershipProperty> collectionMap;
	private Map<String, MembershipProperty> dummyCollection;

	public Collection<MembershipProperty> collectMembershipCollectionList() {
		return collectionMap.values().stream().map(membershipProperty -> membershipProperty.addProjectId(projectId)).toList();
	}

	public MembershipProperty findLotrCollection() {
		return dummyCollection.get("lotr");
	}

	public boolean isRelatedCollection(String collectionId) {
		return collectionMap.values().stream().map(MembershipProperty::getCollectionId).anyMatch(collectionId::equals);
	}

	public int findTotalOrder(List<String> collectionIds) {
		return collectionMap.values().stream().filter(prop -> collectionIds.contains(prop.getCollectionId()))
				.map(MembershipProperty::getOrder).mapToInt(Integer::intValue).max().orElse(0);
	}
}
