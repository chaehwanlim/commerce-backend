package chlim.commercebackend.domain.user.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class UserAlreadyExistsProblem extends Problem {

	public UserAlreadyExistsProblem(String message) {
		super(ProblemCategory.UNPROCESSABLE, "user/already-exists", message, null);
	}
}
