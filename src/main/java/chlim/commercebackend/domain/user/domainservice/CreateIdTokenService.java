package chlim.commercebackend.domain.user.domainservice;

import chlim.commercebackend.domain.user.entity.User;

public interface CreateIdTokenService {

	String createIdToken(User user);
}
