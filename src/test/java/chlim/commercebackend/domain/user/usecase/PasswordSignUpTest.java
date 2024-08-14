package chlim.commercebackend.domain.user.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import chlim.commercebackend.domain.user.command.PasswordSignUpCommand;
import chlim.commercebackend.domain.user.domainservice.CreateIdTokenService;
import chlim.commercebackend.domain.user.domainservice.EncodePasswordService;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.problem.UserAlreadyExistsProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.domain.user.result.SignUpResult;
import chlim.commercebackend.testfixtures.domain.UserFixture;

@ExtendWith(MockitoExtension.class)
class PasswordSignUpTest {

	@InjectMocks
	private PasswordSignUp passwordSignUp;

	@Mock
	private UserRepository userRepository;

	@Mock
	private EncodePasswordService encodePasswordService;

	@Mock
	private CreateIdTokenService createIdTokenService;

	@Test
	@DisplayName("이메일이 중복되면 회원가입 할 수 없다.")
	void fail() {
		when(userRepository.findByEmail(UserFixture.EMAIL))
			.thenReturn(Optional.of(UserFixture.createPasswordUser()));

		PasswordSignUpCommand command = PasswordSignUpCommand.builder()
			.name("name")
			.email(UserFixture.EMAIL)
			.password("password")
			.build();

		assertThatThrownBy(() -> passwordSignUp.execute(command))
			.isInstanceOf(UserAlreadyExistsProblem.class);
	}

	@Test
	@DisplayName("이메일이 중복되지 않으면 회원가입 할 수 있다.")
	void success() {
		User user = UserFixture.createPasswordUser();
		when(userRepository.findByEmail(UserFixture.EMAIL))
			.thenReturn(Optional.empty());
		when(encodePasswordService.encodePassword(any()))
			.thenReturn("encodedPassword");
		when(userRepository.save(any()))
			.thenReturn(user);
		when(createIdTokenService.createIdToken(any()))
			.thenReturn("accessToken");

		PasswordSignUpCommand command = PasswordSignUpCommand.builder()
			.name("name")
			.email(UserFixture.EMAIL)
			.password("password")
			.build();

		SignUpResult result = passwordSignUp.execute(command);

		assertThat(result.accessToken()).isEqualTo("accessToken");
		assertThat(user.getEncodedPassword()).isNotNull();
		assertThat(user.getEncodedPassword()).isEqualTo("encodedPassword");
	}
}