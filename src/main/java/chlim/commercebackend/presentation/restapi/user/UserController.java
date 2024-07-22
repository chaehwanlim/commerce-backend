package chlim.commercebackend.presentation.restapi.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chlim.commercebackend.domain.user.command.PasswordSignInCommand;
import chlim.commercebackend.domain.user.command.PasswordSignUpCommand;
import chlim.commercebackend.domain.user.result.SignInResult;
import chlim.commercebackend.domain.user.result.SignUpResult;
import chlim.commercebackend.domain.user.usecase.PasswordSignIn;
import chlim.commercebackend.domain.user.usecase.PasswordSignUp;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import chlim.commercebackend.presentation.restapi.user.request.SignInRequest;
import chlim.commercebackend.presentation.restapi.user.request.SignUpRequest;
import chlim.commercebackend.presentation.restapi.user.response.SignInResponse;
import chlim.commercebackend.presentation.restapi.user.response.SignUpResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final PasswordSignUp passwordSignUp;
	private final PasswordSignIn passwordSignIn;

	@PostMapping("/sign-up/password")
	public CommonResponse<SignUpResponse> passwordSignUp(@RequestBody SignUpRequest request) {
		PasswordSignUpCommand command = PasswordSignUpCommand.builder()
			.email(request.email())
			.name(request.name())
			.password(request.password())
			.build();

		SignUpResult result = passwordSignUp.execute(command);

		SignUpResponse response = new SignUpResponse(result.accessToken());

		return CommonResponse.success("Sign up success", response);
	}

	@PostMapping("/sign-in/password")
	public CommonResponse<SignInResponse> signIn(@RequestBody SignInRequest request) {
		PasswordSignInCommand command = PasswordSignInCommand.builder()
			.email(request.email())
			.password(request.password())
			.build();

		SignInResult result = passwordSignIn.execute(command);

		SignInResponse response = new SignInResponse(result.accessToken());

		return CommonResponse.success("Sign in success", response);
	}
}
