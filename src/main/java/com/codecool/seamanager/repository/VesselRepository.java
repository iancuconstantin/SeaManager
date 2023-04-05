package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VesselRepository extends JpaRepository<Vessel, Long> {
}
