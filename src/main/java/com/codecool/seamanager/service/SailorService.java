package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.email.EmailTakenException;
import com.codecool.seamanager.exceptions.email.SailorNotFoundException;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.employee.Gender;
import com.codecool.seamanager.repository.SailorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

	public void addNewEmployee(Sailor sailor) {
		Optional<Sailor> employeeOptional = sailorRepository.findEmployeeByEmail(sailor.getEmail());
		if (employeeOptional.isPresent()) {
			throw new EmailTakenException(
					"Email " + sailor.getEmail() + " is taken"
			);
		}
		sailorRepository.save(sailor);
	}

	public void deleteEmployee(Long employeeId) {
		boolean exists = sailorRepository.existsById(employeeId);
		if (!exists) {
			throw new SailorNotFoundException(
					"Employee with id " + employeeId + " does not exist."
			);
		}
		boolean isOnboard = sailorRepository.findById(employeeId).get().getVessel() != null;
		if(isOnboard){
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

		if (sailorDetails.getFirstName() != null) {
			sailorToUpdate.setFirstName(sailorDetails.getFirstName());
		}

		if(sailorDetails.getLastName()!=null){
			sailorToUpdate.setLastName(sailorDetails.getLastName());
		}

		if(sailorDetails.getEmail()!=null){
			sailorToUpdate.setEmail(sailorDetails.getEmail());
		}

		if(sailorDetails.getBirthDate()!= null){
			sailorToUpdate.setBirthDate(sailorDetails.getBirthDate());
		}

		if(sailorDetails.getAddress()!= null){
			sailorToUpdate.setAddress(sailorDetails.getAddress());
		}

		if(sailorDetails.getContactNo() != null){
			sailorToUpdate.setContactNo(sailorDetails.getContactNo());
		}

		if(sailorDetails.getRank() != null){
			sailorToUpdate.setRank(sailorDetails.getRank());
		}

		Gender updatedGender = sailorDetails.getGender();
		if (updatedGender != null &&
				Arrays.asList(Gender.values()).contains(updatedGender) &&
				!updatedGender.equals(sailorToUpdate.getGender())) {
			sailorToUpdate.setGender(updatedGender);
		}
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
}
