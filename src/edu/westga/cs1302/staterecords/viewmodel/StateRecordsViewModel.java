package edu.westga.cs1302.staterecords.viewmodel;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import edu.westga.cs1302.staterecords.datatier.TextFileLoader;
import edu.westga.cs1302.staterecords.model.NameRecordKey;
import edu.westga.cs1302.staterecords.model.NameRecordValue;
import edu.westga.cs1302.staterecords.model.StateNameRecord;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * This class defines the ViewModel class for the Tech Store application
 * 
 * @author started by CS1302, finished by jabes
 * @version Spring 2023
 */
public class StateRecordsViewModel {

	private final StringProperty nameProperty;
	private final StringProperty genderProperty;
	private final ListProperty<Integer> yearProperty;
	private final ObjectProperty<Integer> selectedYearProperty;
	private final IntegerProperty freqProperty;
	private final ListProperty<NameRecordValue> nameRecordsListProperty;
	private StringProperty stateNameAbriviation;
	// ----------------bar Chart-----------------------
	private final StringProperty barChartYearProperty;
	// ----------------Line Chart----------------------
	private final StringProperty nameProperty2;
	private final StringProperty genderProperty2;
	private final IntegerProperty startYear;
	private final IntegerProperty endYear;
	// ----------------Model--------------------
	private StateNameRecord stateNameRecord;

	/**
	 * Constructor
	 * 
	 */
	public StateRecordsViewModel() {
		this.nameProperty = new SimpleStringProperty();
		this.genderProperty = new SimpleStringProperty();
		this.yearProperty = new SimpleListProperty<Integer>(FXCollections.observableArrayList());
		this.selectedYearProperty = new SimpleObjectProperty<Integer>();
		this.freqProperty = new SimpleIntegerProperty();
		this.stateNameRecord = new StateNameRecord();
		this.stateNameAbriviation = new SimpleStringProperty(this.stateNameRecord.getStateName());
		this.nameRecordsListProperty = new SimpleListProperty<>(
				FXCollections.observableArrayList(this.stateNameRecord.values()));
		// -----------------Bar Chart-------------------------------
		this.barChartYearProperty = new SimpleStringProperty();
		// -----------------Line Chart-----------------------------
		this.nameProperty2 = new SimpleStringProperty();
		this.genderProperty2 = new SimpleStringProperty();
		this.startYear = new SimpleIntegerProperty();
		this.endYear = new SimpleIntegerProperty();
	}

	/**
	 * Gets the nameProperty
	 * 
	 * @precondition none
	 * @postcondition none
	 *
	 * @return the nameProperty
	 */
	public StringProperty nameProperty() {
		return this.nameProperty;
	}

	/**
	 * Gets the genderProperty
	 * 
	 * @precondition none
	 * @postcondition none
	 *
	 * @return the genderProperty
	 */
	public StringProperty genderProperty() {
		return this.genderProperty;
	}

	/**
	 * Gets yearProperty.
	 * 
	 * @precondition none
	 * @postcondition none
	 *
	 * @return the yearProperty
	 */
	public ListProperty<Integer> yearProperty() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		ArrayList<Integer> years = new ArrayList<Integer>();
		for (int year = 1910; year <= currentYear; year++) {
			years.add(year);
		}
		this.yearProperty.addAll(years);
		return this.yearProperty;
	}

	/**
	 * Gets freqProperty.
	 * 
	 * @precondition none
	 * @postcondition none
	 *
	 * @return the freqProperty
	 */
	public IntegerProperty freqProperty() {
		return this.freqProperty;
	}

	/**
	 * Gets stateNameAbriviation.
	 * 
	 * @precondition none
	 * @postcondition none
	 *
	 * @return the stateNameAbriviation
	 */
	public StringProperty stateNameAbriviation() {
		return this.stateNameAbriviation;
	}

	/**
	 * Gets nameRecordsListProperty.
	 * 
	 * @precondition none
	 * @postcondition none
	 *
	 * @return the nameRecordsListProperty
	 */
	public ListProperty<NameRecordValue> babyNameListProperty() {
		return this.nameRecordsListProperty;
	}

	/**
	 * Getter for selected year object property
	 * 
	 * @return the selectedYearProperty
	 */
	public ObjectProperty<Integer> selectedYearProperty() {
		return this.selectedYearProperty;
	}

	/**
	 * Adds a new record to the list of records.
	 * 
	 * @precondition name != null && gender != null && frequency >= 0
	 * @postcondition the new record is added to the list of records
	 * @return true or false depending one weather or not the record was added
	 */
	public boolean addRecord() {
		String name = this.nameProperty.get();
		String gender = this.genderProperty.get();
		int year = this.selectedYearProperty.get();
		int frequency = this.freqProperty.get();

		NameRecordKey key = new NameRecordKey(name, gender, year);
		NameRecordValue value = new NameRecordValue(key, frequency);

		if (this.stateNameRecord.containsKey(key)) {
			return false;
		} else {
			this.stateNameRecord.put(key, value);
			this.nameRecordsListProperty.add(value);
			return true;
		}
	}

	private void updateDisplay() {
		this.nameRecordsListProperty.setAll(FXCollections.observableArrayList(this.stateNameRecord.values()));
	}

	/**
	 * Adds a new record to the list of records.
	 * 
	 * @precondition name != null && gender != null && frequency >= 0
	 * @postcondition the new record is added to the list of records
	 * @return true or false depending one weather or not the record was added
	 */
	public boolean updateRecord() {
		String name = this.nameProperty.get();
		String gender = this.genderProperty.get();
		int year = this.yearProperty.getValue().get(0);
		NameRecordValue selectedRecord = null;
		for (NameRecordValue record : this.nameRecordsListProperty.get()) {
			NameRecordKey key = record.getKey();
			if (key.getName().equals(name) && key.getGender().equals(gender) && key.getYear() == year) {
				selectedRecord = record;
				break;
			}
		}
		if (selectedRecord != null) {
			int frequency = this.freqProperty.get();
			NameRecordKey key = selectedRecord.getKey();
			NameRecordValue updatedRecord = new NameRecordValue(key, frequency);
			this.stateNameRecord.replace(key, updatedRecord);
			this.nameRecordsListProperty.add(updatedRecord);
			this.updateDisplay();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Searches for the a record
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return true or false depending on weather or not the record was found
	 */
	public boolean searchRecords() {
		String name = this.nameProperty.get();
		String gender = this.genderProperty.get();
		int year = this.selectedYearProperty.get();

		for (NameRecordValue record : this.nameRecordsListProperty.get()) {
			NameRecordKey key = record.getKey();
			if (key.getName().equalsIgnoreCase(name) && key.getGender().equalsIgnoreCase(gender)
					&& key.getYear() == year) {
				this.stateNameRecord.clear();
				this.stateNameRecord.put(key, record);
				this.nameRecordsListProperty.add(record);
				this.freqProperty.set(record.getFrequency());
				this.updateDisplay();
				return true;
			}
		}
		return false;
	}

	/**
	 * Deletes a record from the list of records.
	 * 
	 * @precondition selectedRecord != null
	 * @postcondition the selected record is deleted from the list of records
	 * 
	 */

	public void deleteRecord() {
		String name = this.nameProperty.get();
		String gender = this.genderProperty.get();
		int year = this.selectedYearProperty.get();
		NameRecordValue recordToDelete = null;
		for (NameRecordValue record : this.nameRecordsListProperty.get()) {
			NameRecordKey key = record.getKey();
			if (key.getName().equals(name) && key.getGender().equals(gender) && key.getYear() == year) {
				recordToDelete = record;
				break;
			}
		}
		this.nameRecordsListProperty.remove(recordToDelete);

	}

	/**
	 * Saves the records to a file
	 * 
	 * @param selectedFile the file to save the records to
	 * @param stateName    the sate name
	 * @throws IOException if an error occurs while writing to the file
	 */
	public void saveRecordsToFile(File selectedFile, String stateName) throws IOException {
		try (PrintWriter writer = new PrintWriter(new FileWriter(selectedFile))) {
			for (NameRecordValue record : this.nameRecordsListProperty.getValue()) {
				writer.printf("%s,%s,%d,%s,%d%n", stateName, record.getKey().getGender(), record.getKey().getYear(),
						record.getKey().getName(), record.getFrequency());
			}
		}
	}

	/**
	 * Loads the records from a file
	 * 
	 * @param absolutePath the path to the file to load the records from
	 * @throws IOException              if an error occurs while reading from the
	 *                                  file
	 * @throws IllegalArgumentException if the file is not a valid XML file
	 */
	public void loadRecords(String absolutePath) throws IOException {
		TextFileLoader reader = new TextFileLoader(new File(absolutePath));
		List<NameRecordValue> records = reader.loadData();
		for (NameRecordValue theRecords : records) {
			this.stateNameRecord.put(theRecords.getKey(), theRecords);
		}
		this.nameRecordsListProperty.clear();
		this.stateNameAbriviation.set(reader.getStateName());
		this.nameRecordsListProperty.addAll(records);
	}

	/**
	 * Sorts the records by name and year
	 * 
	 * @precondition none
	 * @postcondition none
	 */

	public void sortRecordsByNameAndYear() {
		List<NameRecordValue> residents = new ArrayList<>(this.nameRecordsListProperty.get());
		Collections.sort(residents, new Comparator<NameRecordValue>() {

			@Override
			public int compare(NameRecordValue record1, NameRecordValue person2) {
				int nameComparison = record1.getKey().getName().compareTo(person2.getKey().getName());
				if (nameComparison != 0) {
					return nameComparison;
				}
				return Integer.compare(record1.getKey().getYear(), person2.getKey().getYear());
			}
		});
		this.nameRecordsListProperty.clear();
		this.nameRecordsListProperty.setAll(FXCollections.observableArrayList(residents));
	}

	/**
	 * Sorts the records by year and frequency
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void sortRecordsByYearAndFreq() {
		List<NameRecordValue> records = new ArrayList<NameRecordValue>(this.nameRecordsListProperty.get());
		Collections.sort(records, new Comparator<NameRecordValue>() {
			@Override
			public int compare(NameRecordValue record1, NameRecordValue record2) {
				int yearComparison = Integer.compare(record2.getKey().getYear(), record1.getKey().getYear());
				if (yearComparison != 0) {
					return yearComparison;
				}
				return Integer.compare(record1.getFrequency(), record2.getFrequency());
			}
		});
		this.nameRecordsListProperty.clear();
		this.nameRecordsListProperty.setAll(FXCollections.observableArrayList(records));
	}

	/**
	 * Clear the records
	 * 
	 * @precondition
	 * @postcondition
	 */
	public void clear() {
		this.stateNameRecord.clear();
		this.nameRecordsListProperty.clear();
	}

	// -----------------------------Bar Chart----------------------------------

	/**
	 * Get records of a particular year
	 * 
	 * @param year the year
	 * @return a map of values for the year
	 */
	public Map<NameRecordKey, NameRecordValue> getDataByYear(int year) {
		Map<NameRecordKey, NameRecordValue> dataForYear = new HashMap<>();

		for (NameRecordValue recordValue : this.stateNameRecord.values()) {
			NameRecordKey key = recordValue.getKey();
			if (key.getYear() == year) {
				dataForYear.put(key, recordValue);
			}
		}

		if (dataForYear.isEmpty()) {
			throw new IllegalArgumentException("No data available for year " + year);
		}

		return dataForYear;
	}

	/**
	 * Returns a List of top six names of particular year based of the frequency
	 * 
	 * @param year   the year
	 * @param gender the gender
	 * @return a List of name Records
	 */
	public List<NameRecordValue> getTopNamesByYearAndGender(int year, String gender) {
		List<NameRecordValue> filteredRecords = new ArrayList<>();
		for (NameRecordValue value : this.stateNameRecord.values()) {
			NameRecordKey key = value.getKey();
			if (key.getYear() == year && key.getGender().equals(gender)) {
				filteredRecords.add(value);
			}
		}
		filteredRecords.sort((r1, r2) -> r2.getFrequency() - r1.getFrequency());
		return filteredRecords.subList(0, Math.min(filteredRecords.size(), 6));
	}

	/**
	 * Getter for the bar chart year property
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the barChartYearProperty
	 */
	public StringProperty barChartYearProperty() {
		return this.barChartYearProperty;
	}

	// ---------------------------------Line Chart---------------------------------
	/**
	 * Gets the frequency data for a given name over a given year range and put it
	 * in map
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the map of the frequency data
	 */
	public Map<Integer, Integer> getNameFrequencyData() {
		String name = this.nameProperty2.get();
		String gender = this.genderProperty2.get();
		int startYear = Integer.valueOf(this.startYear.get());
		int endYear = Integer.valueOf(this.endYear.get());
		NameRecordKey key = new NameRecordKey(name, gender, startYear);
		StateNameRecord record = this.stateNameRecord;

		Map<Integer, Integer> frequencyData = new HashMap<>();

		for (int year = startYear; year <= endYear; year++) {
			key.setYear(year);
			NameRecordValue value = record.get(key);

			if (value != null) {
				frequencyData.put(year, value.getFrequency());
			}
		}

		return frequencyData;
	}

	/**
	 * Gets the gender of the given name
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the genderProperty2
	 */
	public StringProperty getGenderProperty2() {
		return this.genderProperty2;
	}

	/**
	 * Gets the name
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the nameProperty2
	 */
	public StringProperty getNameProperty2() {
		return this.nameProperty2;
	}

	/**
	 * Gets the start year
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the startYear
	 */
	public IntegerProperty getStartYear() {
		return this.startYear;
	}

	/**
	 * Gets the end year
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the endYear
	 */
	public IntegerProperty getEndYear() {
		return this.endYear;
	}

}
