package chlim.commercebackend.domain.auth.entity;

import java.time.ZonedDateTime;
import java.util.Objects;

import chlim.commercebackend.domain.auth.problem.VerificationCodeWrongProblem;
import chlim.commercebackend.domain.auth.problem.VerificationMessageExpiredProblem;
import chlim.commercebackend.domain.common.AbstractEntity;
import chlim.commercebackend.util.RandomGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class VerificationMessage extends AbstractEntity {

	private static final int EXPIRATION_IN_MINUTES = 30;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String receiver;

	private String content;

	private String code;

	@Enumerated(value = EnumType.STRING)
	private VerificationMessageType type;

	@Enumerated(value = EnumType.STRING)
	private VerificationMessageStatus status;

	@Enumerated(value = EnumType.STRING)
	private VerificationMessagePurpose purpose;

	private ZonedDateTime expiresAt;

	@Builder
	private VerificationMessage(String receiver, String content, String code,
		VerificationMessageType type, VerificationMessageStatus status, VerificationMessagePurpose purpose) {

		this.receiver = receiver;
		this.content = content;
		this.code = code;
		this.type = type;
		this.status = status;
		this.purpose = purpose;
		this.expiresAt = ZonedDateTime.now().plusMinutes(EXPIRATION_IN_MINUTES);
	}

	public static VerificationMessage forPhoneVerification(String receiver) {
		String code = RandomGenerator.generateNumber(6);

		return VerificationMessage.builder()
			.receiver(receiver)
			.content(String.format("[Commerce] Your verification code is %s", code))
			.code(code)
			.type(VerificationMessageType.SMS)
			.status(VerificationMessageStatus.SENT)
			.purpose(VerificationMessagePurpose.PHONE_VERIFICATION)
			.build();
	}

	public boolean hasExpired() {
		return VerificationMessageStatus.USED.equals(this.status)
			|| this.expiresAt.isBefore(ZonedDateTime.now());
	}

	public void verify(String code) {
		if (!this.code.equals(code)) {
			throw new VerificationCodeWrongProblem();
		}

		if (hasExpired()) {
			throw new VerificationMessageExpiredProblem();
		}

		this.status = VerificationMessageStatus.USED;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VerificationMessage that = (VerificationMessage)o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
