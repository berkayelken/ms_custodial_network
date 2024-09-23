package io.github.berkayelken.custodial.network.rest.wallet;

import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.Wallet;
import io.github.berkayelken.custodial.network.extcall.wallet.WalletClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping ("/api/wallet")
public class WalletController {
	private final WalletClient client;

	public WalletController(WalletClient client) {
		this.client = client;
	}

	@GetMapping
	public Wallet getWalletOfUser(@RequestHeader String email) {
		return client.getWalletOfUser(email);
	}

}
