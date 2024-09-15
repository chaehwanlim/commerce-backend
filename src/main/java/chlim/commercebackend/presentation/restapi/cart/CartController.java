package chlim.commercebackend.presentation.restapi.cart;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chlim.commercebackend.domain.cart.command.AddProductInCartCommand;
import chlim.commercebackend.domain.cart.command.RemoveProductInCartCommand;
import chlim.commercebackend.domain.cart.usecase.AddProductInCart;
import chlim.commercebackend.domain.cart.usecase.RemoveProductInCart;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import chlim.commercebackend.presentation.restapi.cart.request.AddProductInCartRequest;
import chlim.commercebackend.presentation.restapi.cart.request.RemoveProductInCartRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

	private final AddProductInCart addProductInCart;
	private final RemoveProductInCart removeProductInCart;

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

	@PostMapping("/remove-product")
	public CommonResponse removeProductInCart(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody RemoveProductInCartRequest request) {
		RemoveProductInCartCommand command = RemoveProductInCartCommand.builder()
			.userId(Long.valueOf(userDetails.getUsername()))
			.productId(request.productId())
			.quantity(request.quantity())
			.build();

		removeProductInCart.execute(command);

		return CommonResponse.success("Remove product in cart success", null);
	}
}
