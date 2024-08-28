package chlim.commercebackend.domain.verification.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.verification.command.SendPhoneVerificationMessageCommand;
import chlim.commercebackend.domain.verification.domainservice.SendPhoneVerificationMessageService;
import chlim.commercebackend.domain.verification.entity.VerificationMessage;
import chlim.commercebackend.domain.verification.problem.VerificationAttemptExceededLimitProblem;
import chlim.commercebackend.domain.verification.repository.VerificationMessageRepository;
import chlim.commercebackend.domain.verification.result.SendPhoneVerificationMessageResult;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SendPhoneVerificationMessage {

	private static final int MAX_VERIFICATION_MESSAGES_PER_DAY = 5;

	private final VerificationMessageRepository verificationMessageRepository;
	private final SendPhoneVerificationMessageService sendPhoneVerificationMessageService;

	public SendPhoneVerificationMessageResult execute(SendPhoneVerificationMessageCommand command) {
		List<VerificationMessage> messages = verificationMessageRepository.findVerificationMessagesSentTodayTo(
			command.getPhoneNumber());

		if (messages.size() >= MAX_VERIFICATION_MESSAGES_PER_DAY) {
			throw new VerificationAttemptExceededLimitProblem();
		}

		VerificationMessage verificationMessage = VerificationMessage.forPhoneVerification(command.getPhoneNumber());
		verificationMessageRepository.save(verificationMessage);

		sendPhoneVerificationMessageService.sendPhoneVerificationMessage(verificationMessage);

		return new SendPhoneVerificationMessageResult(verificationMessage.getExpiresAt());
	}
}
