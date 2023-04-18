package com.codecool.seamanager.model.voyage;

import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.portinteraction.PortInteraction;
import com.codecool.seamanager.model.vessel.Vessel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Voyage {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "voyage_id")
	private Long voyageId;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "vessel_id", foreignKey = @ForeignKey(name = "vessel_id_foreign"))
	@JoinTable(
			name = "vessel_voyage",
			joinColumns = @JoinColumn(name = "voyage_id"),
			inverseJoinColumns = @JoinColumn(
					referencedColumnName = "vesselId",
					name = "vessel_id"
			)
	)
	private Vessel vessel;
	private String name;
	@NotNull
	@OneToMany(
			mappedBy = "currentVoyage"
	)
	private Set<Sailor> crewList;
	@NotNull
	@ManyToOne
	@JoinTable(
			name = "voyage_departurePI",
			joinColumns = @JoinColumn(name = "voyage_id"),
			inverseJoinColumns = @JoinColumn(
					referencedColumnName = "portInteractionId",
					name = "departurePI_id"
			)
	)
	@JoinColumn(
			name = "departure_id",
			foreignKey = @ForeignKey(name = "departurePI_id_foreign")
	)
	private PortInteraction departure;
	@NotNull
	@ManyToOne
	@JoinTable(
			name = "voyage_arrivalPI",
			joinColumns = @JoinColumn(name = "voyage_id"),
			inverseJoinColumns = @JoinColumn(
					referencedColumnName = "portInteractionId",
					name = "arrivalPI_id"
			)
	)
	@JoinColumn(
			name = "arrival_id",
			foreignKey = @ForeignKey(name = "arrivalPI_id_foreign")
	)
	private PortInteraction arrival;

	public Voyage(Vessel vessel, Set<Sailor> crewList, PortInteraction departure, PortInteraction arrival) {
		this.vessel = vessel;
		this.crewList = crewList;
		this.departure = departure;
		this.arrival = arrival;
	}

	@PrePersist
	public void prePersist() {
		vessel.getVoyages().add(this);
		assignName();
	}

	private void assignName() {
		this.name = vessel.getName().toUpperCase() + "; " + arrival.getPort().getName() + " --> " + departure.getPort().getName();
	}
}
