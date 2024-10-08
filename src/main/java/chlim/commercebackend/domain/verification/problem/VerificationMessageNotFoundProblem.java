package chlim.commercebackend.domain.verification.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class VerificationMessageNotFoundProblem extends Problem {

	public VerificationMessageNotFoundProblem(String message) {
		super(ProblemCategory.NOT_FOUND, "verification/message-not-found", message, null);
	}

	public static VerificationMessageNotFoundProblem forReceiver(String receiver) {
		return new VerificationMessageNotFoundProblem("Verification message is not found for receiver: " + receiver);
	}
}
