package com.example.springdatah2.service;

import java.util.List;
import java.util.Optional;

import com.example.springdatah2.entity.Employee;

public interface EmployeeService {

	// Save operation
	Employee addEmployee(Employee employee);

	// Read operation
	List<Employee> getAllEmployee();

	Employee getEmployee(Long id);

	List<Employee> getEmployeeByFirstName(String firstName);

	List<Employee> getEmployeeByName(String firstName, String lastName);

	// Update operation
	Optional<Employee> updateEmployeeById(Employee employee, Long id);

	// Delete operation
	Employee deleteEmployeeById(Long id);
}
