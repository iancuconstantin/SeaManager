package com.codecool.seamanager.config;

import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.user.User;
import com.codecool.seamanager.model.port.Port;
import com.codecool.seamanager.model.portinteraction.PortInteraction;
import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.model.voyage.Voyage;
import com.codecool.seamanager.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static com.codecool.seamanager.model.certificate.CertificateType.*;
import static com.codecool.seamanager.model.employee.Gender.FEMALE;
import static com.codecool.seamanager.model.employee.Gender.MALE;
import static com.codecool.seamanager.model.employee.Rank.*;
import static com.codecool.seamanager.model.portinteraction.PortInteractionType.LOAD;
import static com.codecool.seamanager.model.vessel.VesselType.TANKER;

@Configuration
public class SeamanagerConfiguration {

	@Bean
	CommandLineRunner commandLineRunner(
			SailorRepository sailorRepository, CertificateRepository certificateRepository,
			VesselRepository vesselRepository, UserRepository userRepository,
			VoyageRepository voyageRepository, PortRepository portRepository,
			PortInteractionRepository portInteractionRepository
	) {
		return args -> {
			Sailor johnDoe = new Sailor(
					"John",
					"Doe",
					LocalDate.of(1990, 11, 1),
					"1234567890",
					"123 Main St",
					"johndoe@gmail.com",
					THIRD_ENGINEER,
					MALE
			);

			Certificate certificate1 = new Certificate(
					johnDoe,
					SEAMANS_BOOK,
					"123TT45",
					LocalDate.of(2022, 1, 1),
					LocalDate.of(2025, 1, 1)
			);


			Certificate certificate2 = new Certificate(
					johnDoe,
					PASSPORT,
					"1234567890",
					LocalDate.of(2020, 1, 1),
					LocalDate.of(2030, 1, 1)
			);

			Certificate certificate3 = new Certificate(
					johnDoe,
					US_VISA,
					"001122334455",
					LocalDate.of(2021, 5, 5),
					LocalDate.of(2026, 5, 5)
			);

			Certificate certificate4 = new Certificate(
					johnDoe,
					BT,
					"004412312",
					LocalDate.of(2019, 2, 10),
					LocalDate.of(2023, 1, 1)
			);

			Certificate certificate5 = new Certificate(
					johnDoe,
					COC,
					"12345",
					LocalDate.of(2022, 1, 1),
					LocalDate.of(2025, 1, 1)
			);

			Sailor janeDoe = new Sailor(
					"Jane",
					"Doe",
					LocalDate.of(1990, 11, 1),
					"1223556789",
					"123 Main St",
					"janedoe@yahoo.com",
					DECK_CADET,
					FEMALE
			);

			Certificate certificate6 = new Certificate(
					janeDoe,
					SEAMANS_BOOK,
					"312XT25",
					LocalDate.of(2022, 1, 1),
					LocalDate.of(2025, 1, 1)
			);


			Certificate certificate7 = new Certificate(
					janeDoe,
					PASSPORT,
					"444666890",
					LocalDate.of(2020, 1, 1),
					LocalDate.of(2030, 1, 1)
			);

			Certificate certificate8 = new Certificate(
					janeDoe,
					US_VISA,
					"0101155398456",
					LocalDate.of(2022, 4, 5),
					LocalDate.of(2028, 4, 5)
			);

			Certificate certificate9 = new Certificate(
					janeDoe,
					BT,
					"0124412999",
					LocalDate.of(2018, 2, 10),
					LocalDate.of(2028, 1, 1)
			);

			Certificate certificate10 = new Certificate(
					janeDoe,
					COC,
					"33216658",
					LocalDate.of(2022, 9, 19),
					LocalDate.of(2028, 9, 19)
			);

			Sailor marian = new Sailor(
					"Marian",
					"Ionescu",
					LocalDate.of(1990, 11, 1),
					"4072355678",
					"Str. Virtutii 15D",
					"marian_i@yahoo.com",
					CHIEF_ENGINEER,
					MALE
			);

			Certificate certificate11 = new Certificate(
					marian,
					SEAMANS_BOOK,
					"3A217DD",
					LocalDate.of(2022, 1, 11),
					LocalDate.of(2025, 1, 11)
			);


			Certificate certificate12 = new Certificate(
					marian,
					PASSPORT,
					"9738211A22",
					LocalDate.of(2020, 3, 21),
					LocalDate.of(2030, 3, 21)
			);

			Certificate certificate13 = new Certificate(
					marian,
					US_VISA,
					"1A2321399458",
					LocalDate.of(2022, 1, 11),
					LocalDate.of(2025, 1, 11)
			);

			Certificate certificate14 = new Certificate(
					marian,
					BT,
					"A014412999",
					LocalDate.of(2022, 1, 11),
					LocalDate.of(2025, 1, 11)
			);

			Certificate certificate15 = new Certificate(
					marian,
					COC,
					"657A123",
					LocalDate.of(2022, 9, 19),
					LocalDate.of(2028, 9, 19)
			);

			Sailor claudiu = new Sailor(
					"Claudiu Mihai",
					"Tudor",
					LocalDate.of(1990, 11, 1),
					"1234567890",
					"Semilunei 4-6",
					"claudium.tudor@gmail.com",
					SECOND_OFFICER,
					MALE
			);

			Certificate certificate16 = new Certificate(
					claudiu,
					SEAMANS_BOOK,
					"30681CA",
					LocalDate.of(2022, 9, 19),
					LocalDate.of(2028, 9, 19)
			);


			Certificate certificate17 = new Certificate(
					claudiu,
					PASSPORT,
					"571231212",
					LocalDate.of(2020, 9, 19),
					LocalDate.of(2025, 9, 19)
			);

			Certificate certificate18 = new Certificate(
					claudiu,
					US_VISA,
					"BB232139125463",
					LocalDate.of(2020, 9, 19),
					LocalDate.of(2025, 9, 19)
			);

			Certificate certificate19 = new Certificate(
					claudiu,
					BT,
					"01B5512000",
					LocalDate.of(2020, 9, 19),
					LocalDate.of(2025, 9, 19)
			);

			Certificate certificate20 = new Certificate(
					claudiu,
					HV,
					"33333",
					LocalDate.of(2020, 9, 19),
					null
			);

			Sailor george = new Sailor(
					"George",
					"Popescu",
					LocalDate.of(1990, 11, 1),
					"1234567890",
					"Calea Crangasi 69",
					"george@yahoo.com",
					COOK,
					MALE
			);

			Certificate certificate21 = new Certificate(
					george,
					ARPA,
					"49681DD",
					LocalDate.of(2020, 9, 19),
					LocalDate.of(2024, 9, 19)
			);


			Certificate certificate22 = new Certificate(
					george,
					BTRM,
					"A571231212",
					LocalDate.of(2020, 9, 19),
					LocalDate.of(2024, 9, 19)
			);

			Certificate certificate23 = new Certificate(
					george,
					US_VISA,
					"0C012333111",
					LocalDate.of(2020, 9, 19),
					LocalDate.of(2024, 9, 19)
			);

			Certificate certificate24 = new Certificate(
					george,
					BT,
					"0C15512000",
					LocalDate.of(2020, 9, 19),
					null
			);

			Certificate certificate25 = new Certificate(
					george,
					COC,
					"99999C",
					LocalDate.of(2020, 9, 19),
					LocalDate.of(2024, 9, 19)
			);

			User user1 = new User("john123", "password123", 1);
			User user2 = new User("jane456", "myp@ssword", 2);
			User user3 = new User("user789", "secure123", 3);
			User user4 = new User("admin123", "adminpass", 3);
			User user5 = new User("user321", "password123", 2);
			User user6 = new User("jdoe", "pass123", 1);
			User user7 = new User("jsmith", "mypassword", 1);
			User user8 = new User("user456", "securepass", 2);
			User user9 = new User("user789", "myp@ssword", 3);
			User user10 = new User("johndoe", "mypassword123", 2);

			Port port1 = new Port("Houston", true);
			Port port2 = new Port("Antwerp", true);
			Port port3 = new Port("Tuxpan", true);
			Port port4 = new Port("Abidjan", false);
			Port port5 = new Port("Jorf Lasfar", false);

			PortInteraction pi1 = new PortInteraction(LOAD, port1, LocalDate.of(2023, 5, 5));
			PortInteraction pi2 = new PortInteraction(LOAD, port2, LocalDate.of(2023, 6, 6));
			PortInteraction pi3 = new PortInteraction(LOAD, port3, LocalDate.of(2023, 7, 7));
			PortInteraction pi4 = new PortInteraction(LOAD, port4, LocalDate.of(2023, 8, 7));
			PortInteraction pi5 = new PortInteraction(LOAD, port1, LocalDate.of(2023, 8, 7));
			PortInteraction pi6 = new PortInteraction(LOAD, port3, LocalDate.of(2023, 9, 7));

			Vessel vessel1 = new Vessel("Ocean Queen", TANKER, "USA", "1234567", new HashSet<>());
			Vessel vessel2 = new Vessel("SS Pacific Voyager", TANKER, "Canada", "6789000", new HashSet<>());

			Voyage vv1 = new Voyage(vessel1, new HashSet<>(), pi1, pi2);
			Voyage vv2 = new Voyage(vessel2, new HashSet<>(), pi3, pi4);
			Voyage vv3 = new Voyage(vessel2, new HashSet<>(), pi5, pi6);

			portRepository.saveAll(
					List.of(
							port1,
							port2,
							port3,
							port4,
							port5)
			);

			portInteractionRepository.saveAll(
					List.of(
							pi1,
							pi2,
							pi3,
							pi4,
							pi5,
							pi6)
			);

			vesselRepository.saveAll(
					List.of(
							vessel1,
							vessel2)
			);

			sailorRepository.saveAll(
					List.of(
							johnDoe,
							janeDoe,
							marian,
							george,
							claudiu
					)
			);

			voyageRepository.saveAll(
					List.of(
							vv1,
							vv2,
							vv3)
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

			userRepository.saveAll(
					List.of(
							user1,
							user2,
							user3,
							user4,
							user5,
							user6,
							user7,
							user8,
							user9,
							user10
					)
			);

			vesselRepository.saveAll(
					List.of(
							vessel1,
							vessel2
					)
			);
		};
	}
}
