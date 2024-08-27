package chlim.commercebackend.presentation.restapi.verification.response;

import java.time.ZonedDateTime;

public record SendPhoneVerificationMessageResponse(
	ZonedDateTime expiresAt
) {
}
