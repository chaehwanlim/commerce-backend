package chlim.commercebackend.domain.verification.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class SendVerificationMessageFailedProblem extends Problem {

	public SendVerificationMessageFailedProblem(Exception cause) {
		super(ProblemCategory.SERVICE_UNAVAILABLE, "verification/send-message-failed", "Send message failed", cause);
	}
}
