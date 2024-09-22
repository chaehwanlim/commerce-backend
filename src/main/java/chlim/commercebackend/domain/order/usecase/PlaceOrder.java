package chlim.commercebackend.domain.order.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.order.command.PlaceOrderCommand;
import chlim.commercebackend.domain.order.entity.Order;
import chlim.commercebackend.domain.order.repository.OrderRepository;
import chlim.commercebackend.domain.order.result.PlaceOrderResult;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceOrder {

	private final UserRepository userRepository;
	private final OrderRepository orderRepository;

	public PlaceOrderResult execute(PlaceOrderCommand command) {
		User user = userRepository.findById(command.getUserId())
			.orElseThrow(() -> UserNotFoundProblem.withId(command.getUserId()));

		Order order = orderRepository.save(Order.from(user, command.getSelectedProductIds()));

		return new PlaceOrderResult(order.getId());
	}
}
