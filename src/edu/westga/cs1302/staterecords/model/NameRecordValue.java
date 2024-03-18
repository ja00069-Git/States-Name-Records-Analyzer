package edu.westga.cs1302.staterecords.model;

import edu.westga.cs1302.staterecords.resources.UI;

/**
 * This class wraps the babyName (keeps a record of babyNames)
 * 
 * @author jabes
 * @version Spring 2023
 */
public class NameRecordValue  {
	private final NameRecordKey recordKey;
	private int frequency;
	
	/**
	 * Constructor
	 * 
	 * @param key record key
	 * @param frequency frequency
	 */
	public NameRecordValue(NameRecordKey key, int frequency) {
		
		if (key == null) {
			throw new NullPointerException(UI.ExceptionMessages.NULL_RECORD_KEY);
		}
		
		if (frequency < 0) {
			throw new IllegalArgumentException(UI.ExceptionMessages.FREQUENCY_LESS_THAN_ZERO);
		}
		
		this.recordKey = key;
		this.frequency = frequency;
	}

	/**
	 * Gets the recordKey
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return recordKey
	 */
	public NameRecordKey getKey() {
		return this.recordKey;
	}

	/**
	 * Gets the frequency
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return frequency
	 */
	public int getFrequency() {
		return this.frequency;
	}
	
	/**
	 * Sets the frequency
	 * @precondition none
	 * @postcondition none
	 * @param frequency the frequency
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public String toString() {
		return this.recordKey.getName() + ", " + this.recordKey.getGender() + ", " + this.recordKey.getYear() + ": "
				+ this.frequency;
	}

}
