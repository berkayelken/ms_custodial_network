package io.github.berkayelken.custodial.network.rest.collection;

import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollection;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollections;
import io.github.berkayelken.custodial.network.extcall.collection.CollectionClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
public class CollectionFetcherController {
	private final CollectionClient client;

	public CollectionFetcherController(CollectionClient client) {
		this.client = client;
	}

	@GetMapping("/all")
	public NftCollections getAllCollections() {
		return client.getAllCollections();
	}

	@GetMapping("/{collectionID}")
	public NftCollection getCollection(@PathVariable String collectionID) {
		return client.getCollection(collectionID);
	}
}
