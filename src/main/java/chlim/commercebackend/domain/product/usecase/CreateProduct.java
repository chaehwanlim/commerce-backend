package chlim.commercebackend.domain.product.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.product.command.CreateProductCommand;
import chlim.commercebackend.domain.product.entity.Product;
import chlim.commercebackend.domain.product.repository.ProductRepository;
import chlim.commercebackend.domain.product.result.CreateProductResult;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateProduct {

	private final ProductRepository productRepository;

	public CreateProductResult execute(CreateProductCommand command) {
		Product product = productRepository.save(Product.builder()
				.name(command.getName())
				.description(command.getDescription())
				.price(command.getPrice())
				.quantity(command.getQuantity())
				.build());

		return new CreateProductResult(product.getId());
	}
}
