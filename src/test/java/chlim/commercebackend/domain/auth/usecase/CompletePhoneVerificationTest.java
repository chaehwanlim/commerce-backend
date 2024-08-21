package chlim.commercebackend.domain.auth.usecase;

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

import chlim.commercebackend.domain.auth.command.CompletePhoneVerificationCommand;
import chlim.commercebackend.domain.auth.problem.VerificationMessageNotFoundProblem;
import chlim.commercebackend.domain.auth.repository.VerificationMessageRepository;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.testfixtures.domain.UserFixture;
import chlim.commercebackend.testfixtures.domain.VerificationMessageFixture;

@ExtendWith(MockitoExtension.class)
class CompletePhoneVerificationTest {

	private static final CompletePhoneVerificationCommand command = CompletePhoneVerificationCommand.builder()
		.userId(1L)
		.phoneNumber("01012345678")
		.code("123456")
		.build();

	@InjectMocks
	private CompletePhoneVerification completePhoneVerification;

	@Mock
	private UserRepository userRepository;

	@Mock
	private VerificationMessageRepository verificationMessageRepository;

	@Test
	@DisplayName("유저가 존재하지 않으면 휴대폰 인증을 완료할 수 없다.")
	void failOnUserNotFound() {
		assertThatThrownBy(() -> completePhoneVerification.execute(command))
			.isInstanceOf(UserNotFoundProblem.class);
	}

	@Nested
	@DisplayName("유저가 존재하고")
	class UserExists {

		@BeforeEach
		void setUp() {
			when(userRepository.findById(command.getUserId()))
				.thenReturn(Optional.of(UserFixture.createUser()));
		}

		@Test
		@DisplayName("휴대폰 번호로 발송된 인증 메시지가 존재하지 않으면 휴대폰 인증을 완료할 수 없다.")
		void failOnVerificationMessageNotFound() {
			assertThatThrownBy(() -> completePhoneVerification.execute(command))
				.isInstanceOf(VerificationMessageNotFoundProblem.class);
		}

		@Test
		@DisplayName("휴대폰 번호로 발송된 인증 메시지가 존재하면 휴대폰 인증을 완료할 수 있다.")
		void success() {
			when(verificationMessageRepository.findByReceiverOrderByCreatedAtDesc(command.getPhoneNumber()))
				.thenReturn(Optional.of(VerificationMessageFixture.forPhoneVerificationWithCode("123456")));

			completePhoneVerification.execute(command);

			verify(userRepository).findById(command.getUserId());
			verify(verificationMessageRepository).findByReceiverOrderByCreatedAtDesc(command.getPhoneNumber());
		}
	}
}