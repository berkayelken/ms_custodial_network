package io.github.berkayelken.custodial.network.rest.collection;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintMetaData;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollection;
import io.github.berkayelken.custodial.network.extcall.collection.CollectionClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping ("/api/artist/collection")
public class CollectionCreatorController {
	private final CollectionClient client;

	public CollectionCreatorController(CollectionClient client) {
		this.client = client;
	}

	@PostMapping
	public NftCollection createCollection(@RequestBody CrossMintMetaData metadata, @RequestHeader String email,
			@RequestParam String price, @RequestParam int supplyLimit) {
		return client.createCollection(metadata, price, email, supplyLimit);
	}
}
