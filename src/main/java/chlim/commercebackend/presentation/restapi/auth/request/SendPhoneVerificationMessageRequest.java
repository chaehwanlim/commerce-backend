package chlim.commercebackend.presentation.restapi.auth.request;

public record SendPhoneVerificationMessageRequest(
	String phoneNumber
) {
}
