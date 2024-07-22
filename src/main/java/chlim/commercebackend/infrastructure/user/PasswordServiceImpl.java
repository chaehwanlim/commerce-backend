package chlim.commercebackend.infrastructure.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import chlim.commercebackend.domain.user.domainservice.EncodePasswordService;
import chlim.commercebackend.domain.user.domainservice.VerifyPasswordService;

@Service
public class PasswordServiceImpl implements EncodePasswordService, VerifyPasswordService {

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean verify(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
