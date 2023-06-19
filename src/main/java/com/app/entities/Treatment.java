package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "reportNumber")
@Entity
@Table(name = "treatment")
public class Treatment {

	@Id
	@Column(name = "treatment_id")
	private Long treatmentId;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "treatment")
	@JoinColumn(name = "report_number", nullable = false)
	private Bill reportNumber; // foreign key of report table for report no.

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "docter_id", nullable = false)
	private Employee employee;

	@Column(length = 100, name = "type_Of_treatment", nullable = false)
	private String typeOfTreatment;

	@Column(length = 400, name = "patient_problem")
	private String patientProblem;

	@Column(length = 400, name = "patient_note")
	private String patientNote;

	@Column(length = 200, name = "treatment_name")
	private String treatmentName;

	@Column(name = "treatment_Charges")
	private Long treatmentCharges;

	@Column(length = 200, name = "doctor_remark")
	private String doctorRemark;

	@Column(length = 200, name = "doctors_note")
	private String doctorsNote;

	@Column(name = "date_of_appointment", nullable = false)
	private LocalDate dateOfAppointment;

	@Column(length = 20, name = "treatment_slot")
	@Enumerated(EnumType.STRING)
	private Slot treatmentSlot;

	@Column(length = 200, name = "treatment_result")
	private String treatmentResult;

	@JoinColumn(name = "bed_id")
	@OneToOne(fetch = FetchType.LAZY)
	// @MapsId
	private Bed bed;

	@Column(name = "bed_No_Of_Days")
	private int bedAllotedForDays;

	@Column(name = "treatment_status", nullable = false)
	private boolean treatmentStatus;

}
