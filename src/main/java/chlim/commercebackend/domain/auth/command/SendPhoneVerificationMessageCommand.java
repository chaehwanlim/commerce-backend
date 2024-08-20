package chlim.commercebackend.domain.auth.command;

import chlim.commercebackend.domain.auth.usecase.SendPhoneVerificationMessage;
import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SendPhoneVerificationMessageCommand extends SelfValidating<SendPhoneVerificationMessage> {

	@NotBlank
	private final String phoneNumber;

	public SendPhoneVerificationMessageCommand(String phoneNumber) {
		this.phoneNumber = phoneNumber;

		validateAndIfViolatedThrow();
	}
}
