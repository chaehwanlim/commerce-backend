package chlim.commercebackend.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Problem extends RuntimeException {

	private final ProblemCategory category;
	private final String errorCode;
	private final String message;
	private final Exception cause;
}
