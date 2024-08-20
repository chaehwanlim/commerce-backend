package chlim.commercebackend.infrastructure.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

import chlim.commercebackend.domain.message.domainservice.SendMessageService;
import chlim.commercebackend.domain.message.problem.SendMessageFailedProblem;

@Service
public class MessageServiceImpl implements SendMessageService {

	private final String from;
	private final DefaultMessageService messageService;

	public MessageServiceImpl(
		@Value("${sms.from}") String from,
		@Value("${sms.api.key}") String apiKey,
		@Value("${sms.api.secret}") String apiSecretKey,
		@Value("${sms.api.url}") String apiUrl) {

		this.from = from;
		this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, apiUrl);
	}

	@Override
	public void sendMessage(String receiver, String content) {
		Message message = new Message();

		message.setFrom(from);
		message.setTo(receiver);
		message.setText(content);

		try {
			messageService.sendOne(new SingleMessageSendingRequest(message));
		} catch (Exception e) {
			throw new SendMessageFailedProblem(e);
		}
	}
}
