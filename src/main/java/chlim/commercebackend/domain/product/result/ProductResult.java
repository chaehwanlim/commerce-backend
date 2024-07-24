package chlim.commercebackend.domain.product.result;

import chlim.commercebackend.domain.product.entity.Product;
import lombok.Builder;

@Builder
public record ProductResult(
	Long id,
	String name,
	String description,
	Long price,
	Long quantity
) {

	public static ProductResult from(Product product) {
		return new ProductResult(
			product.getId(),
			product.getName(),
			product.getDescription(),
			product.getPrice(),
			product.getQuantity()
		);
	}
}
