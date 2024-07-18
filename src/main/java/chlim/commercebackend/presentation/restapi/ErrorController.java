package chlim.commercebackend.presentation.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import chlim.commercebackend.domain.common.Problem;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {ConstraintViolationException.class, IllegalArgumentException.class})
	public CommonResponse onConstraintViolationException(ConstraintViolationException e) {
		return CommonResponse.fail(e);
	}

	@ResponseBody
	@ExceptionHandler(value = Problem.class)
	public ResponseEntity onProblem(Problem e) {
		HttpStatus httpStatus = switch (e.getCategory()) {
			case INVALID_REQUEST -> HttpStatus.BAD_REQUEST;
			case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
			case FORBIDDEN -> HttpStatus.FORBIDDEN;
			case NOT_FOUND -> HttpStatus.NOT_FOUND;
			case UNPROCESSABLE -> HttpStatus.UNPROCESSABLE_ENTITY;
			default -> HttpStatus.SERVICE_UNAVAILABLE;
		};

		return new ResponseEntity<>(
			CommonResponse.fail(e),
			httpStatus
		);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public CommonResponse onException(Exception e) {
		return CommonResponse.fail(e);
	}
}

