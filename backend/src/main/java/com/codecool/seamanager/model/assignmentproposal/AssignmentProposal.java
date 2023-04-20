//package com.codecool.seamanager.model.assignmentproposal;
//
//import com.codecool.seamanager.model.employee.Sailor;
//import com.codecool.seamanager.model.user.User;
//import com.codecool.seamanager.model.vessel.Vessel;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//
//import static jakarta.persistence.GenerationType.IDENTITY;
//
//@Table
//@Entity
//public class AssignmentProposal {
//	@Id
//	@GeneratedValue(strategy = IDENTITY)
//	private Long proposalId;
//	@NotNull(message = "Status cannot be empty.")
//	@Enumerated(EnumType.STRING)
//	private AssignmentProposalStatus status;
//	@ManyToOne
//	@JoinColumn(name = "created_by_user_id")
//	private User createdBy;
//	@ManyToOne
//	@JoinColumn(name = "decision_by_user_id")
//	private User decisionBy;
//
//
//	private Sailor toBeRelieved;
//	private Vessel assignedVessel;
//}
