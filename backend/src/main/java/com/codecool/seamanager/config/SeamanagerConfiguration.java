package com.codecool.seamanager.config;

import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.certificate.CertificateType;
import com.codecool.seamanager.model.employee.Gender;
import com.codecool.seamanager.model.employee.Rank;
import com.codecool.seamanager.model.employee.Sailor;
import com.codecool.seamanager.model.port.Port;
import com.codecool.seamanager.model.portinteraction.PortInteraction;
import com.codecool.seamanager.model.vessel.Vessel;
import com.codecool.seamanager.model.voyage.Voyage;
import com.codecool.seamanager.repository.*;
import com.codecool.seamanager.utils.DateGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static com.codecool.seamanager.model.portinteraction.PortInteractionType.LOAD;
import static com.codecool.seamanager.model.vessel.VesselType.BULK;
import static com.codecool.seamanager.model.vessel.VesselType.TANKER;

@Configuration
public class SeamanagerConfiguration {
	private static int serialNumber = 100;
	@Bean
	CommandLineRunner commandLineRunner(
			SailorRepository sailorRepository, CertificateRepository certificateRepository,
			VesselRepository vesselRepository, UserRepository userRepository,
			VoyageRepository voyageRepository, PortRepository portRepository,
			PortInteractionRepository portInteractionRepository, ContractRepository contractRepository
	) {
		return args -> {
			Random random = new Random();
			for (int i = 0; i < 200; i++) {
				Sailor sailor = new Sailor(
						"FirstName" + i,
						"Lastname" + i,
						DateGenerator.generateRandomBirthDate(),
						"1234567890",
						"Address" + i,
						"email" + i + "@yahoo.com",
						Rank.values()[random.nextInt(Rank.values().length)],
						Gender.values()[random.nextInt(Gender.values().length)],
						DateGenerator.generateRandomReadinessDate()
						//TODO - readiness date field?
				);
				Certificate certificate = new Certificate();
				for (int j = 0; j < 10; j++){
					certificate = new Certificate(
							sailor,
							CertificateType.values()[j],
							String.valueOf(serialNumber++),
							DateGenerator.generateRandomDatePast3Years(),
							DateGenerator.generateRandomDateFuture3Years()
					);

				}

				sailorRepository.save(sailor);
				certificateRepository.save(certificate);

			}
//

//			User user1 = new User("john123", "pasAsword123", "John", "Doe", "john@yahoo.com", 1);
//			User user2 = new User("jane456", "myAp@ssword", "Jane", "Doe", "jane@yahoo.com", 2);
//			User user3 = new User("user789", "seAcure123", "Nicu", "Gheara", "nicu@gmail.co.uk", 3);
//			User user4 = new User("admin123", "admAinpass", "Fane", "Spoitoru", "fane_spoitoru@gmail.com", 3);
//			User user5 = new User("user321", "paAssword123", "Sile", "Camataru", "sile@yahoo.ro", 2);
//			User user6 = new User("jdoe", "pasAs1234", "Nutu", "Camataru", "nutu@gmail.com", 1);
//			User user7 = new User("jsmith", "mypAassword", "Adi", "Corduneanu", "adi@yahoo.com", 1);
//			User user8 = new User("user456", "secAurepass", "George", "Corduneanu", "george@yahoo.com", 2);
//			User user9 = new User("user789", "myp@ssAword", "Sandu", "Geamanu", "sandu.g@yahoo.com", 3);
//			User user10 = new User("johndoe", "mypasswAord123", "Sorin", "Caiac", "caiac@hotmail.com", 2);

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

			Vessel vessel1 = new Vessel("Ocean Queen", TANKER, "Denmark", "222333444", "vessel1@company.com", "A5BB3", "1234567", "2020", "23456", "25412");
			Vessel vessel2 = new Vessel("SS Pacific Voyager", BULK, "Liberia", "123456789", "vessel2@company.com", "D7WF1", "9903133", "2015", "24856", "28992");

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


			voyageRepository.saveAll(
					List.of(
							vv1,
							vv2,
							vv3)
			);


//			userRepository.saveAll(
//					List.of(
//							user1,
//							user2,
//							user3,
//							user4,
//							user5,
//							user6,
//							user7,
//							user8,
//							user9,
//							user10
//					)
//			); //TODO - fix controller/service

			vesselRepository.saveAll(
					List.of(
							vessel1,
							vessel2
					)
			);
		};
	}
}
