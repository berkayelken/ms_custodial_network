package io.github.berkayelken.custodial.network.auth;

import io.github.berkayelken.custodial.network.domain.UserAuthModel;
import io.github.berkayelken.custodial.network.domain.UserEntity;
import io.github.berkayelken.custodial.network.domain.auth.LoginResponse;
import io.github.berkayelken.custodial.network.domain.auth.UserType;
import io.github.berkayelken.custodial.network.domain.cross.mint.wallet.Wallet;
import io.github.berkayelken.custodial.network.domain.hook.NftCreationEntity;
import io.github.berkayelken.custodial.network.exception.AuthorizationException;
import io.github.berkayelken.custodial.network.exception.InvalidAuthenticationTokenException;
import io.github.berkayelken.custodial.network.exception.UsedEmailException;
import io.github.berkayelken.custodial.network.extcall.wallet.WalletClient;
import io.github.berkayelken.custodial.network.properties.JwtProperties;
import io.github.berkayelken.custodial.network.properties.MembershipProperties;
import io.github.berkayelken.custodial.network.repository.TokenCreationRepository;
import io.github.berkayelken.custodial.network.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserAuthenticationManager implements AuthenticationManager {
	private final UserRepository repository;
	private final TokenCreationRepository tokenRepository;
	private final JwtTokenProvider tokenProvider;
	private final JwtProperties jwtProperties;
	private final MembershipProperties membershipProperties;
	private final WalletClient walletClient;

	public UserAuthenticationManager(UserRepository repository, TokenCreationRepository tokenRepository,
			JwtTokenProvider tokenProvider, JwtProperties jwtProperties, MembershipProperties membershipProperties,
			WalletClient walletClient) {
		this.repository = repository;
		this.tokenRepository = tokenRepository;
		this.tokenProvider = tokenProvider;
		this.jwtProperties = jwtProperties;
		this.membershipProperties = membershipProperties;
		this.walletClient = walletClient;
	}

	@SneakyThrows
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = String.valueOf(authentication.getPrincipal());
		UserEntity user = getUser(email);
		UserAuthModel authModel = new UserAuthModel(user);
		return new UsernamePasswordAuthenticationToken(email, authModel.getPassword(), authModel.getAuthorities());
	}

	public LoginResponse doRegister(String email, String password, UserType userType)
			throws ExecutionException, InterruptedException {
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		if (Boolean.TRUE.equals(repository.existsByEmail(email).toFuture().get())) {
			throw new UsedEmailException();
		}

		UserEntity user = UserEntity.builder().email(email).password(encoder.encode(password)).role(userType.getValidOperations())
				.build();
		repository.save(user).toFuture().get();
		walletClient.createWallet(email);

		Authentication authentication = authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		String token = tokenProvider.createToken(authentication, user);
		Wallet wallet = walletClient.getWalletOfUser(email);

		return LoginResponse.builder().expireAt(jwtProperties.createAndGetExpireAt().toEpochMilli()).email(email).token(token)
				.wallet(wallet.getPublicKey()).build();
	}

	public LoginResponse doLogin(String email, String password) throws ExecutionException, InterruptedException {
		UserEntity user = getUser(email);
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		if (!encoder.matches(password, user.getPassword())) {
			throw new InvalidAuthenticationTokenException("Authentication context is invalid",
					HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}

		Authentication authentication = authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		String token = tokenProvider.createToken(authentication, user);
		Wallet wallet = walletClient.getWalletOfUser(email);

		return LoginResponse.builder().expireAt(jwtProperties.createAndGetExpireAt().toEpochMilli()).email(email).token(token)
				.order(calculateOrder(wallet)).wallet(wallet.getPublicKey()).build();
	}

	private int calculateOrder(Wallet wallet) {
		List<NftCreationEntity> nftList = tokenRepository.findByWalletAddress(wallet.getPublicKey());
		if (CollectionUtils.isEmpty(nftList)) {
			return 0;
		}

		return membershipProperties.findTotalOrder(nftList.stream().map(NftCreationEntity::getCollectionId).toList());
	}

	private UserEntity getUser(String email) throws ExecutionException, InterruptedException {
		UserEntity user = repository.findByEmail(email).toFuture().get();
		if (user == null)
			throw new AuthorizationException("User cannot be found. Authorization is failed.");

		return user;
	}
}
