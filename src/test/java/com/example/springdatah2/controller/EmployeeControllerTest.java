package com.example.springdatah2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.springdatah2.dto.EmployeeDTO;
import com.example.springdatah2.entity.Employee;
import com.example.springdatah2.service.EmployeeService;
import com.example.springdatah2.util.EmployeeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
	@MockBean
	private EmployeeService employeeService;

	@MockBean
	private EmployeeConverter employeeConverter;

	@InjectMocks
	private EmployeeController employeeController;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	final void testAddEmployee() throws Exception {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").build();
		EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").build();
		Employee response = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		EmployeeDTO responseDTO = EmployeeDTO.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();

		Mockito.when(employeeConverter.convertDtoToEntity(employeeDTO)).thenReturn(employee);
		Mockito.when(employeeService.addEmployee(employee)).thenReturn(response);
		Mockito.when(employeeConverter.convertEntityToDto(response)).thenReturn(responseDTO);

		mockMvc.perform(post("/employee").content(objectMapper.writeValueAsString(employeeDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.aMapWithSize(4)))
				.andExpect(jsonPath("$.firstName", Matchers.is("Prabhakaran")))
				.andExpect(jsonPath("$.lastName", Matchers.is("Ravichandran")))
				.andExpect(jsonPath("$.emailId", Matchers.is("pbkn@gmail.com")))
				.andExpect(jsonPath("$.id", Matchers.is(1)));
	}

	@Test
	final void testGetAllEmployee() throws Exception {
		Employee emp1 = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran").emailId("pbkn@gmail.com")
				.id((long) 1).build();
		Employee emp2 = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran").emailId("pbkn@gmail.com")
				.id((long) 2).build();
		List<Employee> response = Arrays.asList(emp1, emp2);
		EmployeeDTO empDTO1 = EmployeeDTO.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		EmployeeDTO empDTO2 = EmployeeDTO.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 2).build();
		List<EmployeeDTO> responseDTO = Arrays.asList(empDTO1, empDTO2);

		Mockito.when(employeeService.getAllEmployee()).thenReturn(response);
		Mockito.when(employeeConverter.convertEntityListToDtoList(response)).thenReturn(responseDTO);

		mockMvc.perform(get("/employee")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.hasSize(2))).andExpect(jsonPath("$[0].id", Matchers.is(1)))
				.andExpect(jsonPath("$[1].id", Matchers.is(2)));
	}

	@Test
	final void testGetEmployee() throws Exception {
		Employee response = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		EmployeeDTO responseDTO = EmployeeDTO.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();

		Mockito.when(employeeService.getEmployee((long) 1)).thenReturn(response);
		Mockito.when(employeeConverter.convertEntityToDto(response)).thenReturn(responseDTO);

		mockMvc.perform(get("/employee/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.aMapWithSize(4)))
				.andExpect(jsonPath("$.firstName", Matchers.is("Prabhakaran")))
				.andExpect(jsonPath("$.lastName", Matchers.is("Ravichandran")))
				.andExpect(jsonPath("$.emailId", Matchers.is("pbkn@gmail.com")))
				.andExpect(jsonPath("$.id", Matchers.is(1)));
	}

	@Test
	final void testGetEmployeeByFirstName() throws Exception {
		Employee emp1 = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran").emailId("pbkn@gmail.com")
				.id((long) 1).build();
		Employee emp2 = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran2").emailId("pbkn2@gmail.com")
				.id((long) 2).build();
		List<Employee> response = Arrays.asList(emp1, emp2);
		EmployeeDTO empDTO1 = EmployeeDTO.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		EmployeeDTO empDTO2 = EmployeeDTO.builder().firstName("Prabhakaran").lastName("Ravichandran2")
				.emailId("pbkn2@gmail.com").id((long) 2).build();
		List<EmployeeDTO> responseDTO = Arrays.asList(empDTO1, empDTO2);

		Mockito.when(employeeService.getEmployeeByFirstName("Prabhakaran")).thenReturn(response);
		Mockito.when(employeeConverter.convertEntityListToDtoList(response)).thenReturn(responseDTO);

		mockMvc.perform(get("/employee/name/Prabhakaran")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.hasSize(2))).andExpect(jsonPath("$[0].id", Matchers.is(1)))
				.andExpect(jsonPath("$[1].id", Matchers.is(2)));
	}

	@Test
	final void testUpdateEmployee() throws Exception {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		Optional<Employee> response = Optional.ofNullable(Employee.builder().firstName("Prabhakaran#")
				.lastName("Ravichandran#").emailId("pbkn@gmail.com#").id((long) 1).build());
		Optional<EmployeeDTO> responseDTO = Optional.ofNullable(EmployeeDTO.builder().firstName("Prabhakaran#")
				.lastName("Ravichandran#").emailId("pbkn@gmail.com#").id((long) 1).build());

		Mockito.when(employeeConverter.convertDtoToEntity(employeeDTO)).thenReturn(employee);
		Mockito.when(employeeService.updateEmployeeById(employee, (long) 1)).thenReturn(response);
		Mockito.when(employeeConverter.convertEntityToDto(response)).thenReturn(responseDTO);

		mockMvc.perform(put("/employee/1").content(objectMapper.writeValueAsString(employeeDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.aMapWithSize(4)))
				.andExpect(jsonPath("$.firstName", Matchers.is("Prabhakaran#")))
				.andExpect(jsonPath("$.lastName", Matchers.is("Ravichandran#")))
				.andExpect(jsonPath("$.emailId", Matchers.is("pbkn@gmail.com#")))
				.andExpect(jsonPath("$.id", Matchers.is(1)));
	}

	@Test
	final void testDeleteEmployeeById() throws Exception {
		Employee employee = Employee.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();
		EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("Prabhakaran").lastName("Ravichandran")
				.emailId("pbkn@gmail.com").id((long) 1).build();

		Mockito.when(employeeService.deleteEmployeeById((long) 1)).thenReturn(employee);
		Mockito.when(employeeConverter.convertEntityToDto(employee)).thenReturn(employeeDTO);

		mockMvc.perform(delete("/employee/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.aMapWithSize(4)))
				.andExpect(jsonPath("$.firstName", Matchers.is("Prabhakaran")))
				.andExpect(jsonPath("$.lastName", Matchers.is("Ravichandran")))
				.andExpect(jsonPath("$.emailId", Matchers.is("pbkn@gmail.com")))
				.andExpect(jsonPath("$.id", Matchers.is(1)));
	}

}
