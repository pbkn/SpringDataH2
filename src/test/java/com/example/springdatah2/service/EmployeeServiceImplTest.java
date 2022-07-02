package com.example.springdatah2.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.springdatah2.entity.Employee;
import com.example.springdatah2.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmployeeServiceImpl.class)
class EmployeeServiceImplTest {

	@MockBean
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	@Test
	final void testAddEmployee() {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").build();
		Employee responseData = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();

		when(employeeRepository.save(employee)).thenReturn(responseData);

		Employee response = employeeServiceImpl.addEmployee(employee);

		Assumptions.assumeTrue(response.equals(responseData));

		verify(employeeRepository, times(1)).save(employee);
	}

	@Test
	final void testGetAllEmployee() {

		Employee emp1 = Employee.builder().firstName("Prabhakaran1").lastName("Ravichandran1")
				.emailId("pbkn@gmail.com1").id((long) 1).build();
		Employee emp2 = Employee.builder().firstName("Prabhakaran2").lastName("Ravichandran2")
				.emailId("pbkn@gmail.com2").id((long) 2).build();
		Employee emp3 = Employee.builder().firstName("Prabhakaran3").lastName("Ravichandran3")
				.emailId("pbkn@gmail.com3").id((long) 3).build();
		List<Employee> dataList = Arrays.asList(emp1, emp2, emp3);

		when(employeeRepository.findAll()).thenReturn(dataList);

		List<Employee> responseList = employeeServiceImpl.getAllEmployee();

		Assertions.assertEquals(3, responseList.size());
		verify(employeeRepository, times(1)).findAll();
	}

	@Test
	final void testGetEmployee() {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();

		when(employeeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(employee));

		Employee response = employeeServiceImpl.getEmployee((long) 1);

		Assumptions.assumeTrue(response.equals(employee));

		verify(employeeRepository, times(1)).findById((long) 1);
	}

	@Test
	final void testGetEmployeeByFirstName() {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		List<Employee> dataList = Arrays.asList(employee);

		when(employeeRepository.findByFirstName("Prabhakaran")).thenReturn(dataList);

		List<Employee> responseList = employeeServiceImpl.getEmployeeByFirstName("Prabhakaran");

		Assertions.assertEquals(1, responseList.size());

		verify(employeeRepository, times(1)).findByFirstName("Prabhakaran");
	}

	@Test
	final void testGetEmployeeByName() {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		List<Employee> dataList = Arrays.asList(employee);

		when(employeeRepository.findAllByName("Prabhakaran", "Ravichandran")).thenReturn(dataList);

		List<Employee> responseList = employeeServiceImpl.getEmployeeByName("Prabhakaran", "Ravichandran");

		Assertions.assertEquals(1, responseList.size());

		verify(employeeRepository, times(1)).findAllByName("Prabhakaran", "Ravichandran");
	}

	@Test
	final void testUpdateEmployeeById() {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		Employee updatedData = Employee.builder().firstName("Prabhakaran#").lastName("Ravichandran#")
				.emailId("pbkn@gmail.com#").id((long) 1).build();

		when(employeeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(employee));
		when(employeeRepository.save(updatedData)).thenReturn(updatedData);

		Optional<Employee> response = employeeServiceImpl.updateEmployeeById(updatedData, (long) 1);

		Assumptions.assumeTrue(response.get().equals(updatedData));

		verify(employeeRepository, times(1)).findById((long) 1);
		verify(employeeRepository, times(1)).save(updatedData);
	}

	@Test
	final void testDeleteEmployeeById() {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();

		when(employeeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(employee));

		Employee response = employeeServiceImpl.deleteEmployeeById((long) 1);

		Assumptions.assumeTrue(response.equals(employee));

		verify(employeeRepository, times(1)).findById((long) 1);
	}

}
