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
    @Autowired
    private CertificateRepository certificateRepository;

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
//    public ResponseEntity<Vessel> updateVessel(@PathVariable Long id, @RequestBody Vessel vessel) {
//        Optional<Vessel> optionalVessel = vesselRepository.findById(id);
//        if (optionalVessel.isPresent()) {
//            Vessel existingVessel = optionalVessel.get();
//            existingVessel.setName(vessel.getName());
//            existingVessel.setType(vessel.getType());
//            existingVessel.setFlag(vessel.getFlag());
//            existingVessel.setIMONumber(vessel.getIMONumber());
//            existingVessel.setNextPortOfCall(vessel.getNextPortOfCall());
//            //update crew list
//            List<Employee> existingCrewList = existingVessel.getCrewList();
//            List<Employee> updatedCrewList = vessel.getCrewList();
//            for (int i = 0; i < updatedCrewList.size(); i++) {
//                Employee updatedCrewMember = updatedCrewList.get(i);
//                Employee existingCrewMember = existingCrewList.get(i);
//                existingCrewMember.setFirstName(updatedCrewMember.getFirstName());
//                existingCrewMember.setLastName(updatedCrewMember.getLastName());
//                existingCrewMember.setBirthDate(updatedCrewMember.getBirthDate());
//                existingCrewMember.setAddress(updatedCrewMember.getAddress());
//                existingCrewMember.setRank(updatedCrewMember.getRank());
//                existingCrewMember.setCertificates(updatedCrewMember.getCertificates());
//                Vessel updatedVessel = vesselRepository.save(existingVessel);
//                return ResponseEntity.ok(updatedVessel);
//            }
//        } else{
//                return ResponseEntity.notFound().build();
//            }
//        };
//    }

//    public ResponseEntity<Vessel> updateVessel(@PathVariable Long id, @RequestBody Vessel vessel) {
//        Optional<Vessel> optionalVessel = vesselRepository.findById(id);
//        if (optionalVessel.isPresent()) {
//            Vessel existingVessel = optionalVessel.get();
//            existingVessel.setName(vessel.getName());
//            existingVessel.setType(vessel.getType());
//            existingVessel.setFlag(vessel.getFlag());
//            existingVessel.setIMONumber(vessel.getIMONumber());
//            existingVessel.setNextPortOfCall(vessel.getNextPortOfCall());
//
//            // update crew members in existingVessel instance
//            List<Employee> existingCrewList = existingVessel.getCrewList();
//            List<Employee> updatedCrewList = vessel.getCrewList();
//            for (int i = 0; i < updatedCrewList.size(); i++) {
//                Employee updatedCrewMember = updatedCrewList.get(i);
//                Employee existingCrewMember = existingCrewList.get(i);
//                existingCrewMember.setFirstName(updatedCrewMember.getFirstName());
//                existingCrewMember.setLastName(updatedCrewMember.getLastName());
//                existingCrewMember.setBirthDate(updatedCrewMember.getBirthDate());
//                existingCrewMember.setAddress(updatedCrewMember.getAddress());
//                existingCrewMember.setRank(updatedCrewMember.getRank());
//                existingCrewMember.setCertificates(updatedCrewMember.getCertificates());
//            }
//
//            Vessel updatedVessel = vesselRepository.save(existingVessel);
//            return ResponseEntity.ok(updatedVessel);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


    public ResponseEntity<Vessel> updateVessel(@PathVariable Long id, @RequestBody Vessel vessel) {
        Optional<Vessel> optionalVessel = vesselRepository.findById(id);
        if (optionalVessel.isPresent()) {
            Vessel existingVessel = optionalVessel.get();
            existingVessel.setName(vessel.getName());
            existingVessel.setType(vessel.getType());
            existingVessel.setFlag(vessel.getFlag());
            existingVessel.setIMONumber(vessel.getIMONumber());
            existingVessel.setNextPortOfCall(vessel.getNextPortOfCall());

            // Update crew list
            List<Employee> existingCrew = existingVessel.getCrewList();
            List<Employee> newCrew = vessel.getCrewList();
            existingCrew.clear();
            for (Employee crewMember : newCrew) {
                Employee existingCrewMember = employeeRepository.findById(crewMember.getId()).orElse(null);
                if (existingCrewMember == null) {
                    // New crew member
                    existingCrewMember = new Employee();
                }
                existingCrewMember.setFirstName(crewMember.getFirstName());
                existingCrewMember.setLastName(crewMember.getLastName());
                existingCrewMember.setBirthDate(crewMember.getBirthDate());
                existingCrewMember.setAddress(crewMember.getAddress());
                existingCrewMember.setRank(crewMember.getRank());

                // Update certificates for this crew member
                List<Certificate> existingCertificates = existingCrewMember.getCertificates();
                List<Certificate> newCertificates = crewMember.getCertificates();
                existingCertificates.clear();
                for (Certificate certificate : newCertificates) {
                    Certificate existingCertificate = certificateRepository.findById(certificate.getId()).orElse(null);
                    if (existingCertificate == null) {
                        // New certificate
                        existingCertificate = new Certificate();
                    }
                    existingCertificate.setDescription(certificate.getDescription());
                    existingCertificate.setSerialNumber(certificate.getSerialNumber());
                    existingCertificate.setIssueDate(certificate.getIssueDate());
                    existingCertificate.setExpiryDate(certificate.getExpiryDate());
                    existingCertificates.add(existingCertificate);
                }
                existingCrew.add(existingCrewMember);
            }

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
