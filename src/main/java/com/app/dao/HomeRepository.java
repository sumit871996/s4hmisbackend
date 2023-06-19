package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Employee;
import com.app.entities.WebsiteStaticData;

public interface HomeRepository extends JpaRepository<WebsiteStaticData, Long> {

	Optional<WebsiteStaticData> findByPolicyEntry(Long policyEntry);

	@Query("select e from Employee e where e.firstName like '%:string%' OR e.lastName like '%:sring%'")
	List<Employee> searchDoctors(String string);

}
