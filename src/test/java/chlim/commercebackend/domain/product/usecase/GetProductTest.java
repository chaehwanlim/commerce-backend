package chlim.commercebackend.domain.product.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import chlim.commercebackend.domain.product.command.GetProductCommand;
import chlim.commercebackend.domain.product.entity.Product;
import chlim.commercebackend.domain.product.problem.ProductNotFoundProblem;
import chlim.commercebackend.domain.product.repository.ProductRepository;
import chlim.commercebackend.domain.product.result.ProductResult;
import chlim.commercebackend.testfixtures.domain.ProductFixture;

@ExtendWith(MockitoExtension.class)
class GetProductTest {

	@InjectMocks
	private GetProduct getProduct;

	@Mock
	private ProductRepository productRepository;

	@Test
	@DisplayName("잘못된 id의 상품 정보는 조회할 수 없다.")
	void fail() {
		GetProductCommand command = new GetProductCommand(1L);

		assertThatThrownBy(() -> getProduct.execute(command))
			.isInstanceOf(ProductNotFoundProblem.class);
	}

	@Test
	@DisplayName("정상적인 id의 상품 정보는 조회할 수 있다.")
	void success() {
		Product product = ProductFixture.createProduct();

		when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		GetProductCommand command = new GetProductCommand(1L);

		ProductResult result = getProduct.execute(command);

		assertThat(result).isNotNull();
		assertThat(result.name()).isEqualTo(product.getName());
		assertThat(result.price()).isEqualTo(product.getPrice());
	}

}