package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Pathology;

public interface PathologyRepository extends JpaRepository<Pathology, Long> {

	// get all pending treatments by empId
	@Query("SELECT p FROM Pathology p WHERE p.pathologist.empId = ?1 AND p.testStatus = false")
	Optional<List<Pathology>> findByEmployeeAndpathologyResult(Long empId);

	@Query("select p from Pathology p WHERE p.reportNumber.billNumber = ?1")
	Optional<Pathology> findByPatientReportNumber(Long reportNumber);

	@Query("select p from Pathology p WHERE p.reportNumber.billNumber = ?1 ")
	Optional<Pathology> getPendingPathologyByReportNumber(Long reportNumber);
}
