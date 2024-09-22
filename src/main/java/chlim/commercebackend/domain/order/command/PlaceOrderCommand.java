package chlim.commercebackend.domain.order.command;

import java.util.List;

import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceOrderCommand extends SelfValidating<PlaceOrderCommand> {

	@NotNull
	private final Long userId;

	@NotEmpty
	private final List<Long> selectedProductIds;

	@Builder
	public PlaceOrderCommand(Long userId, List<Long> selectedProductIds) {
		this.userId = userId;
		this.selectedProductIds = selectedProductIds;

		validateAndIfViolatedThrow();
	}
}
