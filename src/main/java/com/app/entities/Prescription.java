package com.app.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//AppointmentNumber	MedicineNumber	Medicine Name	Quantity	Unit Cost	Dosage(per day)	Duration

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "reportNumber")
@Entity
@Table(name = "prescription")
public class Prescription {

	@Id
	@Column(name = "medicines_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long medicineNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_number", nullable = false)
//	@MapsId("report_number")
	private Bill reportNumber;

	@Column(length = 100, nullable = false)
	private String medicineName;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private double unitCost;

	@Column(length = 40, nullable = false)
	private String dosagePerDay;

	@Column(nullable = false)
	private int duration;

	@Column
	private LocalDateTime timestamp = LocalDateTime.now();
}
