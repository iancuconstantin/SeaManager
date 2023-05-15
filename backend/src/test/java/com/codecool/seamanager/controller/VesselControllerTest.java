package com.codecool.seamanager.controller;

import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.service.VesselService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codecool.seamanager.model.vessel.VesselType.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RunWith(MockitoJUnitRunner.class)
public class VesselControllerTest {

	private static final String END_POINT_PATH = "/api/vessel";

	private Vessel vessel1;
	private Vessel vessel2;
	private MockMvc mockMvc;
	@Mock
	private VesselService vesselService;
	@InjectMocks
	private VesselController vesselController;

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

		mockMvc = MockMvcBuilders.standaloneSetup(vesselController).build();
	}

	@Test
	public void shouldReturnListOfVessels() throws Exception {
		List<Vessel> vessels = List.of(vessel1, vessel2);
		when(vesselService.getAllVessels()).thenReturn(vessels);

		mockMvc.perform(get(END_POINT_PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name").value(vessel1.getName()))
				.andExpect(jsonPath("$[0].flag").value(vessel1.getFlag()))
				.andExpect(jsonPath("$[0].email").value(vessel1.getEmail()))
				.andExpect(jsonPath("$[0].imonumber").value(vessel1.getIMONumber()))
				.andExpect(jsonPath("$[0].yearBuilt").value(vessel1.getYearBuilt()))
				.andExpect(jsonPath("$[1].name").value(vessel2.getName()))
				.andExpect(jsonPath("$[1].imonumber").value(vessel2.getIMONumber()))
				.andExpect(jsonPath("$[1].dwt").value(vessel2.getDWT()))
				.andExpect(jsonPath("$[1].grt").value(vessel2.getGRT()))
				.andExpect(jsonPath("$[1].type").value(vessel2.getType().toString()));
	}

	@Test
	public void shouldReturnVesselById() throws Exception {
		Long vesselId = 1L;
		when(vesselService.getById(vesselId)).thenReturn(vessel1);

		mockMvc.perform(get(END_POINT_PATH + "/" + vesselId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.name").value(vessel1.getName()))
				.andExpect(jsonPath("$.flag").value(vessel1.getFlag()))
				.andExpect(jsonPath("$.email").value(vessel1.getEmail()))
				.andExpect(jsonPath("$.yearBuilt").value(vessel1.getYearBuilt()))
				.andExpect(jsonPath("$.imonumber").value(vessel1.getIMONumber()))
				.andExpect(jsonPath("$.dwt").value(vessel1.getDWT()))
				.andExpect(jsonPath("$.grt").value(vessel1.getGRT()))
				.andExpect(jsonPath("$.type").value(vessel1.getType().toString()))
				.andExpect(jsonPath("$.callSign").value(vessel1.getCallSign()))
				.andExpect(jsonPath("$.mmsi").value(vessel1.getMMSI()));
	}

	@Test
	public void shouldAddNewVessel() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String vesselJson = mapper.writeValueAsString(vessel1);

		mockMvc.perform(post(END_POINT_PATH).contentType(APPLICATION_JSON).content(vesselJson))
				.andExpect(status().isOk());

		verify(vesselService, times(1)).createVessel(any(Vessel.class));
	}

	@Test
	public void shouldDeleteVesselById() throws Exception {
		Long vesselId = 1L;

		mockMvc.perform(delete(END_POINT_PATH + "/" + vesselId, vesselId))
				.andExpect(status().isOk());

		verify(vesselService, times(1)).deleteVessel(vesselId);
	}

	@Test
	public void shouldUpdateVessel() throws Exception {
		Long vesselId = 1L;
		Vessel updateVessel = new Vessel(
				"New Name",
				CHEMICAL,
				"Singapore",
				"987654321",
				"newemail@company.com",
				"BCA22",
				"3334445",
				"2002",
				"19999",
				"21222"
		);
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String vesselJson = mapper.writeValueAsString(updateVessel);

		when(vesselService.updateVessel(eq(vesselId), any(Vessel.class))).thenReturn(ResponseEntity.of(Optional.of(updateVessel)));
		mockMvc.perform(put(END_POINT_PATH + "/" + vesselId)
						.contentType(APPLICATION_JSON)
						.content(vesselJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(updateVessel.getName()))
				.andExpect(jsonPath("$.type").value(updateVessel.getType().toString()))
				.andExpect(jsonPath("$.email").value(updateVessel.getEmail()))
				.andExpect(jsonPath("$.flag").value(updateVessel.getFlag()))
				.andExpect(jsonPath("$.mmsi").value(updateVessel.getMMSI()))
				.andExpect(jsonPath("$.dwt").value(updateVessel.getDWT()))
				.andExpect(jsonPath("$.grt").value(updateVessel.getGRT()))
				.andExpect(jsonPath("$.callSign").value(updateVessel.getCallSign()))
				.andExpect(jsonPath("$.imonumber").value(updateVessel.getIMONumber()))
				.andExpect(jsonPath("$.yearBuilt").value(updateVessel.getYearBuilt()));

		verify(vesselService, times(1)).updateVessel(eq(vesselId), any(Vessel.class));
	}
}
