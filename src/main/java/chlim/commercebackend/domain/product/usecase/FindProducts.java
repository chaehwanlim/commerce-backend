package chlim.commercebackend.domain.product.usecase;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.product.command.FindProductsCommand;
import chlim.commercebackend.domain.product.repository.ProductRepository;
import chlim.commercebackend.domain.product.result.ProductResult;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindProducts {

	private final ProductRepository productRepository;

	public Page<ProductResult> execute(FindProductsCommand command) {
		return productRepository.findAllByCommand(command);
	}
}
