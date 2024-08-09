package chlim.commercebackend.presentation.restapi.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import chlim.commercebackend.domain.user.entity.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final Long userId;

	public static CustomUserDetails of(User user) {
		return new CustomUserDetails(user.getId());
	}

	@Override
	public String getUsername() {
		return this.userId.toString();
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
}
