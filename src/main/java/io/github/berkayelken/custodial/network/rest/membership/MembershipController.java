package io.github.berkayelken.custodial.network.rest.membership;

import io.github.berkayelken.custodial.network.service.TenureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/membership")
public class MembershipController {
	private final TenureService service;

	public MembershipController(TenureService service) {
		this.service = service;
	}

	@GetMapping
	public int getUserNft(@RequestHeader String email) {
		return service.calculateOrder(email);
	}
}
