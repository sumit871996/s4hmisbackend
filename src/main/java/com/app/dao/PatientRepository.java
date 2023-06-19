package com.app.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Bill;
import com.app.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
//	Optional<Patient> findByPatientId(Long patientid);

	@Query("select p from Patient p where p.phone=?1 and p.securityAnswer=?2 ")
	Optional<Patient> findByFirstNameAndPhoneNoAndDOB(String phoneNo,
			String securityAnswer);

	@Query("select b from Bill b where b.patient.patientId=?1")
	List<Bill> findBillbyPatientId(Long patientId);

	@Query("select p from Patient p where p.phone=?1 and p.password=?2")
	Optional<Patient> findByPhoneAndPassword(String phoneNo, String password);

	@Query("select p from Patient p where p.firstName=?1 and p.lastName=?2 and p.phone=?3")
	Optional<Patient> findByFNameAndLNameAndPhoneNo(String firstName, String LastName, String PhoneNo);

	// @Query("select p from Patient p where phone = ?1")
	Optional<Patient> findByPhone(String phone);

}
