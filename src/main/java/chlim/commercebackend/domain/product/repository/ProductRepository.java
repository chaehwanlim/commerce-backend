package chlim.commercebackend.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chlim.commercebackend.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
