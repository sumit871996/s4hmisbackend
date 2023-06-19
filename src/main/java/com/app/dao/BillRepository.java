package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Bill;
import com.app.entities.Patient;

public interface BillRepository extends JpaRepository<Bill, Long> {

	// find Bill details by bill/reportNumber
	Optional<Bill> findByBillNumber(Long billNumber);

	@Query("select b.patient from Bill b WHERE b.billNumber = ?1")
	Optional<Patient> getPatientByreportNumber(Long reportNumber);

	@Query("select b from Bill b where b.patient.patientId = ?1")
	Optional<List<Bill>> getAllBillFromPatientId(Long patientId);

	Optional<List<Bill>> findByPaidStatusFalse();

	Optional<List<Bill>> findByPaidStatusTrue();

	@Query("select b from Bill b where b.patient.patientId=?1 and b.paidStatus = ?2")
	Optional<List<Bill>> findbyBillForPatientbyStatus(Long patientId, boolean status);

}
