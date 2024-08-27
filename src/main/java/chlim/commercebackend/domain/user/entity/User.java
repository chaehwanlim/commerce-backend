package chlim.commercebackend.domain.user.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import chlim.commercebackend.domain.cart.entity.Cart;
import chlim.commercebackend.domain.common.AbstractEntity;
import chlim.commercebackend.domain.product.entity.Product;
import chlim.commercebackend.domain.userauthentication.entity.UserAuthentication;
import chlim.commercebackend.domain.userauthentication.entity.UserAuthenticationType;
import chlim.commercebackend.domain.userauthentication.problem.UserAuthenticationNotFoundProblem;
import chlim.commercebackend.domain.verification.entity.VerificationMessage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String name;

	private String phoneNumber;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserAuthentication> authentications = new ArrayList<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Cart cart;

	@Builder
	public User(Long id, String email, String name) {
		validateEmail(email);
		validateName(name);

		this.id = id;
		this.email = email;
		this.name = name;
		this.cart = new Cart(this);
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
			.orElseThrow(() -> new UserAuthenticationNotFoundProblem(String.format("%s authentication is not found", type)));
	}

	public String getEncodedPassword() {
		return this.findAuthenticationByType(UserAuthenticationType.PASSWORD).getPassword();
	}

	public void addProductInCart(Product product, Long quantity) {
		this.cart.add(product, quantity);
	}

	public void completePhoneVerification(VerificationMessage verificationMessage, String code) {
		verificationMessage.verify(code);

		this.phoneNumber = verificationMessage.getReceiver();
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
