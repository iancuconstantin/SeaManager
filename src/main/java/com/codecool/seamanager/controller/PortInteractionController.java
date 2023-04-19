package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.portinteraction.PortInteraction;
import com.codecool.seamanager.service.PortInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("api/port-interaction")
public class PortInteractionController {

	private final PortInteractionService portInteractionService;

	@Autowired
	public PortInteractionController(PortInteractionService portInteractionService) {
		this.portInteractionService = portInteractionService;
	}

	@GetMapping
	public List<PortInteraction> getAllPortInteractions() {
		return portInteractionService.getAllPortInteractions();
	}

	@GetMapping("/{portInteractionId}")
	public PortInteraction getById(@PathVariable("portInteractionId") Long portInteractionId) {
		return portInteractionService.getById(portInteractionId);
	}

	@DeleteMapping("/{portInteractionId}")
	public void deletePortInteraction(@PathVariable("portInteractionId") Long portInteractionId) {
		portInteractionService.deletePortInteraction(portInteractionId);
	}
}
