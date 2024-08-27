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

import chlim.commercebackend.domain.user.command.GetUserCommand;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.domain.user.result.UserResult;
import chlim.commercebackend.testfixtures.domain.UserFixture;

@ExtendWith(MockitoExtension.class)
class GetUserTest {

	private static final GetUserCommand command = new GetUserCommand(1L);

	@InjectMocks
	private GetUser getUser;

	@Mock
	private UserRepository userRepository;

	@Test
	@DisplayName("존재하지 않는 유저의 정보는 조회할 수 없다.")
	void failOnUserNotFound() {
		assertThatThrownBy(() -> getUser.execute(command))
			.isInstanceOf(UserNotFoundProblem.class);
	}

	@Test
	@DisplayName("존재하는 유저의 정보를 조회할 수 있다.")
	void success() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(UserFixture.createUser()));

		UserResult userResult = getUser.execute(command);

		assertThat(userResult.email()).isEqualTo(UserFixture.EMAIL);
		assertThat(userResult.name()).isEqualTo(UserFixture.NAME);
	}
}
