package com.codecool.seamanager.model.user;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(name = "username")
	private String username;
	@NotNull
	@Column(name = "password")
	private String password;
	@NotNull
	@Column(name = "accessLevel")
	@Min(value = 1, message = "Access level must be at least 1")
	@Max(value = 3, message = "Access level cannot be greater than 3")
	private Integer accessLevel;


	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}
}
