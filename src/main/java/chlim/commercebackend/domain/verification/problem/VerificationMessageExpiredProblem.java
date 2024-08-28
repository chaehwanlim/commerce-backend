package chlim.commercebackend.domain.verification.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class VerificationMessageExpiredProblem extends Problem {

	public VerificationMessageExpiredProblem() {
		super(ProblemCategory.UNPROCESSABLE, "verification/message-expired", "Verification message has expired", null);
	}
}
