package com.example.springdatah2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.springdatah2.entity.Employee;
import com.example.springdatah2.exception.ResourceNotFoundException;
import com.example.springdatah2.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final String NO_DATA_FOUND = "No Data found";

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployee() {
		List<Employee> response = employeeRepository.findAll();
		if (CollectionUtils.isEmpty(response))
			throw new ResourceNotFoundException(NO_DATA_FOUND);
		return response;
	}

	@Override
	@Cacheable(value = "employee", key = "#id")
	public Employee getEmployee(Long id) {
		return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NO_DATA_FOUND));
	}

	@Override
	public List<Employee> getEmployeeByFirstName(String firstName) {
		List<Employee> response = employeeRepository.findByFirstName(firstName);
		if (CollectionUtils.isEmpty(response))
			throw new ResourceNotFoundException(NO_DATA_FOUND);
		return response;
	}

	@Override
	public List<Employee> getEmployeeByName(String firstName, String lastName) {
		List<Employee> response = employeeRepository.findAllByName(firstName, lastName);
		if (CollectionUtils.isEmpty(response))
			throw new ResourceNotFoundException(NO_DATA_FOUND);
		return response;
	}

	@Override
	@CachePut(value = "employee", key = "#id")
	public Optional<Employee> updateEmployeeById(Employee employee, Long id) {
		Employee existingData = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NO_DATA_FOUND));
		Employee updatedData = null;
		if (!existingData.equals(employee)) {
			updatedData = employeeRepository.save(employee);
		} else {
			throw new ResourceNotFoundException("Nothing to Modify");
		}
		return Optional.ofNullable(updatedData);
	}

	@Override
	@CacheEvict(value = "employee", key = "#id")
	public Employee deleteEmployeeById(Long id) {
		Employee existingData = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NO_DATA_FOUND));

		employeeRepository.deleteById(id);

		return existingData;
	}

}
