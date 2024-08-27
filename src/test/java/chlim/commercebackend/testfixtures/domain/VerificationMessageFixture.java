package chlim.commercebackend.testfixtures.domain;

import org.springframework.test.util.ReflectionTestUtils;

import chlim.commercebackend.domain.verification.entity.VerificationMessage;

public class VerificationMessageFixture {

	public static final String PHONE_NUMBER = "01012345678";

	public static VerificationMessage forPhoneVerification() {
		return VerificationMessage.forPhoneVerification(PHONE_NUMBER);
	}

	public static VerificationMessage forPhoneVerificationWithCode(String code) {
		VerificationMessage verificationMessage = VerificationMessage.forPhoneVerification(PHONE_NUMBER);
		ReflectionTestUtils.setField(verificationMessage, "code", code);

		return verificationMessage;
	}
}
