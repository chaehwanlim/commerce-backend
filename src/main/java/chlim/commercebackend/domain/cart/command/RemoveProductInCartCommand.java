package chlim.commercebackend.domain.cart.command;

import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RemoveProductInCartCommand extends SelfValidating<RemoveProductInCartCommand> {

	@NotNull
	private Long userId;

	@NotNull
	private Long productId;

	@PositiveOrZero
	private Long quantity;

	@Builder
	public RemoveProductInCartCommand(Long userId, Long productId, Long quantity) {
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;

		validateAndIfViolatedThrow();
	}
}
