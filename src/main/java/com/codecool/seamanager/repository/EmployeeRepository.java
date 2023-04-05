package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
