package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.port.Port;
import com.codecool.seamanager.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("api/port")
public class PortController {
	private final PortService portService;

	@Autowired
	public PortController(PortService portService) {
		this.portService = portService;
	}

	@GetMapping
	public List<Port> getAllPorts() {
		return portService.getAllPorts();
	}

	@GetMapping("/{portId}")
	public Port getById(@PathVariable("portId") Long portId) {
		return portService.getById(portId);
	}

	@DeleteMapping("/{portId}")
	public void deletePort(@PathVariable("portId") Long portId) {
		portService.deletePort(portId);
	}
}
