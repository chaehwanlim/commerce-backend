package chlim.commercebackend.domain.user.command;

import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordSignUpCommand extends SelfValidating<PasswordSignUpCommand> {

	@NotBlank
	private final String email;

	@NotBlank
	private final String name;

	@NotBlank
	private final String password;

	@Builder
	public PasswordSignUpCommand(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;

		validateAndIfViolatedThrow();
	}
}
