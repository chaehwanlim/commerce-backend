package chlim.commercebackend.domain.product.command;

import org.springframework.data.domain.Pageable;

import chlim.commercebackend.domain.common.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindProductsCommand extends SelfValidating<FindProductsCommand> {

	private static final Integer DEFAULT_PAGE = 0;
	private static final Integer DEFAULT_SIZE = 20;

	private final Pageable pageable;

	private final String name;

	@Builder
	public FindProductsCommand(String name, Integer page, Integer size) {
		this.pageable = Pageable
			.ofSize(size == null || size < 1 ? DEFAULT_SIZE : size)
			.withPage(page == null || page < 0 ? DEFAULT_PAGE : page);
		this.name = name;
	}
}
