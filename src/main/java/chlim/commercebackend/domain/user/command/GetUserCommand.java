package chlim.commercebackend.domain.user.command;

import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetUserCommand extends SelfValidating<GetUserCommand> {

	@NotNull
	private final Long id;

	public GetUserCommand(Long id) {
		this.id = id;
		validateAndIfViolatedThrow();
	}
}
