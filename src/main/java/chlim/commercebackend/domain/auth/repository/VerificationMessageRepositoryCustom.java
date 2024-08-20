package chlim.commercebackend.domain.auth.repository;

import java.util.List;

import chlim.commercebackend.domain.auth.entity.VerificationMessage;

public interface VerificationMessageRepositoryCustom {

	List<VerificationMessage> findVerificationMessagesSentTodayTo(String receiver);
}
