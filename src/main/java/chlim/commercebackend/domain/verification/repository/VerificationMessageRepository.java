package chlim.commercebackend.domain.verification.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import chlim.commercebackend.domain.verification.entity.VerificationMessage;

public interface VerificationMessageRepository extends JpaRepository<VerificationMessage, Long>, VerificationMessageRepositoryCustom {

	Optional<VerificationMessage> findByReceiverOrderByCreatedAtDesc(String receiver);
}
