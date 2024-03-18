package edu.westga.cs1302.staterecords.resources;

/**
 * This class keeps track of a number of Strings designed to be standard output
 * messages for the user
 * 
 * @author CS1302
 * @version Spring 2023
 *
 */
public class UI {
	/**
	 * Exception messages
	 * 
	 * @author jabes
	 *
	 */
	public static final class ExceptionMessages {

		public static final String NAME_NULL_EMPTY = "Name cannot be null or empty";
		public static final String GENDER_NULL_EMPTY = "Gender cannot be null or empty";
		public static final String INAVLID_YEAR = "Year cannot be before 1910";
		public static final String FREQUENCY_LESS_THAN_ZERO = "Frequency cannot less than zero";
		public static final String EMPTY_STATENAME = "StateName cannot be empty";
		public static final String NULL_STATENAME = "StateName cannot be null";
		public static final String INVALID_STATENAME = "StateName should be 2 letters";
		public static final String NULL_RECORD_VALUE = "Record value cannot be null";
		public static final String NULL_RECORD_KEY = "Record key cannot be null";
		public static final String FIELD_SEPERATOR = ",";
		public static final String SPACE = " ";
		public static final String REQUIRED = "Value is required.";
		public static final String YEAR_FOUR_DIGITS = "Year should have 4 digits.";
		public static final String CANNOT_START_WITH_ZERO = "Cannot start with zero.";
		public static final String CANNOT_BE_NEGATIVE = "Value must be positive.";
		public static final String INVALID = "Value is invalid";
		public static final String NULL_FILE = "File name canot be null";
	}

	/**
	 * Other
	 * 
	 * @author jabes
	 *
	 */
	public static final class Text {
		public static final String NO_RECORDS_COULD_BE_FOUND_IN_THE_FILE = "No records could be found in the file.";
		public static final String NO_RECORDS_FOUND = "No Records Found";
		public static final String INVALID_FORMAT_IN_THE_FILE = "Invalid format in the file.";
		public static final String AN_ERROR_OCCURRED_WHILE_LOADING_THE_FILE = "An error occurred while loading the file.";
		public static final String ERROR = "Error";
		public static final String FILE_HAS_BEEN_SUCCESSFULLY_LOADED = "File has been successfully loaded!";
		public static final String FILE_LOADED = "File Loaded";
		public static final String OPEN_STATE_RECORDS_FILE = "Open State Records File";
		public static final String ARE_YOU_SURE_YOU_WANT_TO_EXIT = "Are you sure you want to exit?";
		public static final String EXIT_APPLICATION = "Exit Application";
		public static final String RECORD_WAS_NOT_UPDATED_PLEASE_TRY_AGAIN = "Record was not updated. Please try again.";
		public static final String RECORD_NOT_UPDATED = "Record Not Updated";
		public static final String RECORD_HAS_BEEN_SUCCESSFULLY_UPDATED = "Record has been successfully updated!";
		public static final String RECORD_UPDATED = "Record Updated";
		public static final String ABOUT_THE_APP = "About the app";
		public static final String STATE_NAME = "State name:";
		public static final String ENTER_THE_STATE_NAME = "Enter the state name:";
		public static final String SAVE_STATE_RECORDS = "Save State Records";
		public static final String AN_ERROR_OCCURRED_WHILE_SAVING_THE_FILE = "An error occurred while saving the file.";
		public static final String ERROR_SAVING_FILE = "Error saving file";
		public static final String COULD_NOT_ADD = "Could not add";
		public static final String INVALID_INPUT = "Invalid Input";
		public static final String RECORD_HAS_BEEN_SUCCESSFULLY_ADDED = "Record has been successfully added!";
		public static final String RECORD_ADDED = "Record Added";
		public static final String RECORD_HAS_BEEN_SUCCESSFULLY_DELETED = "Record has been successfully deleted!";
		public static final String RECORD_DELETED = "Record Deleted";
		public static final String NO_RECORD_WAS_FOUND_FOR_THE_GIVEN_NAME_GENDER_AND_YEAR = "No record was found for the given name, gender, and year.";
		public static final String RECORD_NOT_FOUND = "Record Not Found";
		public static final String FOUND_RESULTS = "Found Results";
		public static final String YOUR_SEARCH_RESULT_IS_DISPLAYED = "Your search result is displayed.";
		public static final String YOUR_SEARCH_DID_NOT_MATCH_ANY_RECORDS = "Your search did not match any records.";
		public static final String NO_RESULTS_FOUND = "No Results Found";
		public static final String INVALIDE_DATA_INTERED = "Invalide data intered";
	}

}
