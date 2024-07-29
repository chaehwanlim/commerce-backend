package chlim.commercebackend.testfixtures.domain;

import chlim.commercebackend.domain.product.entity.Product;

public class ProductFixture {

	public static final String NAME = "product";
	public static final String DESCRIPTION = "description";
	public static final Long PRICE = 1000L;
	public static final Long QUANTITY = 10L;

	public static Product createProduct() {
		return Product.builder()
			.name(NAME)
			.description(DESCRIPTION)
			.price(PRICE)
			.quantity(QUANTITY)
			.build();
	}
}
