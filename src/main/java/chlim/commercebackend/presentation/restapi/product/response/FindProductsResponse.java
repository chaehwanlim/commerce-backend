package chlim.commercebackend.presentation.restapi.product.response;

import java.util.List;

import org.springframework.data.domain.Page;

import chlim.commercebackend.domain.product.result.ProductResult;

public record FindProductsResponse(
	Long count,
	List<ProductResponse> products
) {

	public static FindProductsResponse from(Page<ProductResult> page) {
		return new FindProductsResponse(
			page.getTotalElements(),
			page.getContent().stream().map(ProductResponse::from).toList()
		);
	}
}
