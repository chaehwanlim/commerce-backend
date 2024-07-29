package chlim.commercebackend.presentation.restapi.product;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chlim.commercebackend.domain.product.command.CreateProductCommand;
import chlim.commercebackend.domain.product.command.FindProductsCommand;
import chlim.commercebackend.domain.product.command.GetProductCommand;
import chlim.commercebackend.domain.product.result.CreateProductResult;
import chlim.commercebackend.domain.product.result.ProductResult;
import chlim.commercebackend.domain.product.usecase.CreateProduct;
import chlim.commercebackend.domain.product.usecase.FindProducts;
import chlim.commercebackend.domain.product.usecase.GetProduct;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import chlim.commercebackend.presentation.restapi.product.request.CreateProductRequest;
import chlim.commercebackend.presentation.restapi.product.request.FindProductsRequest;
import chlim.commercebackend.presentation.restapi.product.response.CreateProductResponse;
import chlim.commercebackend.presentation.restapi.product.response.FindProductsResponse;
import chlim.commercebackend.presentation.restapi.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

	private final CreateProduct createProduct;
	private final FindProducts findProducts;
	private final GetProduct getProduct;

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

	@PostMapping("/find")
	public CommonResponse<FindProductsResponse> findProducts(@RequestBody FindProductsRequest request) {
		FindProductsCommand command = FindProductsCommand.builder()
			.name(request.name())
			.page(request.page())
			.size(request.size())
			.build();

		Page<ProductResult> result = findProducts.execute(command);

		return CommonResponse.success("Find products success", FindProductsResponse.from(result));
	}

	@GetMapping("/{id}")
	public CommonResponse<ProductResponse> getProduct(@PathVariable Long id) {
		GetProductCommand command = new GetProductCommand(id);

		ProductResult result = getProduct.execute(command);

		return CommonResponse.success("Get product success", ProductResponse.from(result));
	}
}
