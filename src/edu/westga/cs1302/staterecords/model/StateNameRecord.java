package edu.westga.cs1302.staterecords.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.westga.cs1302.staterecords.resources.UI;

/**
 * State's baby names record class
 * 
 * @author jabes
 * @version Spring 2023
 */
public class StateNameRecord {

	private String stateName;
	private Map<NameRecordKey, NameRecordValue> records;

	/**
	 * Constructor
	 */
	public StateNameRecord() {
		this.records = new HashMap<NameRecordKey, NameRecordValue>();
	}

	/**
	 * Gets the state name
	 * 
	 * @return the sate name
	 */
	public String getStateName() {
		return this.stateName;
	}

	/**
	 * Sets the state name
	 *
	 * @precondition none
	 * @postcondition none
	 * @param stateName the state name
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * Checks if the records contains a record with the key
	 * 
	 * @precondition recordKey != null
	 * @postcondition none
	 * @param recordKey the record Key
	 * @return true if records contains a record with recordKey, false otherwise
	 */
	public boolean containsKey(NameRecordKey recordKey) {
		if (recordKey == null) {
			throw new NullPointerException(UI.ExceptionMessages.NULL_RECORD_KEY);
		}
		for (NameRecordValue arecord : this.records.values()) {
			if (arecord.getKey().equals(recordKey)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the NameRecordValue with the specified recordKey form the records
	 * 
	 * @precondition recordKey != null
	 * @postcondition none
	 * @param recordKey the record key
	 * @return the NameRecordValue with the specified recordKey if records contains
	 *         the NameRecordValue with recordKey, null otherwise
	 */
	public NameRecordValue get(NameRecordKey recordKey) {
		if (recordKey == null) {
			throw new NullPointerException(UI.ExceptionMessages.NULL_RECORD_KEY);
		}
		for (NameRecordValue recordValue : this.records.values()) {
			if (recordValue.getKey().equals(recordKey)) {
				return recordValue;
			}
		}
		return null;
	}

	/**
	 * Put NameRecordValue in the records
	 * 
	 * @precondition recordValue != null; recordKey != null
	 * @postcondition none
	 * @param recordKey   the recordKey
	 * @param recordValue the recordValue
	 * @return the put record value
	 */
	public NameRecordValue put(NameRecordKey recordKey, NameRecordValue recordValue) {
		if (recordValue == null) {
			throw new NullPointerException(UI.ExceptionMessages.NULL_RECORD_VALUE);
		}
		if (recordKey == null) {
			throw new NullPointerException(UI.ExceptionMessages.NULL_RECORD_KEY);
		}
		return this.records.put(recordKey, recordValue);
	}

	/**
	 * Replace a NameRecordValue in the records with an new one
	 * 
	 * @precondition recordValue != null; recordKey != null
	 * @postcondition none
	 * @param recordKey   the recordKey
	 * @param recordValue the recordValue
	 * @return the new recordValue
	 */
	public NameRecordValue replace(NameRecordKey recordKey, NameRecordValue recordValue) {
		if (recordValue == null) {
			throw new NullPointerException(UI.ExceptionMessages.NULL_RECORD_VALUE);
		}
		if (recordKey == null) {
			throw new NullPointerException(UI.ExceptionMessages.NULL_RECORD_KEY);
		}
		return this.records.replace(recordKey, recordValue);
	}

	/**
	 * Gets the NameRecordKey
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return NameRecordKey
	 */
	public NameRecordKey getReocrdKey() {
		NameRecordKey recordKey = null;
		for (Map.Entry<NameRecordKey, NameRecordValue> entry : this.records.entrySet()) {
			recordKey = entry.getKey();
		}
		return recordKey;
	}

	/**
	 * Gets the NameRecordValue
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return NameRecordValue
	 */
	public NameRecordValue getRecordValue() {
		NameRecordValue recordValue = null;
		for (Map.Entry<NameRecordKey, NameRecordValue> entry : this.records.entrySet()) {
			recordValue = entry.getValue();
		}
		return recordValue;
	}

	/**
	 * records Values (NameRecordValues)
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return Values
	 */
	public Collection<NameRecordValue> values() {
		return this.records.values();
	}

	/**
	 * Checks if the records is empty
	 * 
	 * @postcondition none
	 * @postcondition none
	 * @return true or false depending on whether or not the records is empty
	 */
	public boolean isEmpty() {
		return this.records.values().isEmpty();
	}

	/**
	 * Returns the removed key and value from the records
	 * 
	 * @param recordKey the recordKey to check
	 * @return the computer
	 */
	public boolean remove(Object recordKey) {
		if (recordKey == null) {
			throw new NullPointerException(UI.ExceptionMessages.NULL_RECORD_KEY);
		}
		return this.records.remove(recordKey, this.get((NameRecordKey) recordKey));
	}

	/**
	 * The size of the inventory
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the size
	 */
	public int size() {
		return this.records.values().size();

	}

	/**
	 * Clears the records
	 * 
	 * @postcondition: none
	 * @postcondition none
	 */
	public void clear() {
		this.records.values().clear();
	}

	@Override
	public String toString() {
		StringBuilder keyAndValue = new StringBuilder();
		for (Map.Entry<NameRecordKey, NameRecordValue> entry : this.records.entrySet()) {
			keyAndValue.append(entry.getValue().toString()).append("\n");
		}
		return keyAndValue.toString();
	}

}
