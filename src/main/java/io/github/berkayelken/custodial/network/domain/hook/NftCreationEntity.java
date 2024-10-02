package io.github.berkayelken.custodial.network.domain.hook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@ToString
@Document(collection = "CreatedNft")
public class NftCreationEntity {
	@MongoId(targetType = FieldType.STRING)
	private String id;
	private String collectionId;
	private String nftContractAddress;
	private String walletAddress;
	private String txId;
}
