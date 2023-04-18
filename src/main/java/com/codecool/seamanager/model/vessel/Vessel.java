package com.codecool.seamanager.model.vessel;

import com.codecool.seamanager.model.voyage.Voyage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Vessel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vessel_id")
	private Long vesselId;
	@NotBlank(message = "Vessel name should not be empty.")
	@Column(columnDefinition = "TEXT")
	private String name;
	@NotNull
	@Column(columnDefinition = "TEXT")
	@Enumerated(EnumType.STRING)
	private VesselType type;
	@NotBlank(message = "Vessel flag should not be empty.")
	@Column(name = "flag")
	private String flag;

	@Pattern(
			regexp = "\\d{7}",
			message = "Invalid IMO Number format. Should be 7 digits long."
	)
	@Column(
			columnDefinition = "TEXT",
			name = "IMO_number"
	)
	private String IMONumber;
	@NotNull
	@OneToMany(
			targetEntity = Voyage.class,
			mappedBy = "vessel"
	)
	@JsonIgnore
	private Set<Voyage> voyages;

	public Vessel(String name, VesselType type, String flag, String IMONumber, Set<Voyage> voyages){
		this.name = name;
		this.type = type;
		this.flag= flag;
		this.IMONumber = IMONumber;
		this.voyages = voyages;
	}
}
