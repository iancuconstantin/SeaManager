package com.codecool.seamanager.model.vessel;

import org.junit.Before;
import org.junit.Test;

import static com.codecool.seamanager.model.vessel.VesselType.BULK;
import static org.junit.jupiter.api.Assertions.*;

public class VesselTest {

	private Vessel vessel;

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
	}

	@Test
	public void testGetName() {
		assertEquals("SS Pacific Voyager", vessel.getName());
	}

	@Test
	public void testGetType() {
		assertEquals(BULK, vessel.getType());
	}

	@Test
	public void testGetFlag() {
		assertEquals("Liberia", vessel.getFlag());
	}

	@Test
	public void testGetMMSI() {
		assertEquals("123456789", vessel.getMMSI());
	}

	@Test
	public void testGetCallSign() {
		assertEquals("D7WF1", vessel.getCallSign());
	}

	@Test
	public void testGetEmail() {
		assertEquals("vessel2@company.com", vessel.getEmail());
	}

	@Test
	public void testGetIMONumber() {
		assertEquals("9903133", vessel.getIMONumber());
	}

	@Test
	public void testGetYearBuilt() {
		assertEquals("2015", vessel.getYearBuilt());
	}

	@Test
	public void testGetDWT() {
		assertEquals("24856", vessel.getDWT());
	}

	@Test
	public void testGetGRT() {
		assertEquals("28992", vessel.getGRT());
	}

	@Test
	public void testSetCallSign() {
		vessel.setCallSign("AABB5");
		assertEquals("AABB5", vessel.getCallSign());
	}

	@Test
	public void testSetEmail() {
		vessel.setEmail("newmail@yahoo.com");
		assertEquals("newmail@yahoo.com", vessel.getEmail());
	}

	@Test
	public void testSetIMONumber() {
		vessel.setIMONumber("1234566");
		assertEquals("1234566", vessel.getIMONumber());
	}

	@Test
	public void testSetYearBuilt() {
		vessel.setYearBuilt("2001");
		assertEquals("2001", vessel.getYearBuilt());
	}
}
