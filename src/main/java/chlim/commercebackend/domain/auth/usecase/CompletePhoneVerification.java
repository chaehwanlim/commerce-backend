package chlim.commercebackend.domain.auth.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.auth.command.CompletePhoneVerificationCommand;
import chlim.commercebackend.domain.auth.entity.VerificationMessage;
import chlim.commercebackend.domain.auth.problem.VerificationMessageNotFoundProblem;
import chlim.commercebackend.domain.auth.repository.VerificationMessageRepository;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompletePhoneVerification {

	private final UserRepository userRepository;
	private final VerificationMessageRepository verificationMessageRepository;

	public void execute(CompletePhoneVerificationCommand command) {
		User user = userRepository.findById(command.getUserId())
			.orElseThrow(() -> UserNotFoundProblem.withId(command.getUserId()));

		VerificationMessage message = verificationMessageRepository.findByReceiverOrderByCreatedAtDesc(
			command.getPhoneNumber())
			.orElseThrow(() -> VerificationMessageNotFoundProblem.forReceiver(command.getPhoneNumber()));

		user.completePhoneVerification(message, command.getCode());
	}
}
