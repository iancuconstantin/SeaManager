package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.request.ContractRequest;
import com.codecool.seamanager.model.voyage.Voyage;
import com.codecool.seamanager.service.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api/voyage")
public class VoyageController {

	private final VoyageService voyageService;

	@Autowired
	public VoyageController(VoyageService voyageService) {
		this.voyageService = voyageService;
	}

	@GetMapping
	public List<Voyage> getAllVoyages() {
		return voyageService.getAllVoyages();
	}

	@GetMapping("/{voyageId}")
	public Voyage getById(@PathVariable("voyageId") Long voyageId) {
		return voyageService.getById(voyageId);
	}

	@PutMapping(path = "/{voyageId}/add/{employeeId}")
	public void addNewCrewMember(@PathVariable("voyageId") Long voyageId, @PathVariable("employeeId") Long employeeId, @RequestBody ContractRequest contractRequest) {
		voyageService.addNewCrewMember(voyageId, employeeId, contractRequest.startDate(), contractRequest.endDate());
	}

	@PutMapping(path = "/{voyageId}/remove/{employeeId}")
	public ResponseEntity<Voyage> removeCrewMember(@PathVariable("voyageId") Long voyageId, @PathVariable("employeeId") Long employeeId) {
		return voyageService.removeCrewMember(voyageId, employeeId);
	}

	@DeleteMapping("/{voyageId}")
	public void deleteVoyage(@PathVariable("voyageId") Long voyageId) {
		voyageService.deleteVoyage(voyageId);
	}
}
