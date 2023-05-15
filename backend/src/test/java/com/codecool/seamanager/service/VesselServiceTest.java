package com.codecool.seamanager.service;

import com.codecool.seamanager.exceptions.vessel.IMONumberExistsException;
import com.codecool.seamanager.exceptions.vessel.VesselNotFoundException;
import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.repository.VesselRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codecool.seamanager.model.vessel.VesselType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VesselServiceTest {
	@Mock
	private VesselRepository vesselRepository;
	@InjectMocks
	private VesselService vesselService;
	private Vessel vessel1;
	private Vessel vessel2;
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() {
		vessel1 = new Vessel(
				"SS Pacific Voyager",
				BULK,
				"Liberia",
				"123456789",
				"vessel2@company.com",
				"D7WF1",
				"9903133",
				"2015",
				"24856",
				"28992"
		);

		vessel2 = new Vessel(
				"Ocean Queen",
				TANKER,
				"Denmark",
				"222333444",
				"vessel1@company.com",
				"A5BB3",
				"1234567",
				"2020",
				"23456",
				"25412"
		);

		restTemplate = new TestRestTemplate(new RestTemplateBuilder());
	}

	@Test
	public void testGetVessels() {
		List<Vessel> expectedVessels = List.of(vessel1, vessel2);
		when(vesselRepository.findAll()).thenReturn(expectedVessels);
		List<Vessel> actualVessels = vesselService.getAllVessels();
		assertEquals(expectedVessels, actualVessels);
	}

	@Test
	public void testGetVesselById() {
		when(vesselRepository.findById(1L)).thenReturn(Optional.of(vessel2));
		Vessel actualVessel = vesselService.getById(1L);
		assertEquals(vessel2, actualVessel);
	}

	@Test(expected = VesselNotFoundException.class)
	public void testGetEmployeeByIdNotFound() {
		when(vesselRepository.findById(1L)).thenReturn(Optional.empty());
		vesselService.getById(1L);
	}

	@Test
	public void testCreateVessel() {
		when(vesselRepository.findVesselByIMONumber(vessel1.getIMONumber())).thenReturn(Optional.empty());
		vesselService.createVessel(vessel1);
		verify(vesselRepository).save(vessel1);
	}

	@Test(expected = IMONumberExistsException.class)
	public void testAddNewVesselShouldThrowIMONumberExistsException() {
		when(vesselRepository.findVesselByIMONumber(vessel2.getIMONumber())).thenReturn(Optional.of(vessel2));
		vesselService.createVessel(vessel2);
	}

	@Test
	public void testDeleteVessel() {
		when(vesselRepository.existsById(1L)).thenReturn(true);
		vesselService.deleteVessel(1L);
		verify(vesselRepository).deleteById(1L);
	}

	@Test(expected = VesselNotFoundException.class)
	public void testDeleteVesselNotFound() {
		when(vesselRepository.existsById(1L)).thenReturn(false);
		vesselService.deleteVessel(1L);
	}

	@Test
	public void testUpdateVessel() {
		Vessel updatedVesselDetails = new Vessel(
				"newname",
				GAS_CARRIER,
				"newflag",
				"333444555",
				"newmail@company.co.uk",
				"BB43A",
				"3334445",
				"2019",
				"21333",
				"24333"
		);
		// Create a HttpHeaders object to set the Content-Type header to application/json
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create a HttpEntity object with the updatedVesselDetails and headers
		HttpEntity<Vessel> request = new HttpEntity<>(updatedVesselDetails, headers);

		// Send a PUT request to the updateVessel endpoint using RestTemplate
		ResponseEntity<Vessel> responseEntity = restTemplate.exchange(
				"http://localhost:8080/api/vessel/{id}",
				HttpMethod.PUT,
				request,
				Vessel.class,
				1L
		);

		// Assert the response
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Vessel updatedVessel = responseEntity.getBody();
		assertNotNull(updatedVessel);
		assertEquals("newname", updatedVessel.getName());
		assertEquals("newmail@company.co.uk", updatedVessel.getEmail());
		assertEquals("BB43A", updatedVessel.getCallSign());
		assertEquals("2019", updatedVessel.getYearBuilt());
		assertEquals("21333", updatedVessel.getDWT());
		assertEquals("24333", updatedVessel.getGRT());
	}
}
