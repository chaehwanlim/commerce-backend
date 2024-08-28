package chlim.commercebackend.domain.verification.domainservice;

import chlim.commercebackend.domain.verification.entity.VerificationMessage;

public interface SendPhoneVerificationMessageService {

	void sendPhoneVerificationMessage(VerificationMessage verificationMessage);
}
