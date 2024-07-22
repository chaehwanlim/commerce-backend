package chlim.commercebackend.domain.userauthentication.entity;

import java.util.Objects;

import chlim.commercebackend.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserAuthentication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Enumerated(EnumType.STRING)
	private UserAuthenticationType type;

	private String password;

	@Builder
	public UserAuthentication(Long id, User user, UserAuthenticationType type, String password) {
		this.id = id;
		this.user = user;
		this.type = type;
		this.password = password;
	}

	public boolean supports(UserAuthenticationType type) {
		return this.type.equals(type);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserAuthentication that = (UserAuthentication)o;
		return Objects.equals(user, that.user) && type == that.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, type);
	}
}
