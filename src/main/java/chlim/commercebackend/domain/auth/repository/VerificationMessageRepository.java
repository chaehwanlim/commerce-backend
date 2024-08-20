package chlim.commercebackend.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chlim.commercebackend.domain.auth.entity.VerificationMessage;

public interface VerificationMessageRepository extends JpaRepository<VerificationMessage, Long>, VerificationMessageRepositoryCustom{
}
