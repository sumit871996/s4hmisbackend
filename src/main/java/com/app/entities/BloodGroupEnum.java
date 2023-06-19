package com.app.entities;

public enum BloodGroupEnum {

	ABPOSITIVE("AB+"), // this sets a property to respective enum
	APOSITIVE("A+"), BPOSITIVE("B+"), OPOSITIVE("O+"),

	ABNEGATIVE("AB-"), ANEGATIVE("A-"), BNEGATIVE("B-"), ONEGATIVE("O-");

	private String value;

	private BloodGroupEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return this.value; // will return , or ' instead of COMMA or APOSTROPHE
	}

}
