package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Employee;
import com.app.entities.Role;
import com.app.entities.SpecialityEnum;

public interface EmployeeRespository extends JpaRepository<Employee, Long> {

	@Query("select e from Employee e where phone = ?1 And password = ?2")
	Optional<Employee> findByPhoneAndPassword(String phone, String password);

	@Query("select distinct e.speciality from Employee e WHERE e.speciality != 'PATHOLOGIST' OR e.speciality != 'ADMINISTRATION' OR e.speciality != 'RECEPTIONIST'")
	Optional<List<SpecialityEnum>> findAllDoctorSpecialities();

	@Query("select e from Employee e where e.role = 'ROLE_DOCTOR' ORDER BY e.firstName, e.lastName")
	Optional<List<Employee>> findByMyRole(Role role);

	Optional<Employee> findByPhone(String phone);

	@Query("select e from Employee e where e.role != 'ROLE_ADMIN'")
	List<Employee> getAllEmployees();

	@Query("select e from Employee e where role = ?1 and speciality = ?2")
	Optional<List<Employee>> findByMyRoleAndSpeciality(Role role, SpecialityEnum speciality);
}
