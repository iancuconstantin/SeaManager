package com.codecool.seamanager.model.portinteraction;

import com.codecool.seamanager.model.port.Port;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "portInteraction")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PortInteraction {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long portInteractionId;
	@NotNull
	@Enumerated(EnumType.STRING)
	private PortInteractionType portInteractionType;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "port_id")
	private Port port;
	@NotNull(message = "Port Interaction's date cannot be empty.")
	@Column(columnDefinition = "DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(
			shape = STRING,
			pattern = "yyyy-MM-dd"
	)
	private LocalDate date;
	private String details;

	public PortInteraction(PortInteractionType type, Port port, LocalDate date) {
		this.portInteractionType = type;
		this.port = port;
		this.date = date;
	}
}
