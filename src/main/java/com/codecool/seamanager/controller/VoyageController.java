package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.voyage.Voyage;
import com.codecool.seamanager.service.VoyageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/voyage")
public class VoyageController {

	private final VoyageService voyageService ;

	@Autowired
	public VoyageController(VoyageService voyageService) {
		this.voyageService = voyageService;
	}

	@GetMapping
	public List<Voyage> getAllVoyages() {
		return voyageService.getAllVoyages();
	}

	@GetMapping("/{id}")
	public Voyage getById(@PathVariable Long id) {
		return voyageService.getById(id);
	}

	@PutMapping(path = "{voyageId}/add")
	public ResponseEntity<Voyage> addNewCrewMember(@PathVariable("voyageId") Long voyageId, @Valid @RequestBody Sailor sailor){
		return voyageService.addNewCrewMember(voyageId, sailor);
	}

	@DeleteMapping("/{id}")
	public void deleteVoyage(@PathVariable Long id) {
		voyageService.deleteVoyage(id);
	}
}
