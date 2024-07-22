package chlim.commercebackend.domain.userauthentication.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class UserAuthenticationNotFoundProblem extends Problem {

	public UserAuthenticationNotFoundProblem(String message) {
		super(ProblemCategory.NOT_FOUND, "user/authentication-not-found", message, null);
	}
}
