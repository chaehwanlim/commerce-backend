package chlim.commercebackend.presentation.restapi.product.request;

public record CreateProductRequest(
	String name,
	String description,
	Long price,
	Long quantity
) {
}
