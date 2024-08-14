package chlim.commercebackend.domain.user.domainservice;

public interface VerifyPasswordService {

	boolean verifyPassword(String rawPassword, String encodedPassword);
}
