package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.sailor.SailorExistsException;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.voyage.Voyage;
import com.codecool.seamanager.repository.SailorRepository;
import com.codecool.seamanager.repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageService {

	private final VoyageRepository voyageRepository;
	private final SailorRepository sailorRepository;
	private final SailorService sailorService;

	@Autowired
	public VoyageService(VoyageRepository voyageRepository, SailorRepository sailorRepository, SailorService sailorService) {
		this.voyageRepository = voyageRepository;
		this.sailorRepository = sailorRepository;
		this.sailorService = sailorService;
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

	public ResponseEntity<Voyage> addNewCrewMember(Long voyageId, Long employeeId) {
		Voyage voyageToUpdate = getById(voyageId);
		Sailor sailorToAdd = sailorService.getEmployeeById(employeeId);


		if (isNotPartOfCrewList(voyageToUpdate, sailorToAdd) && sailorToAdd.getCurrentVoyage() != null ){
			addNewCrewMember(voyageToUpdate, sailorToAdd);
			sailorToAdd.setCurrentVoyage(voyageToUpdate);
			sailorToAdd.setReadinessDate(null);
		} else{
			throw new SailorExistsException(
					"Sailor with id: " + sailorToAdd.getEmployeeId() + " already part of a crew list."
			);
		}

		Voyage updatedVoyage = voyageRepository.save(voyageToUpdate);
		return ResponseEntity.ok(updatedVoyage);
	}

	private void addNewCrewMember(Voyage voyageToUpdate, Sailor sailorToAdd) {
		voyageToUpdate.getCrewList().add(sailorToAdd);
	}

	private boolean isNotPartOfCrewList(Voyage voyageToUpdate, Sailor sailorToAdd) {
		return voyageToUpdate.getCrewList().stream()
				.noneMatch(s -> s.getEmployeeId().equals(sailorToAdd.getEmployeeId()));
	}
}
