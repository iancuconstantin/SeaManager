package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.vessel.IMONumberExistsException;
import com.codecool.seamanager.exceptions.vessel.VesselNotFoundException;
import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.repository.SailorRepository;
import com.codecool.seamanager.repository.VesselRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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
				.orElseThrow(() -> new VesselNotFoundException(
						"Vessel with id: " + id + " not found.")
				);
	}

	public void createVessel(@RequestBody @Valid Vessel vessel) {
		Optional<Vessel> vesselOptional = vesselRepository.findVesselByIMONumber(vessel.getIMONumber());

		if (vesselOptional.isPresent()) {
			throw new IMONumberExistsException(
					"Vessel with IMO Number: " + vessel.getIMONumber() + " already exists."
			);
		}

		vesselRepository.save(vessel);
	}


	public ResponseEntity<Vessel> updateVessel(@PathVariable Long id, @RequestBody @Valid Vessel vessel) {
		Vessel vesselToUpdate = vesselRepository.findById(id)
				.orElseThrow(() -> new VesselNotFoundException(
						"Vessel with id:" + id + "not found.")
				);

		Optional<Vessel> vesselOptional = vesselRepository.findVesselByIMONumber(vessel.getIMONumber());
		if(vesselOptional.isPresent()){
			throw new IMONumberExistsException(
					"IMO number " + vessel.getIMONumber() + " exists in database."
			);
		}
		vesselToUpdate.setIMONumber(vessel.getIMONumber());

		vesselToUpdate.setName(vessel.getName());
		vesselToUpdate.setType(vessel.getType());
		vesselToUpdate.setFlag(vessel.getFlag());
		vesselToUpdate.setEmail(vessel.getEmail());
		vesselToUpdate.setDWT(vessel.getDWT());
		vesselToUpdate.setGRT(vessel.getGRT());
		vesselToUpdate.setCallSign(vessel.getCallSign());
		vesselToUpdate.setYearBuilt(vessel.getYearBuilt());
		vesselToUpdate.setMMSI(vessel.getMMSI());

		Vessel updatedVessel = vesselRepository.save(vesselToUpdate);
		return ResponseEntity.ok(updatedVessel);
	}

	public void deleteVessel(@PathVariable Long id) {
		boolean exists = vesselRepository.existsById(id);
		if (!exists) {
			throw new VesselNotFoundException("Vessel with id: " + id + " not found.");
		}

		vesselRepository.deleteById(id);
	}
}
