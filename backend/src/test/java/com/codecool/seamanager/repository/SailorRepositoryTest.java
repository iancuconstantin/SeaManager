package com.codecool.seamanager.repository;


import com.codecool.seamanager.model.employee.Sailor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SailorRepositoryTest {
	@Mock
	private SailorRepository sailorRepository;
	private Sailor sailor;

	@Before
	public void setUp(){
		sailor = new Sailor(
				"John",
				"Doe",
				LocalDate.of(1990, 1, 1),
				"+123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				MALE
		);
	}

	@Test
	public void testFindEmployeeByEmail(){
		when(sailorRepository.findEmployeeByEmail(sailor.getEmail())).thenReturn(Optional.of(sailor));

		Optional<Sailor> foundEmployee = sailorRepository.findEmployeeByEmail(sailor.getEmail());

		assertTrue(foundEmployee.isPresent());
		assertEquals(foundEmployee.get(), sailor);

		verify(sailorRepository, times(1)).findEmployeeByEmail(sailor.getEmail());
	}
}
