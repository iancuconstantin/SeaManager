package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.service.VesselService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/vessel")
public class VesselController {

    @Autowired
    private VesselService vesselService;

    @GetMapping
    public List<Vessel> getAllVessels() {
        return vesselService.getAllVessels();
    }

    @GetMapping("/{id}")
    public Vessel getById(@PathVariable Long id) {
        return vesselService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<Vessel> createVessel(@RequestBody @Valid Vessel vessel) {
        return  vesselService.createVessel(vessel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vessel> updateVessel(@PathVariable Long id, @RequestBody @Valid Vessel vessel) {
        return vesselService.updateVessel(id,vessel);
    }

    @DeleteMapping("/{id}")
    public void deleteVessel(@PathVariable Long id) {
        vesselService.deleteVessel(id);
    }
}
