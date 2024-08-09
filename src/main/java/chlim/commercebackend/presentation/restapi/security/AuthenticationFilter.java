package chlim.commercebackend.presentation.restapi.security;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import chlim.commercebackend.domain.user.domainservice.IdentifyByIdTokenService;
import chlim.commercebackend.domain.user.entity.User;
import chlim.commercebackend.domain.user.repository.UserRepository;
import chlim.commercebackend.presentation.restapi.CommonResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private final UserRepository userRepository;
	private final IdentifyByIdTokenService identifyByIdTokenService;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		User user;
		try {
			String accessToken = request.getHeader("Authorization");
			if (accessToken == null || !accessToken.startsWith("Bearer ")) {
				setUnauthorizedResponse(response, "Access token is invalid.");
				return;
			}

			Long userId = identifyByIdTokenService.identifyByIdToken(accessToken.split("Bearer ")[1]);
			if (userId == null) {
				setUnauthorizedResponse(response, "Access token is invalid.");
				return;
			}

			user = userRepository.findById(userId).orElse(null);
		} catch (Exception e) {
			setUnauthorizedResponse(response, e.getMessage());
			return;
		}

		if (user == null) {
			setUnauthorizedResponse(response, "User not found.");
			return;
		}

		CustomUserDetails userDetails = CustomUserDetails.of(user);
		UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

	@Bean
	public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistrationBean() {
		FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(this);
		registrationBean.addUrlPatterns(List.of(
			"/api/v1/carts/*",
			"/api/v1/products/*"
		).toArray(new String[0]));
		registrationBean.setName("AuthenticationFilter");
		registrationBean.setOrder(1);
		return registrationBean;
	}

	private void setUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
		CommonResponse commonResponse = CommonResponse.builder()
			.message(message)
			.errorCode("unauthorized")
			.build();

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
	}
}
