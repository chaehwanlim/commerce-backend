package chlim.commercebackend.domain.product.entity;

import org.apache.commons.lang3.ObjectUtils;

import chlim.commercebackend.domain.common.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private Long price;

	private Long quantity;

	@Builder
	public Product(Long id, String name, String description, Long price, Long quantity) {
		validateName(name);
		validatePrice(price);
		validateQuantity(quantity);

		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

	private void validateName(String name) {
		if (ObjectUtils.isEmpty(name)) {
			throw new IllegalArgumentException("Product name must not be empty");
		}
	}

	private void validatePrice(Long price) {
		if (price == null || price < 0) {
			throw new IllegalArgumentException("Product price must not be null or negative");
		}
	}

	private void validateQuantity(Long quantity) {
		if (quantity == null || quantity < 0) {
			throw new IllegalArgumentException("Product quantity must not be null or negative");
		}
	}
}
