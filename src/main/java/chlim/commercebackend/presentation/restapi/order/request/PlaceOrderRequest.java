package chlim.commercebackend.presentation.restapi.order.request;

import java.util.List;

public record PlaceOrderRequest(
	List<Long> selectedProductIds
) {
}
