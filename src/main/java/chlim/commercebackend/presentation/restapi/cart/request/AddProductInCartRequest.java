package chlim.commercebackend.presentation.restapi.cart.request;

public record AddProductInCartRequest(
	Long productId,
	Long quantity
) {
}
