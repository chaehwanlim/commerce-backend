package chlim.commercebackend.presentation.restapi.user.response;

import chlim.commercebackend.domain.user.result.UserResult;
import lombok.Builder;

@Builder
public record UserResponse(
	Long id,
	String email,
	String name,
	String phoneNumber
) {

	public static UserResponse from(UserResult userResult) {
		return UserResponse.builder()
			.id(userResult.id())
			.email(userResult.email())
			.name(userResult.name())
			.phoneNumber(userResult.phoneNumber())
			.build();
	}
}
