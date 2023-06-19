package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Treatment;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

	// get all pending treatments by empId
	@Query("SELECT u FROM Treatment u WHERE u.employee.empId = ?1 AND u.treatmentStatus = false ORDER BY u.dateOfAppointment , u.treatmentSlot")
	Optional<List<Treatment>> findByEmployeeAndPendingTreatments(Long empId);

//	5. find patientId in bed allocation table & return bed number, ward number, bed charges per day
//	and fetch bedAllocatedforDays for that empId for the ongoing report

	@Query("select t from Treatment t WHERE t.reportNumber.billNumber = ?1")
	Optional<Treatment> findByPatientReportNumber(Long reportNumber);

	@Query("select t from Treatment t where t.reportNumber.billNumber = ?1 and t.treatmentStatus=false")
	Optional<Treatment> findListByReportNumber(Long reportNumber, boolean treatmentStatus);

	@Query("select t from Treatment t where t.reportNumber.billNumber = ?1")
	Optional<Treatment> findDoctorByReportNumber(Long reportnumber);

}
