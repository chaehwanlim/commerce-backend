package chlim.commercebackend.presentation.restapi.order;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chlim.commercebackend.domain.order.command.PlaceOrderCommand;
import chlim.commercebackend.domain.order.result.PlaceOrderResult;
import chlim.commercebackend.domain.order.usecase.PlaceOrder;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import chlim.commercebackend.presentation.restapi.order.request.PlaceOrderRequest;
import chlim.commercebackend.presentation.restapi.order.response.PlaceOrderResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final PlaceOrder placeOrder;

	@PostMapping
	public CommonResponse placeOrder(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody PlaceOrderRequest request
	) {
		PlaceOrderCommand command = PlaceOrderCommand.builder()
			.userId(Long.valueOf(userDetails.getUsername()))
			.selectedProductIds(request.selectedProductIds())
			.build();

		PlaceOrderResult result = placeOrder.execute(command);

		PlaceOrderResponse response = new PlaceOrderResponse(result.orderId());

		return CommonResponse.success("Place order success", response);
	}
}
