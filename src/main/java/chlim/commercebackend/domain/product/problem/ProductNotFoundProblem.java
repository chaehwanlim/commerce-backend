package chlim.commercebackend.domain.product.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class ProductNotFoundProblem extends Problem {

	public ProductNotFoundProblem(String message) {
		super(ProblemCategory.NOT_FOUND, "product/not-found", message, null);
	}

	public static ProductNotFoundProblem withId(Long id) {
		return new ProductNotFoundProblem("Product is not found with id: " + id);
	}
}
