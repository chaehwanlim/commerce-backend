package chlim.commercebackend.domain.order.entity;

import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

import chlim.commercebackend.domain.product.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	private Long quantity;

	@Builder
	public OrderItem(Product product, Order order, Long quantity) {
		this.product = product;
		this.order = order;
		this.quantity = ObjectUtils.defaultIfNull(quantity, 0L);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrderItem orderItem = (OrderItem)o;
		return Objects.equals(id, orderItem.id) && Objects.equals(product, orderItem.product)
			&& Objects.equals(order, orderItem.order);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, product, order);
	}
}
