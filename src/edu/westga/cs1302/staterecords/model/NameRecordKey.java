package edu.westga.cs1302.staterecords.model;

import java.util.Objects;

import edu.westga.cs1302.staterecords.resources.UI;

/**
 * The name frequencies class
 * 
 * @author jabes
 * @version Spring 2023
 */
public class NameRecordKey implements Comparable<NameRecordKey> {
	private static final int MIN_YEAR = 1910;
	private final String name;
	private final String gender;
	private int year;

	/**
	 * Constructor
	 * 
	 * @param name   the name to record
	 * @param gender the gender associated with the name
	 * @param year   the year associated with the name
	 */
	public NameRecordKey(String name, String gender, int year) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException(UI.ExceptionMessages.NAME_NULL_EMPTY);
		}
		if (gender == null || gender.isBlank()) {
			throw new IllegalArgumentException(UI.ExceptionMessages.GENDER_NULL_EMPTY);
		}

		if (year < MIN_YEAR) {
			throw new IllegalArgumentException(UI.ExceptionMessages.INAVLID_YEAR);
		}

		this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		this.gender = gender;
		this.year = year;
	}

	/**
	 * Gets the name
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the gender
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return gender
	 */
	public String getGender() {
		return this.gender;
	}

	/**
	 * Gets the year
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return year
	 */
	public int getYear() {
		return this.year;
	}
	
	/**
	 * Sets the year
	 * @precondition none
	 * @postcondition none
	 * @param year the year to set to
	 */
	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof NameRecordKey)) {
			return false;
		}
		NameRecordKey other = (NameRecordKey) obj;
		return Objects.equals(this.name, other.name) && Objects.equals(this.gender, other.gender)
				&& this.year == other.year;
	}

	@Override
	public int compareTo(NameRecordKey recordKey) {
		if (this.gender != recordKey.gender) {
			return this.gender.compareTo(recordKey.gender);
		} else if (!this.name.equals(recordKey.name)) {
			return this.name.compareTo(recordKey.name);
		} else {
			return Integer.compare(this.year, recordKey.year);
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.gender, this.year);
	}

	@Override
	public String toString() {
		return this.name + "," + this.gender + "," + this.year;
	}

}
