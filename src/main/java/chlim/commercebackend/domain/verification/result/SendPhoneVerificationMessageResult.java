package chlim.commercebackend.domain.verification.result;

import java.time.ZonedDateTime;

public record SendPhoneVerificationMessageResult(
	ZonedDateTime expiresAt
) {
}
