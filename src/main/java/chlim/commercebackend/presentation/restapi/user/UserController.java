package chlim.commercebackend.presentation.restapi.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chlim.commercebackend.domain.user.command.GetUserCommand;
import chlim.commercebackend.domain.user.command.PasswordSignInCommand;
import chlim.commercebackend.domain.user.command.PasswordSignUpCommand;
import chlim.commercebackend.domain.user.result.SignInResult;
import chlim.commercebackend.domain.user.result.SignUpResult;
import chlim.commercebackend.domain.user.result.UserResult;
import chlim.commercebackend.domain.user.usecase.GetUser;
import chlim.commercebackend.domain.user.usecase.PasswordSignIn;
import chlim.commercebackend.domain.user.usecase.PasswordSignUp;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import chlim.commercebackend.presentation.restapi.user.request.SignInRequest;
import chlim.commercebackend.presentation.restapi.user.request.SignUpRequest;
import chlim.commercebackend.presentation.restapi.user.response.SignInResponse;
import chlim.commercebackend.presentation.restapi.user.response.SignUpResponse;
import chlim.commercebackend.presentation.restapi.user.response.UserResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final PasswordSignUp passwordSignUp;
	private final PasswordSignIn passwordSignIn;
	private final GetUser getUser;

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

	@GetMapping("/me")
	public CommonResponse<UserResponse> getMe(
		@AuthenticationPrincipal UserDetails userDetails
	) {
		GetUserCommand command = new GetUserCommand(Long.parseLong(userDetails.getUsername()));

		UserResult response = getUser.execute(command);

		return CommonResponse.success("Get me success", UserResponse.from(response));
	}
}
