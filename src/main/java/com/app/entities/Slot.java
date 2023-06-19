package com.app.entities;

public enum Slot {

	FIRST("6 A.M.- 9 A.M."), SECOND("9 A.M. - 12 P.M."), THIRD("12 P.M. - 3 P.M."), FOURTH("3 P.M. - 6 P.M."),
	FIFTH("6 P.M. - 9 P.M."), SIXTH("9 P.M. - 12 A.M.");

	private String value;

	private Slot(String value) {
		this.value = value;
	}

	public String toString() {
		return this.value;
	}
}
