package chlim.commercebackend.presentation.restapi.cart.request;

public record RemoveProductInCartRequest(
	Long productId,
	Long quantity
) {
}
