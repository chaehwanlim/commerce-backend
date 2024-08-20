package chlim.commercebackend.testfixtures.domain;

import chlim.commercebackend.domain.auth.entity.VerificationMessage;

public class VerificationMessageFixture {

	public static final String PHONE_NUMBER = "01012345678";

	public static VerificationMessage createVerificationMessage() {
		return VerificationMessage.forPhoneVerification(PHONE_NUMBER);
	}
}
