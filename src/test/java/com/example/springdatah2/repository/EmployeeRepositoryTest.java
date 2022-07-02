package com.example.springdatah2.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.springdatah2.entity.Employee;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	final void testFindByFirstName() {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").build();
		List<Employee> dataList = Arrays.asList(employee);

		employeeRepository.save(employee);

		List<Employee> responseList = employeeRepository.findByFirstName("Prabhakaran");
		Assertions.assertIterableEquals(responseList, dataList);
	}

	@Test
	final void testFindAllByName() {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").build();
		List<Employee> dataList = Arrays.asList(employee);

		employeeRepository.save(employee);

		List<Employee> responseList = employeeRepository.findAllByName("Prabhakaran", "Ravichandran");
		Assertions.assertIterableEquals(responseList, dataList);

	}

}
