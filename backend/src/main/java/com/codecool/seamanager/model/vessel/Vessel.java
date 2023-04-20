package com.codecool.seamanager.model.vessel;

import com.codecool.seamanager.model.voyage.Voyage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(name = "imo_number_uk", columnNames = {"imo_number"})
		}
)
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
	@Pattern(
			regexp = "[a-zA-Z]*",
			message = "Vessel's flag should have only letters."
	)
	private String flag;
	@NotBlank(message = "MMSI number should not be empty.")
	@Pattern(
			regexp = "\\d{9}",
			message = "MMSI number should be 9 digits long."
	)
	private String MMSI;
	@NotBlank(message = "Call Sign should not be empty.")
	@Pattern(
			regexp = "[a-zA-Z\\d]{5}",
			message = "Call Sign should be 5 characters long and contain only digits and letters."
	)
	private String callSign;
	@NotBlank(message = "GRT should not be empty.")
	@Pattern(
			regexp = "[\\d]{5,6}",
			message = "Incorrect GRT format. Accepting only digits (min 5, max 6)."
	)
	private String GRT;
	@NotBlank(message = "DWT should not be empty.")
	@Pattern(
			regexp = "[\\d]{5,6}",
			message = "Incorrect DWT format. Accepting only digits (min 5, max 6)."
	)
	private String DWT;
	@NotBlank(message = "Year built should not be empty.")
	@Pattern(regexp = "\\d{4}", message = "Year built must be a 4-digit number")
	private String yearBuilt;
	@Email(message = "Not a valid email.")
	private String email;
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
	private Set<Voyage> voyages = new HashSet<>();

	public Vessel(String name, VesselType type, String flag, String MMSI, String email, String callSign, String IMONumber, String yearBuilt, String DWT, String GRT) {
		this.name = name;
		this.type = type;
		this.flag = flag;
		this.IMONumber = IMONumber;
		this.callSign = callSign;
		this.yearBuilt = yearBuilt;
		this.email = email;
		this.MMSI = MMSI;
		this.DWT = DWT;
		this.GRT = GRT;
	}
}
