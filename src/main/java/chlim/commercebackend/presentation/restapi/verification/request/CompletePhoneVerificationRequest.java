package chlim.commercebackend.presentation.restapi.verification.request;

public record CompletePhoneVerificationRequest(
	String phoneNumber,
	String code
) {
}
