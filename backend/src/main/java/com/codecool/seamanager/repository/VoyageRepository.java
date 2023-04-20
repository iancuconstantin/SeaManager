package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.voyage.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
}
