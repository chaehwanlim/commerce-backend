package chlim.commercebackend.presentation.restapi.cart;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chlim.commercebackend.domain.cart.command.AddProductInCartCommand;
import chlim.commercebackend.domain.cart.usecase.AddProductInCart;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import chlim.commercebackend.presentation.restapi.cart.request.AddProductInCartRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

	private final AddProductInCart addProductInCart;

	@PostMapping("/add-product")
	public CommonResponse addProductInCart(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody AddProductInCartRequest request) {
		AddProductInCartCommand command = AddProductInCartCommand.builder()
			.userId(Long.valueOf(userDetails.getUsername()))
			.productId(request.productId())
			.quantity(request.quantity())
			.build();

		addProductInCart.execute(command);

		return CommonResponse.success("Add product in cart success", null);
	}
}
