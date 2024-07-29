package chlim.commercebackend.domain.product.command;

import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GetProductCommand extends SelfValidating<GetProductCommand> {

	@NotNull
	private final Long id;

	public GetProductCommand(Long id) {
		this.id = id;
		validateAndIfViolatedThrow();
	}
}
