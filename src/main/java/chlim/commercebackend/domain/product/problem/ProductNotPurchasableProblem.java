package chlim.commercebackend.domain.product.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class ProductNotPurchasableProblem extends Problem {

	public ProductNotPurchasableProblem(String message) {
		super(ProblemCategory.UNPROCESSABLE, "product/not-purchasable", message, null);
	}

	public static ProductNotPurchasableProblem withQuantity(Long quantity) {
		return new ProductNotPurchasableProblem("Product is not purchasable with quantity: " + quantity);
	}
}
