package chlim.commercebackend.presentation.restapi.verification.request;

public record SendPhoneVerificationMessageRequest(
	String phoneNumber
) {
}
