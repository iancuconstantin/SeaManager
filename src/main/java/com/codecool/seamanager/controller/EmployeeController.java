package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.employee.Employee;
import com.codecool.seamanager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController {

	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping
	public List<Employee> getEmployees() {
		return employeeService.getEmployees();
	}

	@GetMapping(path = "/{employeeId}")
	public Employee getEmployeeById(@PathVariable("employeeId") Long employeeId){
		return employeeService.getEmployeeById(employeeId);
	}

	@PostMapping()
	public void registerNewEmployee(@RequestBody Employee employee) {
		employeeService.addNewEmployee(employee);
	}

	@DeleteMapping(path = "{employeeId}")
	public void deleteEmployee(@PathVariable("employeeId") Long employeeId){
		employeeService.deleteEmployee(employeeId);
	}

	@PutMapping(path = "{employeeId}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employeeDetails){
		return employeeService.updateEmployee(employeeId, employeeDetails);
	}
}
