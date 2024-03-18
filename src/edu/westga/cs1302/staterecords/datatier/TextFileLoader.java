package edu.westga.cs1302.staterecords.datatier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.westga.cs1302.staterecords.model.NameRecordKey;
import edu.westga.cs1302.staterecords.model.NameRecordValue;
import edu.westga.cs1302.staterecords.model.StateNameRecord;

/**
 * Loads data from a file
 * 
 * @author jabes
 * @version Spring 2023
 */
public class TextFileLoader implements TextLoader {

	private File file;
	private String stateName;

	/**
	 * Instantiates a new text file loader.
	 *
	 * @precondition none
	 * @postcondition none
	 * @param file the file
	 */
	public TextFileLoader(File file) {
		this.file = file;
	}

	/**
	 * Gets the name of the state on the file
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the name of the state
	 */
	public String getStateName() {
		return this.stateName;
	}

	/**
	 * Sets the name of the state on the file
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param stateName the name of the sate on the file
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public List<NameRecordValue> loadData() throws IOException {
		ArrayList<NameRecordValue> babyNames = new ArrayList<>();
		try (Scanner scanner = new Scanner(this.file)) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				try {
					this.nameRecordKeyAndValueFromReadLine(babyNames, line);
				} catch (Exception ex) {
					System.err.println("Error reading file: invalid " + line);
				}
			}
		}
		return babyNames;
	}

	/**
	 * Creates a StateNameRecord object from a line of input and adds it to the
	 * given list of baby names.
	 *
	 * @param babyNames the list of baby names to add the new StateNameRecord to
	 * @param line      the line of input containing the data for the new
	 *                  StateNameRecord in the format "GA,F,1910,Mary,841"
	 */
	private void nameRecordKeyAndValueFromReadLine(ArrayList<NameRecordValue> babyNames, String line) {

		String[] lineArray = line.split(",");

		if (lineArray.length == 5) {
			String stateAbbr = (lineArray[0]);
			String gender = (lineArray[1]);
			int year = Integer.parseInt(lineArray[2]);
			String name = lineArray[3];
			int freq = Integer.parseInt(lineArray[4]);
			NameRecordKey recK = new NameRecordKey(name, gender, year);
			NameRecordValue recV = new NameRecordValue(recK, freq);
			StateNameRecord stateRec = new StateNameRecord();
			this.stateName = stateAbbr;
			stateRec.put(recK, recV);
			babyNames.add(recV);
		} else {
			System.err.println("Error reading line: invalid format: " + line);
		}
	}

}
