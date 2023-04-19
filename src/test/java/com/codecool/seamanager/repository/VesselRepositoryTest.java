package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.vessel.Vessel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.codecool.seamanager.model.vessel.VesselType.BULK;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VesselRepositoryTest {
	@Mock
	private VesselRepository vesselRepository;
	private Vessel vessel;

	@Before
	public void setUp(){
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
	}

	@Test
	public void testFindVesselByIMONumber(){
		when(vesselRepository.findVesselByIMONumber(vessel.getIMONumber())).thenReturn(Optional.of(vessel));

		Optional<Vessel> foundVessel = vesselRepository.findVesselByIMONumber(vessel.getIMONumber());
		assertTrue(foundVessel.isPresent());
		assertEquals(foundVessel.get(), vessel);

		verify(vesselRepository, times(1)).findVesselByIMONumber(vessel.getIMONumber());

	}
}
