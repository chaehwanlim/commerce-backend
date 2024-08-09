package chlim.commercebackend.domain.cart.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import chlim.commercebackend.testfixtures.domain.CartItemFixture;

class CartItemTest {

	@Nested
	class IncreaseQuantity {

		@Test
		@DisplayName("수량을 증가시킬 수 있다.")
		void success() {
			CartItem cartItem = CartItemFixture.createCartItem();

			cartItem.increaseQuantity(1L);

			assertThat(cartItem.getQuantity()).isEqualTo(2L);
		}
	}
}
