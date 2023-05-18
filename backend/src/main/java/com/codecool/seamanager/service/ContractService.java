package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.contract.ContractExistsException;
import com.codecool.seamanager.exceptions.contract.ContractNotFoundException;
import com.codecool.seamanager.model.contract.Contract;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.repository.ContractRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
	private final ContractRepository contractRepository;

	@Autowired
	public ContractService(ContractRepository contractRepository) {
		this.contractRepository = ContractService.this.contractRepository;
	}

	public List<Contract> getContracts() {
		return contractRepository.findAll();
	}

	public Contract getById(@PathVariable Long id) {
		return contractRepository.findById(id)
				.orElseThrow(() -> new ContractNotFoundException(
						"Contract with id: " + id + " not found.")
				);
	}

	public void addNewContract(@RequestBody @Valid Contract contract) {

		//TODO handle owner`s other contracts

		Optional<Contract> contractOptional = contractRepository.findById(contract.getContractId());

		if (contractOptional.isPresent()) {
			throw new ContractExistsException(
					"Contract with id: " + contract.getContractId() + " already exists."
			);
		}

		contractRepository.save(contract);
	}


	public ResponseEntity<Contract> updateContract(@PathVariable Long id, @RequestBody @Valid Contract contract) {
		Contract contractToUpdate = contractRepository.findById(id)
				.orElseThrow(() -> new ContractNotFoundException(
						"Contract with id:" + id + "not found.")
				);

		Optional<Contract> contractOptional = contractRepository.findById(contract.getContractId());
		if(contractOptional.isPresent()){
			throw new ContractExistsException(
					"Contract with id:" + contract.getContractId() + " exists in database."
			);
		}

		//TODO - might not be needed

		Contract updatedContract = contractRepository.save(contractToUpdate);
		return ResponseEntity.ok(updatedContract);
	}

	public void deleteContract(@PathVariable Long id) {
		boolean exists = contractRepository.existsById(id);
		if (!exists) {
			throw new ContractNotFoundException("Contract with id: " + id + " not found.");
		}

		contractRepository.deleteById(id);
	}

}
