package com.example.springdatah2.util;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springdatah2.dto.EmployeeDTO;
import com.example.springdatah2.entity.Employee;

@Component
public class EmployeeConverter {

	@Autowired
	ModelMapper modelMapper;

	public EmployeeDTO convertEntityToDto(Employee employee) {
		return modelMapper.map(employee, EmployeeDTO.class);
	}

	public Employee convertDtoToEntity(EmployeeDTO employeeDTO) {
		return modelMapper.map(employeeDTO, Employee.class);
	}

	public Optional<EmployeeDTO> convertEntityToDto(Optional<Employee> employee) {
		return Optional.ofNullable(modelMapper.map(employee, EmployeeDTO.class));
	}

	public List<EmployeeDTO> convertEntityListToDtoList(List<Employee> employeeList) {
		return modelMapper.map(employeeList, new TypeToken<List<EmployeeDTO>>() {
		}.getType());
	}

}
