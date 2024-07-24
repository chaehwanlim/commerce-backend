package chlim.commercebackend.presentation.restapi.product.request;

public record FindProductsRequest(
	String name,
	Integer page,
	Integer size
) {
}
