package chlim.commercebackend.domain.auth.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.auth.command.SendPhoneVerificationMessageCommand;
import chlim.commercebackend.domain.auth.entity.VerificationMessage;
import chlim.commercebackend.domain.auth.problem.VerificationAttemptExceededLimitProblem;
import chlim.commercebackend.domain.auth.repository.VerificationMessageRepository;
import chlim.commercebackend.domain.auth.result.SendPhoneVerificationMessageResult;
import chlim.commercebackend.domain.message.domainservice.SendMessageService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SendPhoneVerificationMessage {

	private static final int MAX_VERIFICATION_MESSAGES_PER_DAY = 5;

	private final VerificationMessageRepository verificationMessageRepository;
	private final SendMessageService sendMessageService;

	public SendPhoneVerificationMessageResult execute(SendPhoneVerificationMessageCommand command) {
		List<VerificationMessage> messages = verificationMessageRepository.findVerificationMessagesSentTodayTo(
			command.getPhoneNumber());

		if (messages.size() >= MAX_VERIFICATION_MESSAGES_PER_DAY) {
			throw new VerificationAttemptExceededLimitProblem();
		}

		VerificationMessage verificationMessage = VerificationMessage.forPhoneVerification(command.getPhoneNumber());
		verificationMessageRepository.save(verificationMessage);

		sendMessageService.sendMessage(verificationMessage.getReceiver(), verificationMessage.getContent());

		return new SendPhoneVerificationMessageResult(verificationMessage.getExpiresAt());
	}
}
