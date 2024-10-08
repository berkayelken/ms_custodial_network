package io.github.berkayelken.custodial.network.rest.membership;

import io.github.berkayelken.custodial.network.domain.hook.NftCreation;
import io.github.berkayelken.custodial.network.service.HookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/webhook")
public class WebhookController {
	private final HookService service;

	public WebhookController(HookService service) {
		this.service = service;
	}

	@PostMapping
	public void addToken(@RequestBody NftCreation request) {
		service.saveToken(request);
	}
}
