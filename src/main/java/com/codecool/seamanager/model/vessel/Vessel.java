package com.codecool.seamanager.model.vessel;

import com.codecool.seamanager.model.employee.Sailor;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vessels")
public class Vessel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(name = "name")
	private String name;
	@NotNull
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private VesselType type;
	@NotNull
	@Column(name = "flag")
	private String flag;
	@NotNull
	@Column(name = "imonumber")
	private long IMONumber;
	@NotNull
	@Column(name = "crewList")
	@OneToMany(
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "vessel"
	)
//	@JoinColumn(name = "vessel_id")
	private List<Sailor> crewList;

	@NotNull
	@Column(name = "nextPortOfCall")
	private String nextPortOfCall;

	public Vessel() {}

	public Vessel(@NotNull String name, @NotNull VesselType type, @NotNull String flag, @NotNull long IMONumber, @NotNull String nextPortOfCall) {
		this.name = name;
		this.type = type;
		this.flag = flag;
		this.IMONumber = IMONumber;
		this.crewList = new ArrayList<>();
		this.nextPortOfCall = nextPortOfCall;
	}




	public void changeCrew(Sailor offSigner, Sailor onSigner) {
		if (!offSigner.getRank().equals(onSigner.getRank())) {
			throw new IllegalArgumentException(
					"Off-signer: " + offSigner.getLastName() + ", rank: " + offSigner.getRank()
							+ "doesn't match rank with on-signer: " + onSigner.getLastName() + ", rank: " + onSigner.getRank()
			);
		} else if (!isPartOfCrewList(offSigner)) {
			throw new IllegalArgumentException(
					"Off-signer " + offSigner.getLastName() + " is not part of of vessel's " + name + " crew list."
			);
		}

		addCrew(onSigner);
		removeCrew(offSigner);
	}

	public void removeCrew(Sailor sailor) {
		if (isPartOfCrewList(sailor)) {
			crewList.remove(sailor);
		} else {
			throw new IllegalArgumentException(
					"Employee " + sailor.getLastName() + " is not part of of vessel's " + name + " crew list."
			);
		}
	}


	public void addCrew(Sailor sailor) {
		crewList.add(sailor);
	}

	public Sailor getCrewMemberById(String employeeID){
		for (Sailor sailor : crewList){
			if(sailor.getEmployeeId().equals(employeeID)){
				return sailor;
			}
		}
		throw new IllegalArgumentException(
				"Employee id: " + employeeID + " is not part of of vessel's " + name + " crew list."
		);
	}

	private boolean isPartOfCrewList(Sailor sailor) {
		for (Sailor e : crewList) {
			if (e.getEmployeeId().equals(sailor.getEmployeeId())) {
				return true;
			}
		}
		return false;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public VesselType getType() {
		return type;
	}


	public void setType(VesselType type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public long getIMONumber() {
		return IMONumber;
	}


	public void setIMONumber(long IMONumber) {
		this.IMONumber = IMONumber;
	}


	public List<Sailor> getCrewList() {
		return crewList;
	}


	public void setCrewList(List<Sailor> crewList) {
		this.crewList = crewList;
	}


	public String getNextPortOfCall() {
		return nextPortOfCall;
	}

	public void setNextPortOfCall(String nextPortOfCall) {
		this.nextPortOfCall = nextPortOfCall;
	}
}
