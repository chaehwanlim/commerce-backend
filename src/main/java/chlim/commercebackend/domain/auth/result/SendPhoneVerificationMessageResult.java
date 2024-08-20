package chlim.commercebackend.domain.auth.result;

import java.time.ZonedDateTime;

public record SendPhoneVerificationMessageResult(
	ZonedDateTime expiresAt
) {
}
