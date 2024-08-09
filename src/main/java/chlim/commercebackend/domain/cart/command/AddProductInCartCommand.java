package chlim.commercebackend.domain.cart.command;

import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddProductInCartCommand extends SelfValidating<AddProductInCartCommand> {

	@NotNull
	private final Long userId;

	@NotNull
	private final Long productId;

	@NotNull
	private final Long quantity;

	@Builder
	public AddProductInCartCommand(Long userId, Long productId, Long quantity) {
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;

		validateAndIfViolatedThrow();
	}
}
