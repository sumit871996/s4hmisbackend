package com.app.entities;
//patientId	reportNumber	Date of Admission (1 st appointment date)

//dateOfDischarge	totalPaidAmount	paidStatus

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "bill")
@Data
@ToString(exclude = "treatment")
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_number")
	private Long billNumber;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Treatment treatment;

	@Column(nullable = false, name = "date_of_admission")
	private LocalDate dateOfAdmission;

	@Column(nullable = true, name = "date_of_discharge")
	private LocalDate dateOfDischarge;

	@Column(nullable = true, name = "total_paid_amount")
	private double totalPaidAmount;

	@Column(nullable = false, name = "paid_status")
	private boolean paidStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	@Column
	private LocalDateTime timestamp = LocalDateTime.now();

}