package chlim.commercebackend.domain.cart.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.cart.command.RemoveProductInCartCommand;
import chlim.commercebackend.domain.product.entity.Product;
import chlim.commercebackend.domain.product.problem.ProductNotFoundProblem;
import chlim.commercebackend.domain.product.repository.ProductRepository;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RemoveProductInCart {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public void execute(RemoveProductInCartCommand command) {
		User user = userRepository.findById(command.getUserId())
			.orElseThrow(() -> UserNotFoundProblem.withId(command.getUserId()));

		Product product = productRepository.findById(command.getProductId())
				.orElseThrow(() -> ProductNotFoundProblem.withId(command.getProductId()));

		user.removeProductInCart(product, command.getQuantity());
	}
}
