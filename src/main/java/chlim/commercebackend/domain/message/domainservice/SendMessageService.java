package chlim.commercebackend.domain.message.domainservice;

public interface SendMessageService {

	void sendMessage(String receiver, String content);
}
