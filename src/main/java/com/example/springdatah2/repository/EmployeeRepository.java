package com.example.springdatah2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.springdatah2.entity.Employee;

@Repository
@Transactional(isolation = Isolation.REPEATABLE_READ)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByFirstName(String firstName);

	@Query(value = "SELECT * FROM employee e WHERE e.first_name = ?1 AND e.last_name = ?2", nativeQuery = true)
	List<Employee> findAllByName(String firstName, String lastName);
}
