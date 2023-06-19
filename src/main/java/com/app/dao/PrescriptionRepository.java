package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

	// 8. Get medicines list by Report number
	@Query("select p from Prescription p where p.reportNumber.billNumber = ?1")
	Optional<List<Prescription>> getPrescriptionDetailsByReportNumber(Long reportNumber);

}