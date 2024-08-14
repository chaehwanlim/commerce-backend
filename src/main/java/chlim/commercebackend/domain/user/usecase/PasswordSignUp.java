package chlim.commercebackend.domain.user.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.user.command.PasswordSignUpCommand;
import chlim.commercebackend.domain.user.domainservice.CreateIdTokenService;
import chlim.commercebackend.domain.user.domainservice.EncodePasswordService;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.problem.UserAlreadyExistsProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.domain.user.result.SignUpResult;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordSignUp {

	private final UserRepository userRepository;
	private final EncodePasswordService encodePasswordService;
	private final CreateIdTokenService createIdTokenService;

	public SignUpResult execute(PasswordSignUpCommand command) {
		userRepository.findByEmail(command.getEmail())
			.ifPresent(user -> {
				throw UserAlreadyExistsProblem.withEmail(command.getEmail());
			});

		User user = userRepository.save(User.builder()
			.email(command.getEmail())
			.name(command.getName())
			.build());

		String encodedPassword = encodePasswordService.encodePassword(command.getPassword());
		user.addPasswordAuthentication(encodedPassword);

		String accessToken = createIdTokenService.createIdToken(user);

		return new SignUpResult(accessToken);
	}
}
