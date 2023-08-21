package com.codecool.seamanager.model.contract;

import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.voyage.Voyage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long contractId;

	@ManyToOne
	@JoinColumn(
			name = "employee_id",
			referencedColumnName = "employee_id",
			columnDefinition = "BIGINT",
			foreignKey = @ForeignKey(name = "owner_id_foreign")
	)
	@JsonIgnore
	private Sailor owner;
//	@PastOrPresent(message = "Start date should be in the past or today.")
	private LocalDate startDate;
//	@Future(message = "End date should be in the future.")
	private LocalDate finishDate;
//	@ManyToOne //TODO - ask?
//	private Voyage vesselVoyageAtStart;
	private Long voyageId;
	private boolean isActive = true;

	public Contract(Sailor owner, LocalDate startDate, LocalDate finishDate
			, Long voyageId
	) {
		this.owner = owner;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.voyageId = voyageId;
//		this.vesselVoyageAtStart = vesselVoyageAtStart;
	}
}
