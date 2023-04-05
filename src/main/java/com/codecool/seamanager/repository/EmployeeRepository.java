package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e FROM Employee e WHERE e.c_email = ?1")
	Optional<Employee> findEmployeeByEmail(String email);
}
