package chlim.commercebackend.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chlim.commercebackend.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
