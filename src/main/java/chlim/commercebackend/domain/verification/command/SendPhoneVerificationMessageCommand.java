package chlim.commercebackend.domain.verification.command;

import chlim.commercebackend.domain.common.SelfValidating;
import chlim.commercebackend.domain.verification.usecase.SendPhoneVerificationMessage;
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
