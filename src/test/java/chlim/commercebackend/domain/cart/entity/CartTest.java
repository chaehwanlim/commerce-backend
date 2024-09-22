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
			Product product1 = ProductFixture.createProductById(1L);
			Product product2 = ProductFixture.createProductById(2L);

			cart.add(product1, 2L);
			cart.add(product2, 3L);

			assertThat(cart.getItems()).hasSize(2);
			assertThat(cart.getItems().stream().map(item -> item.getProduct())).contains(product1, product2);
			CartItem cartItem1 = cart.getItems().stream().filter(item -> item.getProduct().equals(product1)).findFirst().get();
			assertThat(cartItem1.getQuantity()).isEqualTo(2L);
			CartItem cartItem2 = cart.getItems().stream().filter(item -> item.getProduct().equals(product2)).findFirst().get();
			assertThat(cartItem2.getQuantity()).isEqualTo(3L);
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

	@Nested
	class Remove {

		@Test
		@DisplayName("카트에 상품이 있다면 수량을 감소시킨다.")
		void removeExistingProduct() {
			Cart cart = CartFixture.createCart();
			Product product = ProductFixture.createProductById(1L);
			cart.add(product, 5L);

			cart.remove(product, 2L);

			assertThat(cart.getItems()).hasSize(1);
			assertThat(cart.getItems().stream().findFirst().get().getProduct()).isEqualTo(product);
			assertThat(cart.getItems().stream().findFirst().get().getQuantity()).isEqualTo(3L);
		}

		@Test
		@DisplayName("현재 선택한 수량 이상의 수로 감소시키려고 하면 상품을 삭제한다.")
		void removeProductIfQuantityIsZero() {
			Cart cart = CartFixture.createCart();
			Product product = ProductFixture.createProductById(1L);
			cart.add(product, 2L);
			Product product2 = ProductFixture.createProductById(2L);
			cart.add(product2, 2L);

			cart.remove(product, 2L);
			cart.remove(product2, 3L);

			assertThat(cart.getItems()).isEmpty();
		}

		@Test
		@DisplayName("감소시킬 수량을 정하지 않으면 상품을 삭제한다.")
		void removeProductIfQuantityIsNull() {
			Cart cart = CartFixture.createCart();
			Product product = ProductFixture.createProductById(1L);
			cart.add(product, 2L);

			cart.remove(product, null);

			assertThat(cart.getItems()).isEmpty();
		}
	}
}