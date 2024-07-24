package chlim.commercebackend.presentation.restapi.product.response;

import chlim.commercebackend.domain.product.result.ProductResult;

public record ProductResponse(
	Long id,
	String name,
	String description,
	Long price,
	Long quantity
) {

	public static ProductResponse from(ProductResult result) {
		return new ProductResponse(
			result.id(),
			result.name(),
			result.description(),
			result.price(),
			result.quantity()
		);
	}
}
