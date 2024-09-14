package chlim.commercebackend.domain.cart.entity;

import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

import chlim.commercebackend.domain.cart.problem.InvalidCartItemQuantityProblem;
import chlim.commercebackend.domain.product.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;

	private Long quantity;

	@Builder
	public CartItem(Cart cart, Product product, Long quantity) {
		this.cart = cart;
		this.product = product;
		this.quantity = ObjectUtils.defaultIfNull(quantity, 0L);
	}

	public void increaseQuantity(Long quantity) {
		if (quantity == null || quantity < 0) {
			throw InvalidCartItemQuantityProblem.withQuantity(quantity);
		}

		this.quantity += quantity;

		product.ensurePurchasable(this.quantity);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CartItem cartItem = (CartItem)o;
		return Objects.equals(id, cartItem.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
