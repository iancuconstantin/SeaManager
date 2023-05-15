package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.employee.Rank;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.service.SailorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/employee")
public class SailorController {

	private final SailorService sailorService;

	@Autowired
	public SailorController(SailorService sailorService) {
		this.sailorService = sailorService;
	}

	@GetMapping
	public List<Sailor> getEmployees() {
		return sailorService.getEmployees();
	}

	@GetMapping(path = "/{employeeId}")
	public Sailor getEmployeeById(@PathVariable("employeeId") Long employeeId){
		return sailorService.getEmployeeById(employeeId);
	}

	@GetMapping(path = "/byRank")
	public List<Sailor> getEmployeesByRank(@RequestParam Rank rank){
		return sailorService.getEmployeesByRank(rank);
	}

	@PostMapping()
	public void registerNewEmployee(@RequestBody Sailor sailor) {
		sailorService.addNewEmployee(sailor);
	}

	@DeleteMapping(path = "{employeeId}")
	public void deleteEmployee(@PathVariable("employeeId") Long employeeId){
		sailorService.deleteEmployee(employeeId);
	}

	@PutMapping(path = "{employeeId}")
	public ResponseEntity<Sailor> updateEmployee(@PathVariable Long employeeId, @RequestBody @Valid Sailor sailorDetails){
		return sailorService.updateEmployee(employeeId, sailorDetails);
	}
}
