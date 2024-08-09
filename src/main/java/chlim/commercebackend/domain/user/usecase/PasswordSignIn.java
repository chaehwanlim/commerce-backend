package chlim.commercebackend.domain.user.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.user.command.PasswordSignInCommand;
import chlim.commercebackend.domain.user.domainservice.CreateIdTokenService;
import chlim.commercebackend.domain.user.domainservice.VerifyPasswordService;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.domain.user.result.SignInResult;
import chlim.commercebackend.domain.userauthentication.problem.UserAuthenticationIncorrectProblem;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordSignIn {

	private final UserRepository userRepository;
	private final CreateIdTokenService createIdTokenService;
	private final VerifyPasswordService verifyPasswordService;

	public SignInResult execute(PasswordSignInCommand command) {
		User user = userRepository.findByEmail(command.getEmail())
			.orElseThrow(() -> UserNotFoundProblem.withEmail(command.getEmail()));

		if (!verifyPasswordService.verify(command.getPassword(), user.getEncodedPassword())) {
			throw new UserAuthenticationIncorrectProblem("Password is incorrect");
		}

		String accessToken = createIdTokenService.createIdToken(user);

		return new SignInResult(accessToken);
	}
}
