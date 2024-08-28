package chlim.commercebackend.domain.verification.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class VerificationCodeWrongProblem extends Problem {

	public VerificationCodeWrongProblem() {
		super(ProblemCategory.UNPROCESSABLE, "verification/code-wrong", "Verification code is wrong", null);
	}
}
