package chlim.commercebackend.presentation.restapi.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chlim.commercebackend.domain.auth.command.CompletePhoneVerificationCommand;
import chlim.commercebackend.domain.auth.command.SendPhoneVerificationMessageCommand;
import chlim.commercebackend.domain.auth.result.SendPhoneVerificationMessageResult;
import chlim.commercebackend.domain.auth.usecase.CompletePhoneVerification;
import chlim.commercebackend.domain.auth.usecase.SendPhoneVerificationMessage;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import chlim.commercebackend.presentation.restapi.auth.request.CompletePhoneVerificationRequest;
import chlim.commercebackend.presentation.restapi.auth.request.SendPhoneVerificationMessageRequest;
import chlim.commercebackend.presentation.restapi.auth.response.SendPhoneVerificationMessageResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final SendPhoneVerificationMessage sendPhoneVerificationMessage;
	private final CompletePhoneVerification completePhoneVerification;

	@PostMapping("/phone-verification/send")
	public CommonResponse sendPhoneVerificationMessage(@RequestBody SendPhoneVerificationMessageRequest request) {
		SendPhoneVerificationMessageCommand command = new SendPhoneVerificationMessageCommand(request.phoneNumber());

		SendPhoneVerificationMessageResult result = sendPhoneVerificationMessage.execute(command);

		SendPhoneVerificationMessageResponse response = new SendPhoneVerificationMessageResponse(result.expiresAt());

		return CommonResponse.success("Send phone verification message success", response);
	}

	@PostMapping("/phone-verification/complete")
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
