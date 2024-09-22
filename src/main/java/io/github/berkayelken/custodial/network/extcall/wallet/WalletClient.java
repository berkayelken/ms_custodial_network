package io.github.berkayelken.custodial.network.extcall.wallet;

import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.Wallet;

public interface WalletClient {
	void createWallet(String email);

	Wallet getWalletOfUser(String email);
}
