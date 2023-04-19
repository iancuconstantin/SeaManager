package com.codecool.seamanager.model.contract;

import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.voyage.Voyage;
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
	private Long id;
	@ManyToOne
	@JoinColumn(
			name = "employee_id",
			referencedColumnName = "employee_id",
			columnDefinition = "BIGINT",
			foreignKey = @ForeignKey(name = "owner_id_foreign")
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Sailor owner;
	@PastOrPresent(message = "Start date cannot be in the future.")
	private LocalDate startDate;
	@Future(message = "End date cannot be today or in the past.")
	private LocalDate finishDate;
	@OneToOne //TODO - ask?
	private Voyage vesselVoyageAtStart;
}
