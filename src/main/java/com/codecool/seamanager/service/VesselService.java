package com.codecool.seamanager.service;

import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.repository.SailorRepository;
import com.codecool.seamanager.repository.VesselRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;

@Service
public class VesselService {
	private final VesselRepository vesselRepository;
	private final SailorRepository sailorRepository;

	@Autowired
	public VesselService(VesselRepository vesselRepository, SailorRepository sailorRepository) {
		this.vesselRepository = vesselRepository;
		this.sailorRepository = sailorRepository;
	}

	public List<Vessel> getAllVessels() {
		return vesselRepository.findAll();
	}

	public Vessel getById(@PathVariable Long id) {
		return vesselRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"Vessel with id: " + id + " not found.")
				);
	}

	public ResponseEntity<Vessel> createVessel(@RequestBody @Valid Vessel vessel) {
		Vessel savedVessel = vesselRepository.save(vessel);
		return ResponseEntity.created(URI.create("/vessel/" + savedVessel.getVesselId())).body(savedVessel);
	}


	public ResponseEntity<Vessel> updateVessel(@PathVariable Long id, @RequestBody @Valid Vessel vessel) {
		Vessel vesselToUpdate = vesselRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"Vessel with id:" + id + "not found.")
				);

		vesselToUpdate.setName(vessel.getName());
		vesselToUpdate.setType(vessel.getType());
		vesselToUpdate.setFlag(vessel.getFlag());
		vesselToUpdate.setIMONumber(vessel.getIMONumber());

		Vessel updatedVessel = vesselRepository.save(vesselToUpdate);
		return ResponseEntity.ok(updatedVessel);
	}

	public void deleteVessel(@PathVariable Long id) {
		boolean exists = vesselRepository.existsById(id);
		if (!exists) {
			throw new IllegalArgumentException("Vessel with id: " + id + " not found.");
		}

		vesselRepository.deleteById(id);
	}
}
