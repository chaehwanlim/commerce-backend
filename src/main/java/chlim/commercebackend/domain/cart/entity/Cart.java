package chlim.commercebackend.domain.cart.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import chlim.commercebackend.domain.common.AbstractEntity;
import chlim.commercebackend.domain.product.entity.Product;
import chlim.commercebackend.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Cart extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CartItem> items = new HashSet<>();

	public Cart(User user) {
		this.user = user;
	}

	public void add(Product product, Long quantity) {
		CartItem cartItem = this.items.stream().filter(item -> item.getProduct().equals(product))
			.findFirst()
			.orElse(CartItem.builder()
				.cart(this)
				.product(product)
				.build()
			);

		cartItem.increaseQuantity(quantity);

		this.items.add(cartItem);
	}

	public void remove(Product product, Long quantity) {
		if (quantity == null) {
			this.items.removeIf(item -> item.getProduct().equals(product));
			return;
		}

		this.items.stream().filter(item -> item.getProduct().equals(product))
			.findFirst()
			.ifPresent(item -> item.decreaseQuantity(quantity));

		this.items.removeIf(CartItem::isEmpty);
	}

	public boolean isEmpty() {
		return this.items.isEmpty();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Cart cart = (Cart)o;
		return Objects.equals(id, cart.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
