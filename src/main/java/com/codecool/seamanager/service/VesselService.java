package com.codecool.seamanager.service;

import com.codecool.seamanager.model.Certificate;
import com.codecool.seamanager.model.Employee;
import com.codecool.seamanager.model.Vessel;
import com.codecool.seamanager.repository.CertificateRepository;
import com.codecool.seamanager.repository.EmployeeRepository;
import com.codecool.seamanager.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VesselService {
    @Autowired
    private VesselRepository vesselRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Vessel> getAllVessels(){return vesselRepository.findAll();}

    public ResponseEntity<Vessel> getById(@PathVariable Long id) {
        Optional<Vessel> optionalUser = vesselRepository.findById(id);
        if (optionalUser.isPresent()) {
            Vessel vessel = optionalUser.get();
            return ResponseEntity.ok(vessel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<Vessel> createVessel(@RequestBody Vessel vessel) {
        Vessel savedVessel = vesselRepository.save(vessel);
        return ResponseEntity.created(URI.create("/user/" + savedVessel.getId())).body(savedVessel);
    }


    public ResponseEntity<Vessel> updateVessel(@PathVariable Long id, @RequestBody Vessel vessel) {
        Optional<Vessel> optionalVessel = vesselRepository.findById(id);
        if (optionalVessel.isPresent()) {
            Vessel existingVessel = optionalVessel.get();
            existingVessel.setName(vessel.getName());
            existingVessel.setType(vessel.getType());
            existingVessel.setFlag(vessel.getFlag());
            existingVessel.setIMONumber(vessel.getIMONumber());
            existingVessel.setNextPortOfCall(vessel.getNextPortOfCall());
            Vessel updatedVessel = vesselRepository.save(existingVessel);
            return ResponseEntity.ok(updatedVessel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteVessel(@PathVariable Long id) {
        Optional<Vessel> optionalVessel = vesselRepository.findById(id);
        if (optionalVessel.isPresent()) {
            vesselRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
