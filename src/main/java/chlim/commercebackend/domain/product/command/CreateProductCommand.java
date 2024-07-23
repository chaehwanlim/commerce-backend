package chlim.commercebackend.domain.product.command;

import chlim.commercebackend.domain.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateProductCommand extends SelfValidating<CreateProductCommand> {

	@NotBlank
	private final String name;

	private final String description;

	@PositiveOrZero
	private final Long price;

	@PositiveOrZero
	private final Long quantity;

	@Builder
	public CreateProductCommand(String name, String description, Long price, Long quantity) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;

		validateAndIfViolatedThrow();
	}
}
