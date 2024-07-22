package chlim.commercebackend.domain.userauthentication.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class UserAuthenticationIncorrectProblem extends Problem {

	public UserAuthenticationIncorrectProblem(String message) {
		super(ProblemCategory.UNAUTHORIZED, "user/authentication-incorrect", message, null);
	}
}
