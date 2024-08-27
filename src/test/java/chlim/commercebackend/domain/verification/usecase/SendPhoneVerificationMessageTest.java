package chlim.commercebackend.domain.verification.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import chlim.commercebackend.domain.message.domainservice.SendMessageService;
import chlim.commercebackend.domain.verification.command.SendPhoneVerificationMessageCommand;
import chlim.commercebackend.domain.verification.problem.VerificationAttemptExceededLimitProblem;
import chlim.commercebackend.domain.verification.repository.VerificationMessageRepository;
import chlim.commercebackend.testfixtures.domain.VerificationMessageFixture;

@ExtendWith(MockitoExtension.class)
class SendPhoneVerificationMessageTest {

	private static final String PHONE_NUMBER = "01012345678";
	private static final SendPhoneVerificationMessageCommand command = new SendPhoneVerificationMessageCommand(PHONE_NUMBER);

	@InjectMocks
	private SendPhoneVerificationMessage sendPhoneVerificationMessage;

	@Mock
	private VerificationMessageRepository verificationMessageRepository;

	@Mock
	private SendMessageService sendMessageService;

	@Test
	@DisplayName("오늘 전송된 인증 메시지가 5회 이상이면 휴대폰 인증 메시지를 전송할 수 없다.")
	void failOnVerificationAttemptExceededLimitProblem() {
		when(verificationMessageRepository.findVerificationMessagesSentTodayTo("01012345678"))
			.thenReturn(IntStream.range(0, 5)
				.mapToObj(i -> VerificationMessageFixture.forPhoneVerification())
				.toList());

		assertThatThrownBy(() -> sendPhoneVerificationMessage.execute(command))
			.isInstanceOf(VerificationAttemptExceededLimitProblem.class);
	}

	@Test
	@DisplayName("오늘 전송된 인증 메시지가 5회 미만이면 휴대폰 인증 메시지를 보낸다.")
	void success() {
		when(verificationMessageRepository.findVerificationMessagesSentTodayTo(PHONE_NUMBER))
			.thenReturn(IntStream.range(0, 3)
				.mapToObj(i -> VerificationMessageFixture.forPhoneVerification())
				.toList());

		sendPhoneVerificationMessage.execute(command);

		verify(verificationMessageRepository).save(any());
		verify(sendMessageService).sendMessage(any(), any());
	}
}