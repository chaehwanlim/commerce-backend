package chlim.commercebackend.domain.auth.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class VerificationMessageExpiredProblem extends Problem {

	public VerificationMessageExpiredProblem() {
		super(ProblemCategory.UNPROCESSABLE, "auth/verification-message-expired", "Verification message has expired", null);
	}
}
