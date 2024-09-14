package chlim.commercebackend.domain.cart.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class InvalidCartItemQuantityProblem extends Problem {

	public InvalidCartItemQuantityProblem(String message) {
		super(ProblemCategory.INVALID_REQUEST, "cart-item/invalid-quantity", message, null);
	}

	public static InvalidCartItemQuantityProblem withQuantity(Long quantity) {
		return new InvalidCartItemQuantityProblem("Invalid cart item quantity: " + quantity);
	}
}
