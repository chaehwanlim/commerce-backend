package chlim.commercebackend.domain.user.domainservice;

public interface VerifyPasswordService {

	boolean verify(String rawPassword, String encodedPassword);
}
