package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Employee extends PersonBaseEntity {

	@Id
	@TableGenerator(initialValue = 0, name = "empGen", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "empGen")
	@Column(name = "emp_id")
	@Range(min = 1, max = 1000)
	private Long empId;

	public Long getEmpId() {
		return empId;
	}
	@Column(length = 15, nullable = false)
	private String education;

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private SpecialityEnum speciality;

	@Column(nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private DepartmentEnum department;

	@Column(nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Override
	public Role getUserRole() {

		return this.role;
	}

}
