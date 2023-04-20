package com.codecool.seamanager.model.port;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Port {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long portId;
	@NotBlank(message = "Port name should not be blank.")
	private String name;
	@NotNull(message = "Port's status on crew change possibility should not be empty.")
	private boolean allowsCrewChange;

	public Port(String name, boolean allowsCrewChange){
		this.name = name;
		this.allowsCrewChange = allowsCrewChange;
	}

}
