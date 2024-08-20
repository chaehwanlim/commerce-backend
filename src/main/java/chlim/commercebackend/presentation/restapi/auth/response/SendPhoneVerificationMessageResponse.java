package chlim.commercebackend.presentation.restapi.auth.response;

import java.time.ZonedDateTime;

public record SendPhoneVerificationMessageResponse(
	ZonedDateTime expiresAt
) {
}
