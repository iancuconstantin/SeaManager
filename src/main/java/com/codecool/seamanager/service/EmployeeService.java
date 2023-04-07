package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.email.EmailTakenException;
import com.codecool.seamanager.exceptions.email.EmployeeNotFoundException;
import com.codecool.seamanager.model.employee.Employee;
import com.codecool.seamanager.model.employee.Gender;
import com.codecool.seamanager.model.employee.Rank;
import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
public class EmployeeService {
	private final EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(Long employeeId) {
		return employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(
								"Employee not exist with id: " + employeeId
						)
				);
	}

	public void addNewEmployee(Employee employee) {
		Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employee.getEmail());
		if (employeeOptional.isPresent()) {
			throw new EmailTakenException(
					"Email " + employee.getEmail() + "is taken"
			);
		}
		employeeRepository.saveAndFlush(employee);
	}

	public void deleteEmployee(Long employeeId) {
		boolean exists = employeeRepository.existsById(employeeId);

		Vessel isOnJob = employeeRepository.getById(employeeId).getI_vessel();

		if (!exists && isOnJob != null) {
			throw new EmployeeNotFoundException(
					"Employee with id " + employeeId + " does not exist."
			);
		}

		employeeRepository.deleteById(employeeId);
	}

	@Transactional
	public void updateEmployee(Long employeeId, Employee employeeDetails) {
		Employee employeeToUpdate = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(
								"Employee not exist with id: " + employeeId
						)
				);

		String updatedFirstName = employeeDetails.getFirstName();
		if (updatedFirstName != null &&
				updatedFirstName.length() > 0 &&
				updatedFirstName.equals(employeeDetails.getFirstName())) {
			employeeToUpdate.setA_firstName(updatedFirstName);
		}

		String updatedLastName = employeeDetails.getLastName();
		if (updatedLastName != null &&
				updatedLastName.length() > 0 &&
				updatedLastName.equals(employeeDetails.getLastName())) {
			employeeToUpdate.setB_lastName(updatedLastName);
		}

		String updatedEmail = employeeDetails.getEmail();
		Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employeeDetails.getEmail());
		if (updatedEmail != null &&
				updatedEmail.length() > 0 &&
				updatedEmail.equals(employeeDetails.getEmail()) &&
				employeeOptional.isEmpty()) {
			employeeToUpdate.setC_email(updatedEmail);
		}

		String updatedDob = employeeDetails.getBirthDate();
		if (updatedDob != null &&
				updatedDob.length() > 0 &&
				updatedDob.equals(employeeDetails.getBirthDate())) {
			employeeToUpdate.setD_birthDate(updatedDob);
		}

		String updatedAddress = employeeDetails.getAddress();
		if (updatedAddress != null &&
				updatedAddress.length() > 0 &&
				updatedAddress.equals(employeeDetails.getAddress())) {
			employeeToUpdate.setE_address(updatedAddress);
		}

		String updatedContactNo = employeeDetails.getContactNo();
		if (updatedContactNo != null &&
				updatedContactNo.length() > 0 &&
				updatedContactNo.equals(employeeDetails.getContactNo())) {
			employeeToUpdate.setF_contactNo(updatedContactNo);
		}

		Rank updatedRank = employeeDetails.getRank();
		if (updatedRank != null &&
				Arrays.asList(Rank.values()).contains(updatedRank) &&
				!updatedRank.equals(employeeToUpdate.getRank())) {
			employeeToUpdate.setG_rank(updatedRank);
		}

		Gender updatedGender = employeeDetails.getGender();
		if (updatedGender != null &&
				Arrays.asList(Gender.values()).contains(updatedGender) &&
				!updatedGender.equals(employeeToUpdate.getGender())) {
			employeeToUpdate.setH_gender(updatedGender);
		}
	}
}
