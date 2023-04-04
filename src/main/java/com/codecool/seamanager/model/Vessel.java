package com.codecool.seamanager.model;

import java.util.List;
import java.util.UUID;

public class Vessel {
	private final String id;
	private final String name;
	private final VesselType type;
	private final String flag;
	private final long IMONumber;
	private final List<Employee> crewList;
	private String nextPortOfCall;

	public Vessel(String name, VesselType type, String flag, long imoNumber, List<Employee> crewList, String nextPortOfCall) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.type = type;
		this.flag = flag;
		this.IMONumber = imoNumber;
		this.crewList = crewList;
		this.nextPortOfCall = nextPortOfCall;
	}

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

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public VesselType getType() {
		return type;
	}

	public String getFlag() {
		return flag;
	}

	public long getIMONumber() {
		return IMONumber;
	}

	public List<Employee> getCrewList() {
		return crewList;
	}

	public String getNextPortOfCall() {
		return nextPortOfCall;
	}

	public void setNextPortOfCall(String nextPortOfCall) {
		this.nextPortOfCall = nextPortOfCall;
	}
}
