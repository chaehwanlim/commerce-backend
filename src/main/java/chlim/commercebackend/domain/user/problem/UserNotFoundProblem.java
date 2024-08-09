package chlim.commercebackend.domain.user.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class UserNotFoundProblem extends Problem {

	public UserNotFoundProblem(String message) {
		super(ProblemCategory.NOT_FOUND, "user/not-found", message, null);
	}

	public static UserNotFoundProblem withId(Long id) {
		return new UserNotFoundProblem("User is not found with id: " + id);
	}

	public static UserNotFoundProblem withEmail(String email) {
		return new UserNotFoundProblem("User is not found with email: " + email);
	}
}
