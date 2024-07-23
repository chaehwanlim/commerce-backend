package chlim.commercebackend.presentation.restapi.product;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chlim.commercebackend.domain.product.command.CreateProductCommand;
import chlim.commercebackend.domain.product.result.CreateProductResult;
import chlim.commercebackend.domain.product.usecase.CreateProduct;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import chlim.commercebackend.presentation.restapi.product.request.CreateProductRequest;
import chlim.commercebackend.presentation.restapi.product.response.CreateProductResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

	private final CreateProduct createProduct;

	@PostMapping
	public CommonResponse<CreateProductResponse> createProduct(@RequestBody CreateProductRequest request) {
		CreateProductCommand command = CreateProductCommand.builder()
			.name(request.name())
			.description(request.description())
			.price(request.price())
			.quantity(request.quantity())
			.build();

		CreateProductResult result = createProduct.execute(command);

		return CommonResponse.success("Create product success", new CreateProductResponse(result.productId()));
	}

}
