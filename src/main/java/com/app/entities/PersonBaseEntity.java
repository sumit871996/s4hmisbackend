package com.app.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonBaseEntity {

	@Column(name = "first_name", length = 30, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 30, nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String password;

	@Column(length = 13, nullable = false, unique = true)
	private String phone;

	@Column(length = 30, nullable = true)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "blood_group", length = 30, nullable = true)
	private BloodGroupEnum bloodGroup;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Gender gender;

	@Column(name = "profile_photo", nullable = true)
	private String profilePhoto;

	@Column(name = "date_of_birth", nullable = true)
	private LocalDate dateOfBirth;

	@Column(name = "building_name", length = 30, nullable = true)
	private String buildingName;

	@Column(name = "street_name", length = 30, nullable = true)
	private String streetName;

	@Column(length = 30, nullable = true)
	private String city;

	@Column(length = 30, nullable = true)
	private String state;

	@Column(name = "pin_code", length = 30, nullable = true)
	private String pinCode;

	@Column(nullable = false)
	private LocalDateTime timestamp = LocalDateTime.now();

	public Role getUserRole() {
		return null;
	}

}
