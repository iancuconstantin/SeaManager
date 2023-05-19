package com.codecool.seamanager.model.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(name = "username_uk", columnNames = {"username"})
		},
		name = "users"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@Column(unique = true)
	@NotBlank(message = "Username is required.")
	@Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "Username must be alphanumeric and have 4 to 20 characters")
	private String username;
	@NotBlank(message = "Password is required.")
	@Pattern(
			regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@#$%\\-^&+-=]{8,}$",
			message = "Password must have at least 8 characters, one uppercase letter, one lowercase letter, and one digit"
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	@NotBlank(message = "First name is required")
	@Size(
			min = 2,
			max = 25,
			message = "First name should be between 2 and 25 characters long"
	)
	private String firstName;
	@Size(
			min = 2,
			max = 15,
			message = "Last name should be between 2 and 15 characters long"
	)
	private String lastName;
	@Column(unique = true)
	@Email(message = "Invalid email.")
	private String email;
	@Column(
			columnDefinition = "DATE"
	)
	@JsonFormat(
			shape = STRING,
			pattern = "yyyy-MM-dd"
	)
	private LocalDate createdAt;
	private String authorities;

	public User(String username, String password, String firstName, String lastName, String email, String authorities) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.authorities = authorities;
	}

	@PrePersist
	public void prePersist(){
		setCreationDate();
	}

	private void setCreationDate(){
		if (this.createdAt == null) this.createdAt = LocalDate.now();
	}

}
