package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Employee;

public interface DoctorRepository extends JpaRepository<Employee, Long> {

	@Query("select e from Employee e WHERE e.role = 'DOCTOR'")
	Optional<List<Employee>> findAllDoctors();

}
