package com.codecool.seamanager.service;

import com.codecool.seamanager.model.employee.Employee;
import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.model.vessel.VesselType;
import com.codecool.seamanager.repository.EmployeeRepository;
import com.codecool.seamanager.repository.VesselRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.sql.Array;
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
        Optional<Vessel> optionalVessel = vesselRepository.findById(id);
        if (optionalVessel.isPresent()) {
            Vessel vessel = optionalVessel.get();
            return ResponseEntity.ok(vessel);
        } else {
            throw new EntityNotFoundException("Vessel not found with id: " + id);
        }
    }
    public ResponseEntity<Vessel> createVessel(@RequestBody Vessel vessel) {
        Vessel savedVessel = vesselRepository.save(vessel);
        return ResponseEntity.created(URI.create("/vessel/" + savedVessel.getId())).body(savedVessel);
    }


    public ResponseEntity<Vessel> updateVessel(@PathVariable Long id, @RequestBody Vessel vessel) {
        Optional<Vessel> optionalVessel = vesselRepository.findById(id);
        if (optionalVessel.isPresent()) {
            Vessel existingVessel = optionalVessel.get();
            if(vessel.getName().length()>0) existingVessel.setName(vessel.getName());
            if(vessel.getType() != null && vessel.getType() instanceof VesselType) existingVessel.setType(vessel.getType());
            if(vessel.getFlag()!= null) existingVessel.setFlag(vessel.getFlag());
            if(vessel.getIMONumber()!=-1) existingVessel.setIMONumber(vessel.getIMONumber());
            if(vessel.getNextPortOfCall()!= null) existingVessel.setNextPortOfCall(vessel.getNextPortOfCall());
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

    public void addEmployeeOnCrew(@PathVariable Long vesselId, @PathVariable Long employeeId){
        Optional<Vessel> optionalVessel = vesselRepository.findById(vesselId);
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if(optionalVessel.isPresent() && optionalEmployee.isPresent()) {
            Vessel existingVessel = optionalVessel.get();
            Employee existingEmployee = optionalEmployee.get();
            existingVessel.addCrew(existingEmployee);
            vesselRepository.save(existingVessel);
            existingEmployee.setI_vessel(existingVessel);
            employeeRepository.save(existingEmployee);
            System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvv "+existingVessel.getCrewList().size());
//            return ResponseEntity.ok(existingVessel);
        }
    }
}
