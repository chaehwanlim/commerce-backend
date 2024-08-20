package chlim.commercebackend.domain.cart.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import chlim.commercebackend.domain.cart.command.AddProductInCartCommand;
import chlim.commercebackend.domain.product.problem.ProductNotFoundProblem;
import chlim.commercebackend.domain.product.repository.ProductRepository;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.testfixtures.domain.ProductFixture;
import chlim.commercebackend.testfixtures.domain.UserFixture;

@ExtendWith(MockitoExtension.class)
public class AddProductInCartTest {

	private static final AddProductInCartCommand command = AddProductInCartCommand.builder()
		.userId(1L)
		.productId(10L)
		.quantity(5L)
		.build();

	@InjectMocks
	private AddProductInCart addProductInCart;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ProductRepository productRepository;

	@Test
	@DisplayName("유저가 존재하지 않으면 장바구니에 상품을 추가할 수 없다.")
	void failOnUserNotFound() {
		assertThatThrownBy(() -> addProductInCart.execute(command))
			.isInstanceOf(UserNotFoundProblem.class);
	}

	@Nested
	@DisplayName("유저가 존재하고")
	class UserExists {

		@BeforeEach
		void setUp() {
			when(userRepository.findById(1L)).thenReturn(Optional.of(UserFixture.createUser()));
		}

		@Test
		@DisplayName("상품이 존재하지 않으면 장바구니에 상품을 추가할 수 없다.")
		void failOnProductNotFound() {
			assertThatThrownBy(() -> addProductInCart.execute(command))
				.isInstanceOf(ProductNotFoundProblem.class);
		}

		@Test
		@DisplayName("상품이 존재하면 장바구니에 상품을 추가할 수 있다.")
		void success() {
			when(productRepository.findById(10L)).thenReturn(Optional.of(ProductFixture.createProduct()));

			assertDoesNotThrow(() -> addProductInCart.execute(command));
		}
	}
}
