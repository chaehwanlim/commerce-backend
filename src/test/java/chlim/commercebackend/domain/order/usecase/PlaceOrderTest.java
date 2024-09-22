package chlim.commercebackend.domain.order.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import chlim.commercebackend.domain.order.command.PlaceOrderCommand;
import chlim.commercebackend.domain.order.entity.Order;
import chlim.commercebackend.domain.order.repository.OrderRepository;
import chlim.commercebackend.domain.product.entity.Product;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.testfixtures.domain.ProductFixture;
import chlim.commercebackend.testfixtures.domain.UserFixture;

@ExtendWith(MockitoExtension.class)
class PlaceOrderTest {

	private static final PlaceOrderCommand command = PlaceOrderCommand.builder()
		.userId(1L)
		.selectedProductIds(List.of(1L, 2L))
		.build();

	@InjectMocks
	private PlaceOrder placeOrder;

	@Mock
	private UserRepository userRepository;

	@Mock
	private OrderRepository orderRepository;

	@Test
	@DisplayName("유저가 존재하지 않으면 주문서를 생성할 수 없다.")
	void failOnUserNotFound() {
		assertThatThrownBy(() -> placeOrder.execute(command))
			.isInstanceOf(UserNotFoundProblem.class);
	}

	@Test
	@DisplayName("유저가 존재하면 주문서를 생성한다.")
	void successOnUserFound() {
		User user = UserFixture.createUser();
		Product product1 = ProductFixture.createProductById(1L);
		user.addProductInCart(product1, 2L);
		Product product2 = ProductFixture.createProductById(1L);
		user.addProductInCart(product2, 3L);
		Order order = Order.from(user, command.getSelectedProductIds());

		when(userRepository.findById(command.getUserId())).thenReturn(Optional.of(user));
		when(orderRepository.save(any())).thenReturn(order);

		placeOrder.execute(command);

		verify(orderRepository).save(any());
	}
}
