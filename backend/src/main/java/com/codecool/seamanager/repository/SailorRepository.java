package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.employee.Rank;
import com.codecool.seamanager.model.employee.Sailor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SailorRepository extends JpaRepository<Sailor, Long> {

	@Query("SELECT s FROM Sailor s WHERE s.email = ?1")
	Optional<Sailor> findEmployeeByEmail(String email);

	@Query("SELECT s FROM Sailor s ORDER BY s.employeeId ASC")
	List<Sailor> findAll();

	@Query("SELECT s FROM Sailor s WHERE s.rank = ?1")
	List<Sailor> findByRank(Rank rank);
}
