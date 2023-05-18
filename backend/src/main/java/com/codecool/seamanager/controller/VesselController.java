package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.service.VesselService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/vessel")
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public class VesselController {

	private final VesselService vesselService;

	@Autowired
	public VesselController(VesselService vesselService) {
		this.vesselService = vesselService;
	}

	@GetMapping
	public List<Vessel> getAllVessels() {
		return vesselService.getAllVessels();
	}

	@GetMapping("/{vesselId}")
	public Vessel getById(@PathVariable Long vesselId) {
		return vesselService.getById(vesselId);
	}

	@PostMapping()
	public void createVessel(@RequestBody @Valid Vessel vessel) {
		vesselService.createVessel(vessel);
	}

	@PutMapping("/{vesselId}")
	public ResponseEntity<Vessel> updateVessel(@PathVariable("vesselId") Long vesselId, @RequestBody @Valid Vessel vessel) {
		return vesselService.updateVessel(vesselId, vessel);
	}

	@DeleteMapping("/{vesselId}")
	public void deleteVessel(@PathVariable("vesselId") Long vesselId) {
		vesselService.deleteVessel(vesselId);
	}
}
