package chlim.commercebackend.domain.user.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chlim.commercebackend.domain.user.command.GetUserCommand;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.problem.UserNotFoundProblem;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.domain.user.result.UserResult;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUser {

	private final UserRepository userRepository;

	public UserResult execute(GetUserCommand command) {
		User user =  userRepository.findById(command.getId())
			.orElseThrow(() -> UserNotFoundProblem.withId(command.getId()));

		return UserResult.from(user);
	}
}
