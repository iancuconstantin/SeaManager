package com.codecool.seamanager.repository;

import com.codecool.seamanager.model.certificate.Certificate;
import com.codecool.seamanager.model.employee.Employee;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CertificateRepositoryTest {
	@Mock
	private CertificateRepository certificateRepository;
	private Certificate certificate;
	private Employee employee;
}
