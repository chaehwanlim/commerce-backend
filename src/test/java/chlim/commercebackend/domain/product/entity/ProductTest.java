package chlim.commercebackend.domain.product.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import chlim.commercebackend.domain.product.problem.InvalidProductQuantityProblem;
import chlim.commercebackend.domain.product.problem.ProductNotPurchasableProblem;
import chlim.commercebackend.testfixtures.domain.ProductFixture;

class ProductTest {

	@Nested
	class EnsurePurchasable {

		@DisplayName("구매할 수량이 유효하지 않으면 구매할 수 없다.")
		@ParameterizedTest(name = "{0}이면 구매할 수 없다.")
		@NullSource
		@ValueSource(longs = {-1L, -2L})
		void failOnInvalidQuantity(Long quantity) {
			Product product = ProductFixture.createProduct();

			assertThatThrownBy(() -> product.ensurePurchasable(quantity))
				.isInstanceOf(InvalidProductQuantityProblem.class);
		}

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