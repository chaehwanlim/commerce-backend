package chlim.commercebackend.domain.cart.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import chlim.commercebackend.domain.product.entity.Product;
import chlim.commercebackend.testfixtures.domain.CartFixture;
import chlim.commercebackend.testfixtures.domain.ProductFixture;

class CartTest {

	@Nested
	class Add {

		@Test
		@DisplayName("카트에 상품이 없다면 새로 추가한다.")
		void addNewProduct() {
			Cart cart = CartFixture.createCart();
			Product product = ProductFixture.createProductById(1L);

			cart.add(product, 2L);

			assertThat(cart.getItems()).hasSize(1);
			assertThat(cart.getItems().stream().findFirst().get().getProduct()).isEqualTo(product);
			assertThat(cart.getItems().stream().findFirst().get().getQuantity()).isEqualTo(2L);
		}

		@Test
		@DisplayName("카트에 이미 상품이 있다면 수량을 증가시킨다.")
		void addExistingProduct() {
			Cart cart = CartFixture.createCart();
			Product product = ProductFixture.createProductById(1L);
			cart.add(product, 2L);

			cart.add(product, 3L);

			assertThat(cart.getItems()).hasSize(1);
			assertThat(cart.getItems().stream().findFirst().get().getProduct()).isEqualTo(product);
			assertThat(cart.getItems().stream().findFirst().get().getQuantity()).isEqualTo(5L);
		}
	}
}