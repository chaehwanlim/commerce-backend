package chlim.commercebackend.domain.verification.repository;

import java.util.List;

import chlim.commercebackend.domain.verification.entity.VerificationMessage;

public interface VerificationMessageRepositoryCustom {

	List<VerificationMessage> findVerificationMessagesSentTodayTo(String receiver);
}
