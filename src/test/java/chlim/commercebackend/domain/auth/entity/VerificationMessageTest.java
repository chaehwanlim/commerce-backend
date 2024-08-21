package chlim.commercebackend.domain.auth.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import chlim.commercebackend.domain.auth.problem.VerificationCodeWrongProblem;
import chlim.commercebackend.domain.auth.problem.VerificationMessageExpiredProblem;
import chlim.commercebackend.testfixtures.domain.VerificationMessageFixture;

class VerificationMessageTest {

	@Nested
	class HasExpired {

		@Test
		@DisplayName("이미 사용된 메시지는 만료된 메시지이다.")
		void expiredWhenUsed() {
			VerificationMessage message = VerificationMessageFixture.forPhoneVerificationWithCode("123456");
			message.verify("123456");

			assertThat(message.hasExpired()).isTrue();
		}

		@Test
		@DisplayName("사용되지 않았지만 생성된 지 30분이 지난 메시지는 만료된 메시지이다.")
		void expiredAfter30Minutes() {
			VerificationMessage message = VerificationMessageFixture.forPhoneVerification();
			ReflectionTestUtils.setField(message, "expiresAt", ZonedDateTime.now().minusMinutes(31));

			assertThat(message.hasExpired()).isTrue();
		}

		@Test
		@DisplayName("사용되지 않았고 생성된 지 30분이 지나지 않은 메시지는 만료되지 않은 메시지이다.")
		void notExpired() {
			VerificationMessage message = VerificationMessageFixture.forPhoneVerification();

			assertThat(message.hasExpired()).isFalse();
		}
	}

	@Nested
	class Verify {

		@Test
		@DisplayName("코드가 일치하지 않으면 인증에 실패한다.")
		void failOnCodeMismatch() {
			VerificationMessage message = VerificationMessageFixture.forPhoneVerificationWithCode("654321");

			assertThatThrownBy(() -> message.verify("123456"))
				.isInstanceOf(VerificationCodeWrongProblem.class);
		}

		@Test
		@DisplayName("코드가 일치하지만 만료되었으면 인증에 실패한다.")
		void failOnExpired() {
			VerificationMessage message = VerificationMessageFixture.forPhoneVerificationWithCode("123456");
			ReflectionTestUtils.setField(message, "status", VerificationMessageStatus.USED);

			assertThatThrownBy(() -> message.verify("123456"))
				.isInstanceOf(VerificationMessageExpiredProblem.class);
		}

		@Test
		@DisplayName("코드가 일치하고 만료되지 않았으면 인증에 성공한다.")
		void success() {
			VerificationMessage message = VerificationMessageFixture.forPhoneVerificationWithCode("123456");

			message.verify("123456");

			assertThat(message.getStatus()).isEqualTo(VerificationMessageStatus.USED);
		}
	}
}