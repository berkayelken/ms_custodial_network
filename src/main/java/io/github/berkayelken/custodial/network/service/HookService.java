package io.github.berkayelken.custodial.network.service;

import io.github.berkayelken.custodial.network.domain.hook.NftCreation;
import io.github.berkayelken.custodial.network.domain.hook.NftCreationEntity;
import io.github.berkayelken.custodial.network.properties.MembershipProperties;
import io.github.berkayelken.custodial.network.repository.TokenCreationRepository;
import org.springframework.stereotype.Service;

@Service
public class HookService {
	private final TokenCreationRepository repository;
	private final MembershipProperties properties;

	public HookService(TokenCreationRepository repository, MembershipProperties properties) {
		this.repository = repository;
		this.properties = properties;
	}

	public void saveToken(NftCreation request) {
		NftCreationEntity entity = request.createDocument();
		if (properties.isRelatedCollection(entity.getCollectionId()) && !repository.existsById(request.getTokenId())) {
			repository.save(entity);
		}
	}

}
