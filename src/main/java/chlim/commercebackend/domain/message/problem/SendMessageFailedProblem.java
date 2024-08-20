package chlim.commercebackend.domain.message.problem;

import chlim.commercebackend.domain.common.Problem;
import chlim.commercebackend.domain.common.ProblemCategory;

public class SendMessageFailedProblem extends Problem {

	public SendMessageFailedProblem(Exception cause) {
		super(ProblemCategory.SERVICE_UNAVAILABLE, "message/send-message-failed", "Send message failed", cause);
	}
}
