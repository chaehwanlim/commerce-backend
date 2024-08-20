package chlim.commercebackend.domain.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problem extends RuntimeException {

	private final ProblemCategory category;
	private final String errorCode;
	private final String message;
	private final Exception cause;

	public Problem(ProblemCategory category, String errorCode, String message, Exception cause) {
		super(message, cause);

		this.category = category;
		this.errorCode = errorCode;
		if (cause != null && cause.getMessage() != null) {
			this.message = String.format("%s (cause: %s)", message, cause.getMessage());
		} else {
			this.message = message;
		}

		this.cause = cause;
	}
}
