package chlim.commercebackend.infrastructure.auth;

import static chlim.commercebackend.domain.auth.entity.QVerificationMessage.*;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import chlim.commercebackend.domain.auth.entity.VerificationMessage;
import chlim.commercebackend.domain.auth.repository.VerificationMessageRepositoryCustom;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VerificationMessageRepositoryImpl implements VerificationMessageRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<VerificationMessage> findVerificationMessagesSentTodayTo(String receiver) {
		ZonedDateTime startOfToday = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

		return jpaQueryFactory.selectFrom(verificationMessage)
			.where(verificationMessage.createdAt.after(startOfToday))
			.where(verificationMessage.receiver.eq(receiver))
			.fetch();
	}
}
