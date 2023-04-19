package com.codecool.seamanager.service;

import com.codecool.seamanager.model.port.Port;
import com.codecool.seamanager.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortService {
	private final PortRepository portRepository;

	@Autowired
	public PortService(PortRepository portRepository) {
		this.portRepository = portRepository;
	}

	public List<Port> getAllPorts() {
		return portRepository.findAll();
	}

	public Port getById(Long id) {
		return portRepository.findById(id)
				//TODO - custom exception
				.orElseThrow(() -> new IllegalArgumentException(
						"Port with id: " + id + " not found.")
				);
	}

	public void deletePort(Long id) {
		boolean exists = portRepository.existsById(id);

		if (!exists) {
			//TODO - custom exception
			throw new IllegalArgumentException(
					"Port with id: " + id + " not found."
			);
		}

		portRepository.deleteById(id);
	}
}
