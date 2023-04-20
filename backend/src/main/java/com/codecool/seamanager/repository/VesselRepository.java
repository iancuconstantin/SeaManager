package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.vessel.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VesselRepository extends JpaRepository<Vessel, Long> {

	@Query("SELECT v FROM Vessel v WHERE v.IMONumber = ?1")
	Optional<Vessel> findVesselByIMONumber(String IMONumber);
}
