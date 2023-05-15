package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.contract.Contract;
import com.codecool.seamanager.service.CertificateService;
import com.codecool.seamanager.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contract")
public class ContractController {

	private final ContractService contractService;

	@Autowired
	public ContractController(ContractService contractService) {
		this.contractService = ContractController.this.contractService;
	}

	@GetMapping
	public List<Contract> getContracts() {
		return contractService.getContracts();
	}

	@GetMapping(path = "/{contractId}")
	public Contract getContractById(@PathVariable("contractId") Long contractId){
		return contractService.getById(contractId);
	}

	@PostMapping()
	public void addNewContract(@RequestBody @Valid Contract contract) {
		contractService.addNewContract(contract);
	}

	@DeleteMapping(path = "/{contractId}")
	public void deleteContract(@PathVariable("contractId") Long contractId){
		contractService.deleteContract(contractId);
	}

	@PutMapping(path = "/{contractId}")
	public ResponseEntity<Contract> updateContract(@PathVariable Long contractId, @RequestBody @Valid Contract contractDetails){
		return contractService.updateContract(contractId, contractDetails);
	}
}
