package chlim.commercebackend.domain.order.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import chlim.commercebackend.domain.order.problem.OrderFromEmptyCartProblem;
import chlim.commercebackend.domain.product.entity.Product;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.testfixtures.domain.ProductFixture;
import chlim.commercebackend.testfixtures.domain.UserFixture;

class OrderTest {

	@Nested
	class From {

		@Test
		@DisplayName("유저의 카트가 비었으면 주문서를 생성할 수 없다.")
		void failOnEmptyCart() {
			User user = UserFixture.createUser();
			List<Long> selectedProductIds = List.of(1L, 2L);

			assertThatThrownBy(() -> Order.from(user, selectedProductIds))
				.isInstanceOf(OrderFromEmptyCartProblem.class);
		}

		@Test
		@DisplayName("유저와 선택한 상품 ID 목록을 받아 주문서를 생성한다.")
		void success() {
			User user = UserFixture.createUser();
			Product product1 = ProductFixture.createProductById(1L);
			user.addProductInCart(product1, 2L);
			Product product2 = ProductFixture.createProductById(2L);
			user.addProductInCart(product2, 3L);
			List<Long> selectedProductIds = List.of(1L, 2L);

			Order order = Order.from(user, selectedProductIds);

			assertThat(order.getUser()).isEqualTo(user);
			assertThat(order.getItems()).hasSize(2);
		}
	}
}