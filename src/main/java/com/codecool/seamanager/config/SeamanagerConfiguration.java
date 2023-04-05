package com.codecool.seamanager.config;

import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.employee.Employee;
import com.codecool.seamanager.repository.CertificateRepository;
import com.codecool.seamanager.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.*;

@Configuration
public class SeamanagerConfiguration {

	@Bean
	CommandLineRunner commandLineRunner(
			EmployeeRepository employeeRepository, CertificateRepository certificateRepository
	) {
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

			Certificate certificate1 = new Certificate(
					johnDoe,
					"Seaman's Book",
					"123TT45",
					"01-01-2022",
					"01-01-2025"
			);


			Certificate certificate2 = new Certificate(
					johnDoe,
					"Passport",
					"5555666890",
					"01-01-2020",
					"01-01-2030"
			);

			Certificate certificate3 = new Certificate(
					johnDoe,
					"US Visa",
					"001122334455",
					"05-05-2021",
					"05-05-2026"
			);

			Certificate certificate4 = new Certificate(
					johnDoe,
					"IMO Basic Course",
					"004412312",
					"10-02-2019",
					"01-01-2023"
			);

			Certificate certificate5 = new Certificate(
					johnDoe,
					"CoC",
					"12345",
					"01-01-2022",
					"01-01-2025"
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

			Certificate certificate6 = new Certificate(
					janeDoe,
					"Seaman's Book",
					"312XT25",
					"01-01-2022",
					"01-01-2025"
			);


			Certificate certificate7 = new Certificate(
					janeDoe,
					"Passport",
					"444666890",
					"01-02-2020",
					"01-02-2030"
			);

			Certificate certificate8 = new Certificate(
					janeDoe,
					"US Visa",
					"001155398456",
					"04-05-2022",
					"04-05-2028"
			);

			Certificate certificate9 = new Certificate(
					janeDoe,
					"IMO Basic Course",
					"014412999",
					"10-02-2018",
					"01-01-2028"
			);

			Certificate certificate10 = new Certificate(
					janeDoe,
					"CoC",
					"3216658",
					"19-09-2023",
					"19-09-2028"
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

			Certificate certificate11 = new Certificate(
					marian,
					"Seaman's Book",
					"3217DD",
					"11-01-2022",
					"11-01-2025"
			);


			Certificate certificate12 = new Certificate(
					marian,
					"Passport",
					"973821122",
					"21-02-2020",
					"21-02-2030"
			);

			Certificate certificate13 = new Certificate(
					marian,
					"US Visa",
					"0012321399458",
					"12-05-2022",
					"12-05-2028"
			);

			Certificate certificate14 = new Certificate(
					marian,
					"IMO Basic Course",
					"014412999",
					"10-02-2018",
					"01-01-2028"
			);

			Certificate certificate15 = new Certificate(
					marian,
					"CoC",
					"657123",
					"28-11-2023",
					"28-11-2028"
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

			Certificate certificate16 = new Certificate(
					claudiu,
					"Seaman's Book",
					"30681CA",
					"11-01-2022",
					"11-01-2025"
			);


			Certificate certificate17 = new Certificate(
					claudiu,
					"Passport",
					"571231212",
					"14-02-2018",
					"14-02-2028"
			);

			Certificate certificate18 = new Certificate(
					claudiu,
					"US Visa",
					"001232139125463",
					"12-05-2021",
					"12-05-2029"
			);

			Certificate certificate19 = new Certificate(
					claudiu,
					"IMO Basic Course",
					"015512000",
					"10-05-2015",
					"10-05-2020"
			);

			Certificate certificate20 = new Certificate(
					claudiu,
					"CoC",
					"33333",
					"25-12-2021",
					null
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

			Certificate certificate21 = new Certificate(
					george,
					"Seaman's Book",
					"49681DD",
					"11-01-2023",
					"11-01-2029"
			);


			Certificate certificate22 = new Certificate(
					george,
					"Passport",
					"571231212",
					"12-12-2018",
					"12-12-2023"
			);

			Certificate certificate23 = new Certificate(
					george,
					"US Visa",
					"0012333111",
					"12-08-2017",
					"12-08-2027"
			);

			Certificate certificate24 = new Certificate(
					george,
					"IMO Basic Course",
					"015512000",
					"10-05-2015",
					null
			);

			Certificate certificate25 = new Certificate(
					george,
					"CoC",
					"99999",
					"25-12-2015",
					"26-12-2025"
			);


			employeeRepository.saveAll(
					List.of(
							johnDoe,
							janeDoe,
							marian,
							george,
							claudiu
					)
			);

			certificateRepository.saveAll(
					List.of(
							certificate1,
							certificate2,
							certificate3,
							certificate4,
							certificate5,
							certificate6,
							certificate7,
							certificate8,
							certificate9,
							certificate10,
							certificate11,
							certificate12,
							certificate13,
							certificate14,
							certificate15,
							certificate16,
							certificate17,
							certificate18,
							certificate19,
							certificate20,
							certificate21,
							certificate22,
							certificate23,
							certificate24,
							certificate25
					)
			);
		};
	}
}
