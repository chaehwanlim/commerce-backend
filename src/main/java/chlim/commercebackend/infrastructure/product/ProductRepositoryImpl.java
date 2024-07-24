package chlim.commercebackend.infrastructure.product;

import static chlim.commercebackend.domain.product.entity.QProduct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import chlim.commercebackend.domain.product.command.FindProductsCommand;
import chlim.commercebackend.domain.product.repository.ProductRepositoryCustom;
import chlim.commercebackend.domain.product.result.ProductResult;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<ProductResult> findAllByCommand(FindProductsCommand command) {
		List<ProductResult> products = jpaQueryFactory
			.select(Projections.constructor(ProductResult.class,
				product.id, product.name, product.description, product.price, product.quantity))
			.from(product)
			.where(
				nameQuery(command.getName())
			)
			.offset(command.getPageable().getOffset())
			.limit(command.getPageable().getPageSize())
			.orderBy(convertToOrderSpecifier(command.getPageable().getSort()).toArray(new OrderSpecifier[0]))
			.fetch();

		Long count = Optional.ofNullable(jpaQueryFactory
			.select(product.count())
			.from(product)
			.where(
				nameQuery(command.getName())
			)
			.fetchOne())
			.orElse(0L);

		return new PageImpl<>(products, command.getPageable(), count);
	}

	private BooleanExpression nameQuery(String name) {
		return name != null ? product.name.containsIgnoreCase(name) : null;
	}

	private List<OrderSpecifier> convertToOrderSpecifier(Sort sort) {
		if (sort == null) {
			return List.of();
		}

		List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

		sort.forEach(sortItem -> {
			switch (sortItem.getProperty()) {
				case "name" -> orderSpecifiers.add(sortItem.isAscending() ? product.name.asc() : product.name.desc());
			}
		});

		return orderSpecifiers;
	}
}
