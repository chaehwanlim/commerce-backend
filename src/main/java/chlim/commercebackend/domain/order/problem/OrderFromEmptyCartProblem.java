package chlim.commercebackend.domain.order.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class OrderFromEmptyCartProblem extends Problem {

	public OrderFromEmptyCartProblem() {
		super(ProblemCategory.UNPROCESSABLE, "order/empty-cart", "Cannot create order from empty cart", null);
	}
}
