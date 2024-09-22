package io.github.berkayelken.custodial.network.extcall.wallet;

import io.github.berkayelken.custodial.network.domain.cross.mint.CrossMintConstants;
import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.Wallet;
import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.WalletRequestModel;
import io.github.berkayelken.custodial.network.extcall.CrossMintFeignClient;
import io.github.berkayelken.custodial.network.properties.CrossMintProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletClientImpl implements WalletClient {
	private final CrossMintFeignClient feignClient;
	private final CrossMintProperties properties;

	public WalletClientImpl(CrossMintFeignClient feignClient, CrossMintProperties properties) {
		this.feignClient = feignClient;
		this.properties = properties;
	}

	@Override
	public Wallet createWallet(String email) {
		return feignClient.createWallet(properties.getApiKey(), new WalletRequestModel(email));
	}

	@Override
	public Wallet getWalletOfUser(String email) {
		List<Wallet> wallets = feignClient.getWalletsOfUser(properties.getApiKey(), email);
		return wallets.stream().filter(wallet -> CrossMintConstants.CHAIN.equals(wallet.getChain())).findFirst()
				.orElseThrow(() -> new RuntimeException("Wallet not found."));
	}
}
