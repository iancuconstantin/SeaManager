package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.certificate.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

	@Query("SELECT c FROM Certificate c WHERE c.c_serialNumber = ?1")
	Optional<Certificate> findCertificateBySerialNumber(String serialNumber);
}
