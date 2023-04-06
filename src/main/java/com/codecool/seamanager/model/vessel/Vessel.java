package com.codecool.seamanager.model.vessel;

import com.codecool.seamanager.model.employee.Employee;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

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
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "vessel_id")
	private List<Employee> crewList;

	@NotNull
	@Column(name = "nextPortOfCall")
	private String nextPortOfCall;

	public void changeCrew(Employee offSigner, Employee onSigner) {
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

	public void removeCrew(Employee employee) {
		if (isPartOfCrewList(employee)) {
			crewList.remove(employee);
		} else {
			throw new IllegalArgumentException(
					"Employee " + employee.getLastName() + " is not part of of vessel's " + name + " crew list."
			);
		}
	}

	public void addCrew(Employee employee) {
		crewList.add(employee);
	}

	public Employee getCrewMemberById(String employeeID){
		for (Employee employee : crewList){
			if(employee.getId().equals(employeeID)){
				return employee;
			}
		}
		throw new IllegalArgumentException(
				"Employee id: " + employeeID + " is not part of of vessel's " + name + " crew list."
		);
	}

	private boolean isPartOfCrewList(Employee employee) {
		for (Employee e : crewList) {
			if (e.getId().equals(employee.getId())) {
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

	public List<Employee> getCrewList() {
		return crewList;
	}

	public void setCrewList(List<Employee> crewList) {
		this.crewList = crewList;
	}

	public String getNextPortOfCall() {
		return nextPortOfCall;
	}

	public void setNextPortOfCall(String nextPortOfCall) {
		this.nextPortOfCall = nextPortOfCall;
	}
}
