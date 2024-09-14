package chlim.commercebackend.domain.product.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class InvalidProductQuantityProblem extends Problem {

	public InvalidProductQuantityProblem(String message) {
		super(ProblemCategory.INVALID_REQUEST, "product/invalid-quantity", message, null);
	}

	public static InvalidProductQuantityProblem withQuantity(Long quantity) {
		return new InvalidProductQuantityProblem("Invalid product quantity: " + quantity);
	}
}
