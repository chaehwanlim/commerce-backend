package chlim.commercebackend.presentation.restapi.verification;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chlim.commercebackend.domain.verification.command.CompletePhoneVerificationCommand;
import chlim.commercebackend.domain.verification.command.SendPhoneVerificationMessageCommand;
import chlim.commercebackend.domain.verification.result.SendPhoneVerificationMessageResult;
import chlim.commercebackend.domain.verification.usecase.CompletePhoneVerification;
import chlim.commercebackend.domain.verification.usecase.SendPhoneVerificationMessage;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import chlim.commercebackend.presentation.restapi.verification.request.CompletePhoneVerificationRequest;
import chlim.commercebackend.presentation.restapi.verification.request.SendPhoneVerificationMessageRequest;
import chlim.commercebackend.presentation.restapi.verification.response.SendPhoneVerificationMessageResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/verification")
@RequiredArgsConstructor
public class VerificationController {

	private final SendPhoneVerificationMessage sendPhoneVerificationMessage;
	private final CompletePhoneVerification completePhoneVerification;

	@PostMapping("/phone/send")
	public CommonResponse sendPhoneVerificationMessage(@RequestBody SendPhoneVerificationMessageRequest request) {
		SendPhoneVerificationMessageCommand command = new SendPhoneVerificationMessageCommand(request.phoneNumber());

		SendPhoneVerificationMessageResult result = sendPhoneVerificationMessage.execute(command);

		SendPhoneVerificationMessageResponse response = new SendPhoneVerificationMessageResponse(result.expiresAt());

		return CommonResponse.success("Send phone verification message success", response);
	}

	@PostMapping("/phone/complete")
	public CommonResponse completePhoneVerification(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody CompletePhoneVerificationRequest request
	) {
		CompletePhoneVerificationCommand command = CompletePhoneVerificationCommand.builder()
			.userId(Long.parseLong(userDetails.getUsername()))
			.phoneNumber(request.phoneNumber())
			.code(request.code())
			.build();

		completePhoneVerification.execute(command);

		return CommonResponse.success("Complete phone verification success", null);
	}
}
