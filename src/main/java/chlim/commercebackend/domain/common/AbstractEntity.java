package chlim.commercebackend.domain.common;

import java.time.ZonedDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

	private ZonedDateTime createdAt;

	private ZonedDateTime updatedAt;

	@PrePersist
	public void prePersist() {
		this.createdAt = ZonedDateTime.now();
		this.updatedAt = ZonedDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = ZonedDateTime.now();
	}
}
