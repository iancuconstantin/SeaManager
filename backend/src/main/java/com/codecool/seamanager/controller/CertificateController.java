package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.service.CertificateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/certificate")
public class CertificateController {

	private final CertificateService certificateService;

	@Autowired
	public CertificateController(CertificateService certificateService) {
		this.certificateService = certificateService;
	}

	@GetMapping
	public List<Certificate> getCertificates() {
		return certificateService.getCertificates();
	}

	@GetMapping(path = "/{certificateId}")
	public Certificate getCertificateById(@PathVariable("certificateId") Long certificateId){
		return certificateService.getCertificateById(certificateId);
	}

	@PostMapping()
	public void addNewCertificate(@RequestBody @Valid Certificate certificate) {
		certificateService.addNewCertificate(certificate);
	}

	@DeleteMapping(path = "/{certificateId}")
	public void deleteCertificate(@PathVariable("certificateId") Long certificateId){
		certificateService.deleteCertificate(certificateId);
	}

	@PutMapping(path = "/{certificateId}")
	public ResponseEntity<Certificate> updateCertificate(@PathVariable Long certificateId, @RequestBody @Valid Certificate certificateDetails){
		return certificateService.updateCertificate(certificateId, certificateDetails);
	}
}
