package com.codecool.seamanager.repository;


import com.codecool.seamanager.model.employee.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.THIRD_ENGINEER;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeRepositoryTest {
	@Mock
	private EmployeeRepository employeeRepository;
	private Employee employee;

	@Before
	public void setUp(){
		employee = new Employee(
				"John",
				"Doe",
				"01-01-1990",
				"+123456789",
				"123 Main St",
				"johndoe@gmail.com",
				THIRD_ENGINEER,
				MALE
		);
	}

	@Test
	public void testFindEmployeeByEmail(){
		when(employeeRepository.findEmployeeByEmail(employee.getEmail())).thenReturn(Optional.of(employee));

	}
}
