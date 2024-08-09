package chlim.commercebackend.testfixtures.domain;

import chlim.commercebackend.domain.cart.entity.Cart;
import chlim.commercebackend.domain.user.entity.User;

public class CartFixture {

	public static final User user = UserFixture.createUser();

	public static Cart createCart() {
		return new Cart(user);
	}
}
