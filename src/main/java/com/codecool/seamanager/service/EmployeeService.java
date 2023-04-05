package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.EmailTakenException;
import com.codecool.seamanager.model.employee.Employee;
import com.codecool.seamanager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

	public void addNewEmployee(Employee employee) {
		Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employee.getEmail());
		if (employeeOptional.isPresent()) {
			throw new EmailTakenException(
					"Email " + employee.getEmail() + "is taken"
			);
		}
		employeeRepository.saveAndFlush(employee);
	}
}
