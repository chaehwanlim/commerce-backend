package chlim.commercebackend.presentation.restapi;

import chlim.commercebackend.domain.common.Problem;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponse<T> {

	private String message;
	private String errorCode;
	private T data;

	public static <T>CommonResponse success(String message, T data) {
		return CommonResponse.builder()
			.message(message)
			.data(data)
			.build();
	}

	public static CommonResponse fail(Problem e) {
		return CommonResponse.builder()
			.message(e.getMessage())
			.errorCode(e.getErrorCode())
			.data(e.getCause())
			.build();
	}

	public static CommonResponse fail(ConstraintViolationException e) {
		return CommonResponse.builder()
			.message(e.getMessage())
			.errorCode("invalid-request")
			.data(e.getStackTrace())
			.build();
	}

	public static CommonResponse fail(IllegalArgumentException e) {
		return CommonResponse.builder()
			.message(e.getMessage())
			.errorCode("invalid-request")
			.data(e.getStackTrace())
			.build();
	}

	public static CommonResponse fail(Exception e) {
		return CommonResponse.builder()
			.message(e.getMessage() + " " + e.getCause())
			.errorCode("internal-server-error")
			.data(e.getStackTrace())
			.build();
	}
}
