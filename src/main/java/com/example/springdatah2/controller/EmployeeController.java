package com.example.springdatah2.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springdatah2.dto.EmployeeDTO;
import com.example.springdatah2.entity.Employee;
import com.example.springdatah2.service.EmployeeService;
import com.example.springdatah2.util.EmployeeConverter;

import io.micrometer.core.instrument.util.StringUtils;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeConverter employeeConverter;

	@PostMapping(value = "/employee")
	public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		Employee employee = employeeConverter.convertDtoToEntity(employeeDTO);
		EmployeeDTO responseData = employeeConverter.convertEntityToDto(employeeService.addEmployee(employee));
		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}

	@GetMapping(value = "/employee")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployee(
			@RequestParam(required = false) MultiValueMap<String, String> params) {
		String firstName = params.getFirst("firstName");
		String lastName = params.getFirst("lastName");
		List<EmployeeDTO> responseData;
		if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)) {
			responseData = employeeConverter
					.convertEntityListToDtoList(employeeService.getEmployeeByName(firstName, lastName));
		} else {
			responseData = employeeConverter.convertEntityListToDtoList(employeeService.getAllEmployee());
		}
		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}

	@GetMapping(value = "/employee/{id}")
	public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("id") Long id) {
		EmployeeDTO responseData = employeeConverter.convertEntityToDto(employeeService.getEmployee(id));
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@GetMapping(value = "/employee/name/{firstName}")
	public ResponseEntity<List<EmployeeDTO>> getEmployeeByFirstName(@PathVariable("firstName") String firstName) {
		List<EmployeeDTO> responseData = employeeConverter
				.convertEntityListToDtoList(employeeService.getEmployeeByFirstName(firstName));
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PutMapping(value = "/employee/{id}")
	public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") Long id,
			@Valid @RequestBody EmployeeDTO employeeDTO) {
		Employee employee = employeeConverter.convertDtoToEntity(employeeDTO);
		Optional<EmployeeDTO> responseData = employeeConverter
				.convertEntityToDto(employeeService.updateEmployeeById(employee, id));
		if (responseData.isPresent()) {
			return new ResponseEntity<>(responseData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping(value = "/employee/{id}")
	public ResponseEntity<EmployeeDTO> deleteEmployeeById(@PathVariable("id") Long id) {
		EmployeeDTO responseData = employeeConverter.convertEntityToDto(employeeService.deleteEmployeeById(id));
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
