package chlim.commercebackend.presentation.restapi.auth.request;

public record CompletePhoneVerificationRequest(
	String phoneNumber,
	String code
) {
}
