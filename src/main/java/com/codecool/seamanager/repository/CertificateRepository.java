package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
