package com.app.dto;

import java.time.LocalDate;

import com.app.entities.Slot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestBasicResponseDto {

	private TreatmentPatientDetailDto patient_details;

	private Long reportNumber;

	private Long empId;

	private String testName;

	private LocalDate dateOfArrivalTest;

	private Slot testSlot;

}
