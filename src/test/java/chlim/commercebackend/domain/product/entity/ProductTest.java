package chlim.commercebackend.domain.product.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import chlim.commercebackend.domain.product.problem.ProductNotPurchasableProblem;
import chlim.commercebackend.testfixtures.domain.ProductFixture;

class ProductTest {

	@Nested
	class EnsurePurchasable {

		@Test
		@DisplayName("구매 가능한 수량보다 더 많은 수량을 구매할 수 없다.")
		void failOnPurchasingMoreThanAvailableQuantity() {
			Product product = ProductFixture.createProduct();

			assertThatThrownBy(() -> product.ensurePurchasable(11L))
				.isInstanceOf(ProductNotPurchasableProblem.class);
		}

		@Test
		@DisplayName("구매 가능한 수량을 구매할 수 있다.")
		void successOnPurchasingAvailableQuantity() {
			Product product = ProductFixture.createProduct();

			assertDoesNotThrow(() -> product.ensurePurchasable(10L));
		}
	}
}