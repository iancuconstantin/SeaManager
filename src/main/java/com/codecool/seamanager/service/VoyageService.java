package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.sailor.SailorExistsException;
import com.codecool.seamanager.exceptions.sailor.SailorNotFoundException;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.voyage.Voyage;
import com.codecool.seamanager.repository.SailorRepository;
import com.codecool.seamanager.repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoyageService {

	private final VoyageRepository voyageRepository;
	private final SailorRepository sailorRepository;

	@Autowired
	public VoyageService(VoyageRepository voyageRepository, SailorRepository sailorRepository) {
		this.voyageRepository = voyageRepository;
		this.sailorRepository = sailorRepository;
	}

	public List<Voyage> getAllVoyages() {
		return voyageRepository.findAll();
	}

	public Voyage getById(Long id) {
		return voyageRepository.findById(id)
				//TODO - custom exception
				.orElseThrow(() -> new IllegalArgumentException("Voyage with id:" + id + " not found."));
	}

	public void deleteVoyage(Long id) {
		boolean exists = voyageRepository.existsById(id);

		if (!exists) {
			//TODO - custom exception
			throw new IllegalArgumentException("Voyage with id:" + id + " not found.");
		}

		voyageRepository.deleteById(id);
	}

	public ResponseEntity<Voyage> addNewCrewMember(Long voyageId, Sailor sailor) {
		Voyage voyageToUpdate = getById(voyageId);
		Optional<Sailor> sailorToAdd = sailorRepository.findEmployeeByEmail(sailor.getEmail());

		if (sailorToAdd.isEmpty()) {
			throw new SailorNotFoundException(
					"Sailor with e-mail: " + sailor.getEmail() + " not found."
			);
		}

		if (isNotPartOfCrewList(voyageToUpdate, sailorToAdd)){
			addNewCrewMember(voyageToUpdate, sailorToAdd.get());
			sailorToAdd.get().setCurrentVoyage(voyageToUpdate);
		} else{
			throw new SailorExistsException(
					"Sailor with id: " + sailorToAdd.get().getEmployeeId() + " already part of vessel's " + voyageToUpdate.getVessel().getName() + " crew list."
			);
		}

		Voyage updatedVoyage = voyageRepository.save(voyageToUpdate);
		return ResponseEntity.ok(updatedVoyage);
	}

	private void addNewCrewMember(Voyage voyageToUpdate, Sailor sailorToAdd) {
		voyageToUpdate.getCrewList().add(sailorToAdd);
	}

	private boolean isNotPartOfCrewList(Voyage voyageToUpdate, Optional<Sailor> sailorToAdd) {
		return voyageToUpdate.getCrewList().stream()
				.noneMatch(s -> s.getEmployeeId().equals(sailorToAdd.get().getEmployeeId()));
	}
}
