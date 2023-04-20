package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.port.Port;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortRepository extends JpaRepository<Port, Long> {
}
