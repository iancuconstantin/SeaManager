package com.codecool.seamanager.model.assignmentproposal;

import com.codecool.seamanager.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table
@Entity
public class AssignmentProposal {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@NotNull(message = "Status cannot be empty.")
	@Enumerated(EnumType.STRING)
	private AssignmentProposalStatus status;
	@ManyToOne //TODO
	private User createdBy;
	@ManyToOne //TODO
	private User decisionBy;
}
