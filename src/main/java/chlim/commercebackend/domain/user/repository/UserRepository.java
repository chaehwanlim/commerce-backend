package chlim.commercebackend.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chlim.commercebackend.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
