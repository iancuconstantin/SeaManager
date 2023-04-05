package com.codecool.seamanager.config;

import com.codecool.seamanager.model.employee.Employee;
import com.codecool.seamanager.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.*;

@Configuration
public class EmployeeConfiguration {

	@Bean
	CommandLineRunner commandLineRunner(
			EmployeeRepository repository) {
		return args -> {
			Employee johnDoe = new Employee(
					"John",
					"Doe",
					"01-01-1990",
					"+123456789",
					"123 Main St",
					"johndoe@gmail.com",
					THIRD_ENGINEER,
					MALE
			);
			Employee janeDoe = new Employee(
					"Jane",
					"Doe",
					"01-01-1990",
					"+1223556789",
					"123 Main St",
					"janedoe@yahoo.com",
					DECK_CADET,
					FEMALE
			);
			Employee marian = new Employee(
					"Marian",
					"Ionescu",
					"11-02-1977",
					"+4072355678",
					"Str. Virtutii 15D",
					"marian_i@yahoo.com",
					CHIEF_ENGINEER,
					MALE
			);
			Employee claudiu = new Employee(
					"Claudiu Mihai",
					"Tudor",
					"13-02-1992",
					"+40728949363",
					"Semilunei 4-6",
					"claudium.tudor@gmail.com",
					SECOND_OFFICER,
					MALE
			);
			Employee george = new Employee(
					"George",
					"Popescu",
					"15-12-1995",
					"+992556789",
					"Calea Crangasi 69",
					"george@yahoo.com",
					CAPTAIN,
					MALE
			);

			repository.saveAll(
					List.of(
							johnDoe,
							janeDoe,
							marian,
							george,
							claudiu
					)
			);
		};
	}
}
