package chlim.commercebackend.domain.product.repository;

import org.springframework.data.domain.Page;

import chlim.commercebackend.domain.product.command.FindProductsCommand;
import chlim.commercebackend.domain.product.result.ProductResult;

public interface ProductRepositoryCustom {

	Page<ProductResult> findAllByCommand(FindProductsCommand command);
}
