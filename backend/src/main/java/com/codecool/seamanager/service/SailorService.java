package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.sailor.EmailTakenException;
import com.codecool.seamanager.exceptions.sailor.SailorNotFoundException;
import com.codecool.seamanager.model.employee.Rank;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.repository.SailorRepository;
import com.codecool.seamanager.repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SailorService {
	private final SailorRepository sailorRepository;

	@Autowired
	public SailorService(SailorRepository sailorRepository) {
		this.sailorRepository = sailorRepository;
	}

	public List<Sailor> getEmployees() {
		return sailorRepository.findAll();
	}

	public Sailor getEmployeeById(Long employeeId) {
		return sailorRepository.findById(employeeId)
				.orElseThrow(() -> new SailorNotFoundException(
								"Employee with id: " + employeeId + " doesn't exist."
						)
				);
	}

	public Sailor addNewEmployee(Sailor sailor) {
		Optional<Sailor> employeeOptional = sailorRepository.findEmployeeByEmail(sailor.getEmail());
		if (employeeOptional.isPresent()) {
			throw new EmailTakenException(
					"Email " + sailor.getEmail() + " is taken"
			);
		}
		sailorRepository.save(sailor);
		return sailor;
	}

	public void deleteEmployee(Long employeeId) {
		boolean exists = sailorRepository.existsById(employeeId);
		if (!exists) {
			throw new SailorNotFoundException(
					"Employee with id " + employeeId + " does not exist."
			);
		}

		boolean isOnboard = sailorRepository.findById(employeeId).get().getCurrentVoyage() != null;
		if (isOnboard) {
			//TODO - custom exception
			throw new IllegalArgumentException(
					"Cannot delete employee with id " + employeeId + " because is on board vessel"
			);
		}

		sailorRepository.deleteById(employeeId);
	}

	public ResponseEntity<Sailor> updateEmployee(Long employeeId, Sailor sailorDetails) {
		Sailor sailorToUpdate = sailorRepository.findById(employeeId)
				.orElseThrow(() -> new SailorNotFoundException(
								"Employee not exist with id: " + employeeId
						)
				);

		validateEmail(sailorDetails.getEmail(), sailorToUpdate.getEmployeeId());

		sailorToUpdate.setFirstName(sailorDetails.getFirstName());
		sailorToUpdate.setLastName(sailorDetails.getLastName());
		sailorToUpdate.setEmail(sailorDetails.getEmail());
		sailorToUpdate.setBirthDate(sailorDetails.getBirthDate());
		sailorToUpdate.setAddress(sailorDetails.getAddress());
		sailorToUpdate.setContactNo(sailorDetails.getContactNo());
		sailorToUpdate.setRank(sailorDetails.getRank());
		sailorToUpdate.setGender(sailorDetails.getGender());
		sailorToUpdate.setReadinessDate(sailorDetails.getReadinessDate());

		Sailor updatedSailor = sailorRepository.saveAndFlush(sailorToUpdate);
		return ResponseEntity.ok(updatedSailor);
	}

	private void validateEmail(String email, Long employeeId) {
		Optional<Sailor> employeeOptional = sailorRepository.findEmployeeByEmail(email);
		if (employeeOptional.isPresent() &&
				!employeeOptional.get().getEmployeeId().equals(employeeId)) {
			throw new EmailTakenException(
					"Email " + employeeOptional.get().getEmail() + " is taken"
			);
		}
	}

	public ResponseEntity<List<Sailor>> getSuitableRelievers(Rank rank, Vessel vessel){
		return null;
		//TODO - add VoyageRepository first
	}

	public List<Sailor> getEmployeesByRank(Rank rank) {
		return sailorRepository.findByRank(rank);
	}
}
