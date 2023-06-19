package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@ToString
@Entity
@Table(name = "pathology")
public class Pathology {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_number", nullable = false)
//	@MapsId("report_number")
	private Bill reportNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pathologist_id")
	private Employee pathologist;

	@Column(name = "test_name", length = 100, nullable = false)
	private String testName;

	@Column(name = "test_charges")
	private double testCharges;

	@Column(name = "pathologist_remark", length = 400)
	private String pathologistRemark;

	@Column(name = "test_status")
	private boolean testStatus;

	@Column(name = "date_of_arrival_test")
	private LocalDate dateOfArrivalTest;

	@Column(name = "test_slot")
	@Enumerated(EnumType.STRING)
	private Slot testSlot;

}
