package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.certificate.CertificateNotFoundException;
import com.codecool.seamanager.exceptions.certificate.CertificateSerialNumberDuplicationException;
import com.codecool.seamanager.exceptions.sailor.SailorNotFoundException;
import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.repository.CertificateRepository;
import com.codecool.seamanager.repository.SailorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateService {
	private final CertificateRepository certificateRepository;
	private final SailorRepository sailorRepository;

	@Autowired
	public CertificateService(CertificateRepository certificateRepository, SailorRepository sailorRepository) {
		this.certificateRepository = certificateRepository;
		this.sailorRepository = sailorRepository;
	}

	public List<Certificate> getCertificates() {
		return certificateRepository.findAll();
	}

	public Certificate getCertificateById(Long certificateId) {
		return certificateRepository.findById(certificateId)
				.orElseThrow(() -> new CertificateNotFoundException(
						"Certificate with id " + certificateId + " does not exist."
				));
	}

	public void addNewCertificate(Certificate certificate) {
		Optional<Certificate> certificateOptional = certificateRepository.findCertificateBySerialNumber(certificate.getSerialNumber());
		if (certificateOptional.isPresent()) {
			throw new CertificateSerialNumberDuplicationException(
					"Serial number " + certificate.getSerialNumber() + " exists in database."
			);
		}

		certificateRepository.saveAndFlush(certificate);
	}

	public void deleteCertificate(Long certificateId) {
		boolean exists = certificateRepository.existsById(certificateId);

		if (!exists) {
			throw new CertificateNotFoundException(
					"Certificate with id " + certificateId + " does not exist."
			);
		}

		certificateRepository.deleteById(certificateId);
	}

	public ResponseEntity<Certificate> updateCertificate(Long certificateId, Certificate certificateDetails) {
		Certificate certificateToUpdate = certificateRepository.findById(certificateId)
				.orElseThrow(() -> new CertificateNotFoundException(
						"Certificate with id " + certificateId + " does not exist."
				));

		Sailor updatedOwner = certificateDetails.getOwner();
		if (updatedOwner != null &&
				!certificateToUpdate.getOwner().getEmployeeId()
						.equals(certificateDetails.getOwner().getEmployeeId())
		) {
			boolean ownerExists = sailorRepository.existsById(certificateDetails.getOwner().getEmployeeId());
			if (!ownerExists) {
				throw new SailorNotFoundException(
						"Owner of this certificate with id " + certificateDetails.getOwner().getEmployeeId() + " does not exist."
				);
			}
			certificateToUpdate.setOwner(updatedOwner);
		}

		certificateToUpdate.setType(certificateDetails.getType());
		certificateToUpdate.setIssueDate(certificateDetails.getIssueDate());
		certificateToUpdate.setExpiryDate(certificateDetails.getExpiryDate());

		String updatedSerialNo = certificateDetails.getSerialNumber();
		Optional<Certificate> certificateOptional = certificateRepository.findCertificateBySerialNumber(certificateDetails.getSerialNumber());
		if (certificateOptional.isPresent()) {
			throw new CertificateSerialNumberDuplicationException(
					"Serial number " + certificateDetails.getSerialNumber() + " exists in database."
			);
		}

		certificateToUpdate.setSerialNumber(updatedSerialNo);
		Certificate updatedCertificate = certificateRepository.saveAndFlush(certificateToUpdate);
		return ResponseEntity.ok(updatedCertificate);
	}
}
