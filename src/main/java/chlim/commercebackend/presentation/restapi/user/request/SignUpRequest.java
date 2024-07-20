package chlim.commercebackend.presentation.restapi.user.request;

public record SignUpRequest(
	String email,
	String name,
	String password
) {
}
