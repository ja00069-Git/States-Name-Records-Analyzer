package edu.westga.cs1302.staterecords.view;

import javafx.util.converter.NumberStringConverter;

/**
 * Frequency converter class
 * 
 * @author jabes
 * @version Spring 2023
 */
public class FrequencyYearNumberConverter extends NumberStringConverter {

	@Override
	public Integer fromString(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
