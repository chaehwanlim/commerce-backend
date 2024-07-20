package chlim.commercebackend.infrastructure.user;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import chlim.commercebackend.domain.user.domainservice.CreateIdTokenService;
import chlim.commercebackend.domain.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements CreateIdTokenService {

	private static final String SECRET_KEY = "3p892uujmi2849ujr2imjmjdj3928t3iecf";

	@Override
	public String createIdToken(User user) {
		Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
		String id_srt = String.valueOf(user.getId());

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("expiresAt", expiredDate.getTime());

		String jwt = Jwts.builder()
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.setSubject(id_srt).setIssuedAt(new Date()).setExpiration(expiredDate)
			.setClaims(claims)
			.compact();

		return jwt;
	}
}
