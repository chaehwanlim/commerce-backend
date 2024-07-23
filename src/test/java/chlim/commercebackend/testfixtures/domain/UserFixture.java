package chlim.commercebackend.testfixtures.domain;

import chlim.commercebackend.domain.user.entity.User;

public class UserFixture {

	public static final String EMAIL = "test@test.com";
	public static final String NAME = "chlim";

	public static User createUser() {
		return User.builder()
			.email(EMAIL)
			.name(NAME)
			.build();
	}

	public static User createPasswordUser() {
		User user = User.builder()
			.email(EMAIL)
			.name(NAME)
			.build();

		user.addPasswordAuthentication("encodedPassword");

		return user;
	}
}
