package chlim.commercebackend.domain.auth.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class VerificationAttemptExceededLimitProblem extends Problem {

	public VerificationAttemptExceededLimitProblem() {
		super(ProblemCategory.UNPROCESSABLE, "auth/verification-attempt-exceeded-limit", "Verification attempt exceeded limit", null);
	}
}
