package chlim.commercebackend.domain.user.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import chlim.commercebackend.domain.userauthentication.entity.UserAuthenticationType;
import chlim.commercebackend.domain.userauthentication.problem.UserAuthenticationNotFoundProblem;
import chlim.commercebackend.domain.verification.entity.VerificationMessage;
import chlim.commercebackend.testfixtures.domain.UserFixture;
import chlim.commercebackend.testfixtures.domain.VerificationMessageFixture;

class UserTest {

	@Nested
	class AddPasswordAuthentication {

		@Test
		@DisplayName("비밀번호 인증을 추가할 수 있다.")
		void success() {
			User user = UserFixture.createUser();

			user.addPasswordAuthentication("encodedPassword");

			assertThat(user.getAuthentications()).hasSize(1);
			assertThat(user.getAuthentications().get(0).getType()).isEqualTo(UserAuthenticationType.PASSWORD);
		}
	}

	@Nested
	class FindAuthenticationByType {

		@Test
		@DisplayName("유저가 가지고 있지 않은 타입의 인증 정보는 가져올 수 없다.")
		void fail() {
			User user = UserFixture.createPasswordUser();

			assertThatThrownBy(() -> user.findAuthenticationByType(UserAuthenticationType.GOOGLE))
				.isInstanceOf(UserAuthenticationNotFoundProblem.class);
		}

		@Test
		@DisplayName("유저가 가지고 있는 타입의 인증 정보를 가져올 수 있다.")
		void success() {
			User user = UserFixture.createPasswordUser();
			user.addPasswordAuthentication("encodedPassword");

			assertThat(user.findAuthenticationByType(UserAuthenticationType.PASSWORD).getType())
				.isEqualTo(UserAuthenticationType.PASSWORD);
		}
	}

	@Nested
	class CompletePhoneVerification {

		@Test
		@DisplayName("휴대폰 인증을 완료하면 유저의 휴대폰 번호가 변경된다.")
		void success() {
			User user = UserFixture.createUser();
			VerificationMessage message = VerificationMessageFixture.forPhoneVerificationWithCode("123456");

			user.completePhoneVerification(message, "123456");

			assertThat(user.getPhoneNumber()).isEqualTo("01012345678");
		}
	}
}