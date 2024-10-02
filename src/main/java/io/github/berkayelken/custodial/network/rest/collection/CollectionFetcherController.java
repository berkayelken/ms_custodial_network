package io.github.berkayelken.custodial.network.rest.collection;

import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollection;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollections;
import io.github.berkayelken.custodial.network.extcall.collection.CollectionClient;
import io.github.berkayelken.custodial.network.properties.MembershipProperties;
import io.github.berkayelken.custodial.network.properties.MembershipProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/api/collection")
public class CollectionFetcherController {
	private final CollectionClient client;
	private final MembershipProperties properties;

	public CollectionFetcherController(CollectionClient client, MembershipProperties properties) {
		this.client = client;
		this.properties = properties;
	}

	@GetMapping("/all")
	public NftCollections getAllCollections() {
		return client.getAllCollections();
	}

	@GetMapping("/{collectionID}")
	public NftCollection getCollection(@PathVariable String collectionID) {
		return client.getCollection(collectionID);
	}

	@GetMapping("/membership")
	public Collection<MembershipProperty> getMembershipCollections() {
		return properties.collectMembershipCollectionList();
	}
}
