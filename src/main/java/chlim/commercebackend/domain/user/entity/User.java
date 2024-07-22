package chlim.commercebackend.domain.user.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import chlim.commercebackend.domain.userauthentication.entity.UserAuthentication;
import chlim.commercebackend.domain.userauthentication.entity.UserAuthenticationType;
import chlim.commercebackend.domain.userauthentication.problem.UserAuthenticationNotFoundProblem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String name;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserAuthentication> authentications = new ArrayList<>();

	@Builder
	public User(Long id, String email, String name) {
		validateEmail(email);
		validateName(name);

		this.id = id;
		this.email = email;
		this.name = name;
	}

	public void addPasswordAuthentication(String encodedPassword) {
		this.authentications.add(UserAuthentication.builder()
			.user(this)
			.type(UserAuthenticationType.PASSWORD)
			.password(encodedPassword)
			.build());
	}

	public UserAuthentication findAuthenticationByType(UserAuthenticationType type) {
		return this.authentications.stream()
			.filter(authentication -> authentication.supports(type))
			.findFirst()
			.orElseThrow(() -> new UserAuthenticationNotFoundProblem(String.format("%s authentication not found", type)));
	}

	public String getEncodedPassword() {
		return this.findAuthenticationByType(UserAuthenticationType.PASSWORD).getPassword();
	}

	private void validateEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("User email must not be blank");
		}

		if (!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
			throw new IllegalArgumentException("User email must be valid");
		}
	}

	private void validateName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("User name must not be blank");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
