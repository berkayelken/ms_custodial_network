package io.github.berkayelken.custodial.network.auth;

import io.github.berkayelken.custodial.network.domain.UserAuthModel;
import io.github.berkayelken.custodial.network.domain.UserEntity;
import io.github.berkayelken.custodial.network.domain.auth.LoginResponse;
import io.github.berkayelken.custodial.network.domain.auth.UserType;
import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.Wallet;
import io.github.berkayelken.custodial.network.exception.AuthorizationException;
import io.github.berkayelken.custodial.network.exception.InvalidAuthenticationTokenException;
import io.github.berkayelken.custodial.network.exception.UsedEmailException;
import io.github.berkayelken.custodial.network.extcall.wallet.WalletClient;
import io.github.berkayelken.custodial.network.properties.JwtProperties;
import io.github.berkayelken.custodial.network.repository.UserRepository;
import io.github.berkayelken.custodial.network.service.TenureService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationManager implements AuthenticationManager {
	private final UserRepository repository;
	private final JwtTokenProvider tokenProvider;
	private final JwtProperties jwtProperties;
	private final WalletClient walletClient;
	private final TenureService tenureService;

	public UserAuthenticationManager(UserRepository repository, JwtTokenProvider tokenProvider, JwtProperties jwtProperties,
			WalletClient walletClient, TenureService tenureService) {
		this.repository = repository;
		this.tokenProvider = tokenProvider;
		this.jwtProperties = jwtProperties;
		this.walletClient = walletClient;
		this.tenureService = tenureService;
	}

	@SneakyThrows
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = String.valueOf(authentication.getPrincipal());
		UserEntity user = getUser(email);
		UserAuthModel authModel = new UserAuthModel(user);
		return new UsernamePasswordAuthenticationToken(email, authModel.getPassword(), authModel.getAuthorities());
	}

	public LoginResponse doRegister(String email, String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		if (repository.existsByEmail(email)) {
			throw new UsedEmailException();
		}

		UserEntity user = UserEntity.builder().email(email).password(encoder.encode(password))
				.role(UserType.PLAIN_MEMBER.getValidOperations()).build();
		repository.save(user);
		walletClient.createWallet(email);

		Authentication authentication = authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		String token = tokenProvider.createToken(authentication, user);
		Wallet wallet = walletClient.getWalletOfUser(email);

		return LoginResponse.builder().expireAt(jwtProperties.createAndGetExpireAt().toEpochMilli()).email(email).token(token)
				.wallet(wallet.getPublicKey()).artist(false).registrationCompleted(false).build();
	}

	public LoginResponse updateUser(String email, UserEntity entity) {
		UserEntity user = getUser(email);

		user.updateUser(entity);

		return doLogin(repository.save(user));
	}

	public LoginResponse doLogin(String email, String password) {
		UserEntity user = getUser(email);
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		if (!encoder.matches(password, user.getPassword())) {
			throw new InvalidAuthenticationTokenException("Authentication context is invalid",
					HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}

		return doLogin(user);
	}

	public LoginResponse doLogin(UserEntity user) {
		Authentication authentication = authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		String token = tokenProvider.createToken(authentication, user);
		Wallet wallet = walletClient.getWalletOfUser(user.getEmail());

		return LoginResponse.builder().expireAt(jwtProperties.createAndGetExpireAt().toEpochMilli()).email(user.getEmail())
				.token(token).order(tenureService.calculateOrder(wallet)).wallet(wallet.getPublicKey())
				.artist(UserType.isArtistType(user.getRole())).name(user.getName()).surname(user.getSurname())
				.location(user.getLocation()).interests(user.getInterests()).socialAccounts(user.getSocialAccounts())
				.registrationCompleted(0 != user.getRole()).build();
	}

	private UserEntity getUser(String email) {
		UserEntity user = repository.findByEmail(email);
		if (user == null)
			throw new AuthorizationException("User cannot be found. Authorization is failed.");

		return user;
	}
}
