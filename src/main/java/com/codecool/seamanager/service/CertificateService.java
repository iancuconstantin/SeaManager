package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.certificate.CertificateNotFoundException;
import com.codecool.seamanager.exceptions.certificate.CertificateSerialNumberDuplicationException;
import com.codecool.seamanager.exceptions.email.EmployeeNotFoundException;
import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.employee.Employee;
import com.codecool.seamanager.repository.CertificateRepository;
import com.codecool.seamanager.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateService {
	private final CertificateRepository certificateRepository;
	private final EmployeeRepository employeeRepository;

	@Autowired
	public CertificateService(CertificateRepository certificateRepository, EmployeeRepository employeeRepository) {
		this.certificateRepository = certificateRepository;
		this.employeeRepository = employeeRepository;
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

	@Transactional
	public void updateCertificate(Long certificateId, Certificate certificateDetails) {
		System.out.println(certificateDetails);
		Certificate certificateToUpdate = certificateRepository.findById(certificateId)
				.orElseThrow(() -> new CertificateNotFoundException(
						"Certificate with id " + certificateId + " does not exist."
				));

		Employee updatedOwner = certificateDetails.getOwner();
		if (updatedOwner != null &&
				!certificateToUpdate.getOwner().getEmployeeId()
						.equals(certificateDetails.getOwner().getEmployeeId())
		) {
			boolean ownerExists = employeeRepository.existsById(certificateDetails.getOwner().getEmployeeId());
			if (!ownerExists) {
				throw new EmployeeNotFoundException(
						"Owner of this certificate with id " + certificateDetails.getOwner().getEmployeeId() +
								" and name " + certificateDetails.getOwner().getFirstName() + certificateDetails.getOwner().getLastName() +
								" does not exist."
				);
			}
			certificateToUpdate.setA_owner(updatedOwner);
		}

		String updatedDescription = certificateDetails.getDescription();
		if (updatedDescription != null &&
				updatedDescription.length() > 0 &&
				!updatedDescription.equals(certificateToUpdate.getDescription())
		) {
			certificateToUpdate.setB_description(updatedDescription);
		}

		String updatedIssueDate = certificateDetails.getIssueDate();
		if (updatedIssueDate != null &&
				updatedIssueDate.length() > 0 &&
				!updatedIssueDate.equals(certificateToUpdate.getIssueDate())
		) {
			System.out.println("IN ISSUE UPDATE");
			certificateToUpdate.setD_issueDate(updatedIssueDate);
		}

		String updatedExpiryDate = certificateDetails.getExpiryDate();
		if (updatedExpiryDate != null &&
				updatedExpiryDate.length() > 0 &&
				!updatedExpiryDate.equals(certificateToUpdate.getExpiryDate())
		) {
			System.out.println("IN EXP UPDATE");
			certificateToUpdate.setE_expiryDate(updatedExpiryDate);
		}

		String updatedSerialNo = certificateDetails.getSerialNumber();
		if (updatedSerialNo != null &&
				updatedSerialNo.length() > 0 &&
				!updatedSerialNo.equals(certificateToUpdate.getSerialNumber())
		) {
			System.out.println("IN SERIALNO UPDATE");
			certificateToUpdate.setC_serialNumber(updatedSerialNo);
		}
	}
}
