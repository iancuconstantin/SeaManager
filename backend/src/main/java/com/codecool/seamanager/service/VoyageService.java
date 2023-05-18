package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.sailor.SailorExistsException;
import com.codecool.seamanager.model.contract.Contract;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.voyage.Voyage;
import com.codecool.seamanager.repository.ContractRepository;
import com.codecool.seamanager.repository.SailorRepository;
import com.codecool.seamanager.repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class VoyageService {

	private final VoyageRepository voyageRepository;
	private final SailorRepository sailorRepository;
	private final SailorService sailorService;
	private final ContractRepository contractRepository;

	@Autowired
	public VoyageService(
			VoyageRepository voyageRepository,
			SailorRepository sailorRepository,
			SailorService sailorService,
			ContractRepository contractRepository) {
		this.voyageRepository = voyageRepository;
		this.sailorRepository = sailorRepository;
		this.sailorService = sailorService;
		this.contractRepository = contractRepository;
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

	public ResponseEntity<Voyage> addNewCrewMember(Long voyageId, Long employeeId, LocalDate contractStartDate, LocalDate contractEndDate) {
		//TODO -recheck logic
		Voyage voyageToUpdate = getById(voyageId);
		Sailor sailorToAdd = sailorService.getEmployeeById(employeeId);


		if (isNotPartOfCrewList(voyageToUpdate, sailorToAdd) && sailorToAdd.getCurrentVoyage() == null) {
			saveNewContractForSailor(sailorToAdd, voyageToUpdate, contractStartDate, contractEndDate);
			addCrewMemberToCrewList(voyageToUpdate, sailorToAdd);
			sailorToAdd.setCurrentVoyage(voyageToUpdate);
			sailorToAdd.setReadinessDate(null);
		} else {
			throw new SailorExistsException(
					"Sailor with id: " + sailorToAdd.getEmployeeId() + " already part of a crew list."
			);
		}

		Voyage updatedVoyage = voyageRepository.save(voyageToUpdate);
		return ResponseEntity.ok(updatedVoyage);
	}

	public ResponseEntity<Voyage> removeCrewMember(Long voyageId, Long employeeId) {
		Voyage voyageToUpdate = getById(voyageId);
		Sailor sailorToRemove = sailorService.getEmployeeById(employeeId);

		if (!isNotPartOfCrewList(voyageToUpdate, sailorToRemove) && sailorToRemove.getCurrentVoyage() != null) {
			makeContractInactive(sailorToRemove);
			removeCrewMemberFromCrewList(voyageToUpdate, sailorToRemove);
			sailorToRemove.setCurrentVoyage(null);
		} else {
			throw new IllegalArgumentException("Sailor with id: " + sailorToRemove.getEmployeeId() + " is not part of a crew list.");
		}

		Voyage updatedVoyage = voyageRepository.save(voyageToUpdate);
		return ResponseEntity.ok(updatedVoyage);
	}

	private void removeCrewMemberFromCrewList(Voyage voyageToUpdate, Sailor sailorToRemove) {
		voyageToUpdate.getCrewList().remove(sailorToRemove);
	}

	private void saveNewContractForSailor(Sailor owner, Voyage voyageAtStart, LocalDate contractStartDate, LocalDate contractEndDate) {
		Contract newContract = new Contract(owner, contractStartDate, contractEndDate, voyageAtStart);
		contractRepository.save(newContract);
	}

	private void makeContractInactive(Sailor sailorToRemove) {
		Set<Contract> contracts = sailorToRemove.getContracts();

//		if (!contracts.isEmpty()) { // check if the set is not empty
//			for (Contract contract : contracts) {
//				if (contract.isActive()) {
//					contract.setActive(false);
//				}
//			}
//		}
		contracts.stream().filter(Contract::isActive).findFirst().get().setActive(false);

		//TODO -refactor
	}

	private void addCrewMemberToCrewList(Voyage voyageToUpdate, Sailor sailorToAdd) {
		voyageToUpdate.getCrewList().add(sailorToAdd);
	}

	private boolean isNotPartOfCrewList(Voyage voyageToUpdate, Sailor sailorToAdd) {
		return voyageToUpdate.getCrewList().stream()
				.noneMatch(s -> s.getEmployeeId().equals(sailorToAdd.getEmployeeId()));
	}
}
