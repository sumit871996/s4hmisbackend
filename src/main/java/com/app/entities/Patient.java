package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "patient")
public class Patient extends PersonBaseEntity {

	@Id
	@TableGenerator(initialValue = 1000, name = "patGen", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "patGen")
	@Column(name = "patient_id")
	private Long patientId;
	
	public Long getPatientId() {
		return patientId;
	}

	@Column(name = "security_question", length = 100, nullable = true)
	private String securityQuestion;

	@Column(name = "security_answer", length = 15, nullable = true)
	private String securityAnswer;

	@Column(length = 10)
	private int feeedback;

//	@Transient
//	private static final Role role=Role.PATIENT;
	@Override
	public Role getUserRole() {

		return Role.ROLE_PATIENT;
	}

}
