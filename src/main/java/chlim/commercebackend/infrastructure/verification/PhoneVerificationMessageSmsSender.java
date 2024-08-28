package chlim.commercebackend.infrastructure.verification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

import chlim.commercebackend.domain.verification.domainservice.SendPhoneVerificationMessageService;
import chlim.commercebackend.domain.verification.entity.VerificationMessage;
import chlim.commercebackend.domain.verification.problem.SendVerificationMessageFailedProblem;

@Service
public class PhoneVerificationMessageSmsSender implements SendPhoneVerificationMessageService {

	private final String from;
	private final DefaultMessageService smsSender;

	public PhoneVerificationMessageSmsSender(
		@Value("${sms.from}") String from,
		@Value("${sms.api.key}") String apiKey,
		@Value("${sms.api.secret}") String apiSecretKey,
		@Value("${sms.api.url}") String apiUrl) {

		this.from = from;
		this.smsSender = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, apiUrl);
	}

	@Override
	public void sendPhoneVerificationMessage(VerificationMessage verificationMessage) {
		Message message = new Message();

		message.setFrom(from);
		message.setTo(verificationMessage.getReceiver());
		message.setText(verificationMessage.getContent());

		try {
			smsSender.sendOne(new SingleMessageSendingRequest(message));
		} catch (Exception e) {
			throw new SendVerificationMessageFailedProblem(e);
		}
	}
}
