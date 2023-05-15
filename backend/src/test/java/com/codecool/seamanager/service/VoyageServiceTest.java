package com.codecool.seamanager.service;

import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.port.Port;
import com.codecool.seamanager.model.portinteraction.PortInteraction;
import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.model.voyage.Voyage;
import com.codecool.seamanager.repository.SailorRepository;
import com.codecool.seamanager.repository.VoyageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.SECOND_ENGINEER;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static com.codecool.seamanager.model.portinteraction.PortInteractionType.LOAD;
import static com.codecool.seamanager.model.vessel.VesselType.BULK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VoyageServiceTest {

	@Mock
	private VoyageRepository voyageRepository;
	@Mock
	private SailorRepository sailorRepository;
	@InjectMocks
	private VoyageService voyageService;
	@Mock
	private SailorService sailorService;
	private Voyage voyage;
	private Vessel vessel;
	private Sailor sailor1;
	private Sailor sailor2;
	private Port arrivalPort;
	private Port departurePort;
	private PortInteraction arrival;
	private PortInteraction departure;
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() {
		vessel = new Vessel(
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
		sailor1 = new Sailor(
				"John",
				"Doe",
				LocalDate.of(1990, 1, 1),
				"123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				MALE,
				null
		);
		sailor2 = new Sailor(
				"Jane",
				"Doe",
				LocalDate.of(1991, 11, 1),
				"987654321",
				"456 Main St",
				"janedoe@gmail.com",
				SECOND_ENGINEER,
				FEMALE,
				null
		);

		arrivalPort = new Port("Abidjan", false);
		departurePort = new Port("Jorf Lasfar", false);

		arrival = new PortInteraction(LOAD, arrivalPort, LocalDate.of(2023, 6, 5));
		departure = new PortInteraction(LOAD, departurePort, LocalDate.of(2023, 5, 6));

		voyage = new Voyage(vessel, Set.of(sailor1), arrival, departure);

		restTemplate = new TestRestTemplate(new RestTemplateBuilder());
	}


	@Test
	public void testAddNewCrewMember(){
		List<Voyage> expectedVoyages = List.of(voyage);
		when(voyageRepository.findAll()).thenReturn(expectedVoyages);
		when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));
		//when(voyageService.getById(1L)).thenReturn(Optional.of(voyage));
		when(sailorRepository.findById(2L)).thenReturn(Optional.of(sailor2));
		//when(sailorService.getEmployeeById(2L)).thenReturn(sailor2);
		voyageService.addNewCrewMember(1L, 2L, LocalDate.now().minusDays(15), LocalDate.now().plusMonths(5));
		assertEquals(voyage.getCrewList().size(), 2);
		//TODO
	}


	@Test
	public void testRemoveCrewMember(){
		//TODO
	}

}
