package chlim.commercebackend.domain.verification.command;

import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompletePhoneVerificationCommand extends SelfValidating<CompletePhoneVerificationCommand> {

	@NotBlank
	private final Long userId;

	@NotBlank
	private final String phoneNumber;

	@NotBlank
	private final String code;

	@Builder
	public CompletePhoneVerificationCommand(Long userId, String phoneNumber, String code) {
		this.userId = userId;
		this.phoneNumber = phoneNumber;
		this.code = code;
	}
}
