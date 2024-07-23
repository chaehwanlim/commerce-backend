package chlim.commercebackend.domain.user.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import chlim.commercebackend.domain.user.command.PasswordSignInCommand;
import chlim.commercebackend.domain.user.domainservice.CreateIdTokenService;
import chlim.commercebackend.domain.user.domainservice.VerifyPasswordService;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.domain.userauthentication.problem.UserAuthenticationIncorrectProblem;
import chlim.commercebackend.testfixtures.domain.UserFixture;

@ExtendWith(MockitoExtension.class)
class PasswordSignInTest {

	@InjectMocks
	private PasswordSignIn passwordSignIn;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CreateIdTokenService createIdTokenService;

	@Mock
	private VerifyPasswordService verifyPasswordService;

	@Test
	@DisplayName("해당 이메일을 가진 유저가 없으면 로그인 할 수 없다.")
	void fail() {
		PasswordSignInCommand command = PasswordSignInCommand.builder()
			.email("not-exists@test.com")
			.password("password")
			.build();

		assertThatThrownBy(() -> passwordSignIn.execute(command))
			.isInstanceOf(UserNotFoundProblem.class);
	}

	@Nested
	@DisplayName("해당 이메일을 가진 유저가 존재하고")
	class UserExists {

		private final User user = UserFixture.createPasswordUser();

		@BeforeEach
		void setUp() {
			when(userRepository.findByEmail(UserFixture.EMAIL))
				.thenReturn(Optional.of(user));
		}

		@Test
		@DisplayName("비밀번호가 일치하지 않으면 로그인 할 수 없다.")
		void fail() {
			when(verifyPasswordService.verify("wrongPassword", user.getEncodedPassword()))
				.thenReturn(false);

			PasswordSignInCommand command = PasswordSignInCommand.builder()
				.email(UserFixture.EMAIL)
				.password("wrongPassword")
				.build();

			assertThatThrownBy(() -> passwordSignIn.execute(command))
				.isInstanceOf(UserAuthenticationIncorrectProblem.class);
		}

		@Test
		@DisplayName("비밀번호가 일치하면 로그인 할 수 있다.")
		void success() {
			when(verifyPasswordService.verify("rightPassword", user.getEncodedPassword()))
				.thenReturn(true);
			when(createIdTokenService.createIdToken(user))
				.thenReturn("accessToken");

			PasswordSignInCommand command = PasswordSignInCommand.builder()
				.email(UserFixture.EMAIL)
				.password("rightPassword")
				.build();

			assertThat(passwordSignIn.execute(command).accessToken()).isEqualTo("accessToken");
		}
	}
}