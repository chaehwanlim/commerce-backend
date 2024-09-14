package chlim.commercebackend.domain.cart.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import chlim.commercebackend.domain.cart.problem.InvalidCartItemQuantityProblem;
import chlim.commercebackend.testfixtures.domain.CartItemFixture;

class CartItemTest {

	@Nested
	class IncreaseQuantity {

		@Test
		@DisplayName("수량을 null이나 음수만큼 증가시킬 수 없다.")
		void failOnInvalidQuantity() {
			CartItem cartItem = CartItemFixture.createCartItem();

			assertThatThrownBy(() -> cartItem.increaseQuantity(-1L))
				.isInstanceOf(InvalidCartItemQuantityProblem.class);
		}

		@Test
		@DisplayName("수량을 양수만큼 증가시킬 수 있다.")
		void success() {
			CartItem cartItem = CartItemFixture.createCartItem();

			cartItem.increaseQuantity(1L);

			assertThat(cartItem.getQuantity()).isEqualTo(2L);
		}
	}
}
