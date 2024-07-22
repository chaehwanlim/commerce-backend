package chlim.commercebackend.presentation.restapi.user.request;

public record SignInRequest(
	String email,
	String password
) {
}
