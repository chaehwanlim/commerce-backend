package chlim.commercebackend.domain.order.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import chlim.commercebackend.domain.cart.entity.Cart;
import chlim.commercebackend.domain.common.AbstractEntity;
import chlim.commercebackend.domain.order.problem.OrderFromEmptyCartProblem;
import chlim.commercebackend.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderItem> items = new HashSet<>();

	public Order(User user) {
		this.user = user;
	}

	public static Order from(User user, List<Long> selectedProductIds) {
		Cart cart = user.getCart();
		if (cart == null || cart.isEmpty()) {
			throw new OrderFromEmptyCartProblem();
		}

		Order order = new Order(user);

		Set<OrderItem> orderItems = cart.getItems().stream()
			.filter(item -> selectedProductIds.contains(item.getProduct().getId()))
			.peek(item -> item.getProduct().ensurePurchasable(item.getQuantity()))
			.map(item -> OrderItem.builder()
				.product(item.getProduct())
				.order(order)
				.quantity(item.getQuantity())
				.build())
			.collect(Collectors.toSet());

		order.items.addAll(orderItems);

		return order;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Order order = (Order)o;
		return Objects.equals(id, order.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
