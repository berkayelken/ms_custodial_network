package io.github.berkayelken.custodial.network.extcall.collection;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintMetaData;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollection;
import io.github.berkayelken.custodial.network.domain.cross.mint.collection.NftCollections;

public interface CollectionClient {
	NftCollections getAllCollections();

	NftCollection getCollection(String collectionID);

	NftCollection createCollection(CrossMintMetaData metadata, String price, String email, int supplyLimit);
}
