package com.codecool.seamanager.service;

import com.codecool.seamanager.model.portinteraction.PortInteraction;
import com.codecool.seamanager.repository.PortInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortInteractionService {
	private final PortInteractionRepository portInteractionRepository;

	@Autowired
	public PortInteractionService(PortInteractionRepository portInteractionRepository) {
		this.portInteractionRepository = portInteractionRepository;
	}

	public List<PortInteraction> getAllPortInteractions() {
		return portInteractionRepository.findAll();
	}

	public PortInteraction getById(Long id) {
		return portInteractionRepository.findById(id)
				//TODO - custom exception
				.orElseThrow(() -> new IllegalArgumentException(
						"PortInteraction with id: " + id + " not found.")
				);
	}

	public void deletePortInteraction(Long id) {
		boolean exists = portInteractionRepository.existsById(id);

		if (!exists) {
			//TODO - custom exception
			throw new IllegalArgumentException(
					"PortInteraction with id: " + id + " not found."
			);
		}

		portInteractionRepository.deleteById(id);
	}
}
