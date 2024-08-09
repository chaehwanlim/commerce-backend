package chlim.commercebackend.testfixtures.domain;

import chlim.commercebackend.domain.cart.entity.Cart;
import chlim.commercebackend.domain.cart.entity.CartItem;

public class CartItemFixture {

	public static final Cart cart = CartFixture.createCart();

	public static CartItem createCartItem() {
		return CartItem.builder()
			.cart(cart)
			.product(ProductFixture.createProduct())
			.quantity(1L)
			.build();
	}
}
