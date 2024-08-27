package chlim.commercebackend.domain.user.result;

import chlim.commercebackend.domain.user.entity.User;

public record UserResult(
	Long id,
	String email,
	String name,
	String phoneNumber
) {

	public static UserResult from(User user) {
		return new UserResult(
			user.getId(),
			user.getEmail(),
			user.getName(),
			user.getPhoneNumber()
		);
	}
}
