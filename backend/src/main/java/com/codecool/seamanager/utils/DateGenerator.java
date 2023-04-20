package com.codecool.seamanager.utils;

import java.time.LocalDate;
import java.util.Random;

public class DateGenerator {
	public static LocalDate generateRandomBirthDate() {
		Random random = new Random();
		LocalDate currentDate = LocalDate.now();
		LocalDate minBirthDate = currentDate.minusYears(60);
		LocalDate maxBirthDate = currentDate.minusYears(18);
		int minDays = (int) minBirthDate.toEpochDay();
		int maxDays = (int) maxBirthDate.toEpochDay();
		int randomDays = random.nextInt(maxDays - minDays) + minDays;
		LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDays);
		return randomBirthDate;
	}

	public static LocalDate generateRandomReadinessDate() {
		Random random = new Random();
		LocalDate currentDate = LocalDate.now();
		LocalDate minDate = currentDate.plusDays(1);
		LocalDate maxDate = currentDate.plusMonths(6);
		long minDays = minDate.toEpochDay();
		long maxDays = maxDate.toEpochDay();
		long randomDays = minDays + random.nextInt((int) (maxDays - minDays));
		LocalDate randomDate = LocalDate.ofEpochDay(randomDays);
		return randomDate;
	}

	public static LocalDate generateRandomDatePast3Years() {
		Random random = new Random();
		LocalDate currentDate = LocalDate.now();
		LocalDate minDate = currentDate.minusYears(3);
		LocalDate maxDate = currentDate;
		long minDays = minDate.toEpochDay();
		long maxDays = maxDate.toEpochDay();
		long randomDays = minDays + random.nextInt((int) (maxDays - minDays));
		LocalDate randomDate = LocalDate.ofEpochDay(randomDays);
		return randomDate;
	}

	public static LocalDate generateRandomDateFuture3Years() {
		Random random = new Random();
		LocalDate currentDate = LocalDate.now();
		LocalDate minDate = currentDate.minusMonths(3); // Tomorrow
		LocalDate maxDate = currentDate.plusYears(3);
		long minDays = minDate.toEpochDay();
		long maxDays = maxDate.toEpochDay();
		long randomDays = minDays + random.nextInt((int) (maxDays - minDays));
		LocalDate randomDate = LocalDate.ofEpochDay(randomDays);
		return randomDate;
	}
}
