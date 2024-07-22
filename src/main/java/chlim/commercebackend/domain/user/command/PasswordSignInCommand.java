package chlim.commercebackend.domain.user.command;

import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordSignInCommand extends SelfValidating<PasswordSignInCommand> {

	@NotBlank
	private final String email;

	@NotBlank
	private final String password;

	@Builder
	public PasswordSignInCommand(String email, String password) {
		this.email = email;
		this.password = password;

		validateAndIfViolatedThrow();
	}
}
