package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.vessel.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselRepository extends JpaRepository<Vessel, Long> {
}
