package chlim.commercebackend.domain.product.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.product.command.GetProductCommand;
import chlim.commercebackend.domain.product.problem.ProductNotFoundProblem;
import chlim.commercebackend.domain.product.repository.ProductRepository;
import chlim.commercebackend.domain.product.result.ProductResult;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetProduct {

	private final ProductRepository productRepository;

	public ProductResult execute(GetProductCommand command) {
		return productRepository.findById(command.getId())
				.map(ProductResult::from)
				.orElseThrow(() -> new ProductNotFoundProblem("Product not found with id: " + command.getId()));
	}
}
