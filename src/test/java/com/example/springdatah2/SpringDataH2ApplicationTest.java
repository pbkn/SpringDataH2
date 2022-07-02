package com.example.springdatah2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springdatah2.controller.EmployeeController;
import com.example.springdatah2.repository.EmployeeRepository;
import com.example.springdatah2.service.EmployeeService;
import com.example.springdatah2.util.EmployeeConverter;

@SpringBootTest
class SpringDataH2ApplicationTest {

	@Autowired
	EmployeeController employeeController;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeConverter employeeConverter;

	@Test
	void contextLoads() {
		Assertions.assertThat(employeeController).isNotNull();
		Assertions.assertThat(employeeRepository).isNotNull();
		Assertions.assertThat(employeeService).isNotNull();
		Assertions.assertThat(employeeConverter).isNotNull();
	}

}
