package com.codecool.seamanager.service;

import com.codecool.seamanager.model.voyage.Voyage;
import com.codecool.seamanager.repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageService {

	private final VoyageRepository voyageRepository;

	@Autowired
	public VoyageService(VoyageRepository voyageRepository) {
		this.voyageRepository = voyageRepository;
	}

	public List<Voyage> getAllVoyages() {
		return voyageRepository.findAll();
	}

	public Voyage getById(Long id) {
		return voyageRepository.findById(id)
				//TODO - custom exception
				.orElseThrow(()-> new IllegalArgumentException("Voyage with id:" + id + " not found."));
	}

	public void deleteVoyage(Long id) {
		boolean exists = voyageRepository.existsById(id);

		if(!exists){
			//TODO - custom exception
			throw new IllegalArgumentException("Voyage with id:" + id + " not found.");
		}

		voyageRepository.deleteById(id);
	}
}
