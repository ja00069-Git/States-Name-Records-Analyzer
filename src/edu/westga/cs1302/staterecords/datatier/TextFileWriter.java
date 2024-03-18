package edu.westga.cs1302.staterecords.datatier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import edu.westga.cs1302.staterecords.model.StateNameRecord;
import edu.westga.cs1302.staterecords.resources.UI;

/**
 * Saves the data to a file
 * 
 * @author jabes
 * @version Spring 2023
 */
public class TextFileWriter {

	private File file;

	/**
	 * Instantiates a new test file writer.
	 *
	 * @precondition file != null
	 * @postcondition none
	 * @param file the file to write to
	 */
	public TextFileWriter(File file) {
		if (file == null) {
			throw new IllegalArgumentException(UI.ExceptionMessages.NULL_FILE);
		}
		this.file = file;
	}

	/**
	 * Saves data to a cvs or text file
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param dataToSave  the  data to save
	 * @throws IOException
	 */
	public void save(List<StateNameRecord> dataToSave) throws IOException, FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(this.file)) {
			for (StateNameRecord babyName : dataToSave) {
				writer.print(babyName.getReocrdKey().getName());
				writer.print(",");
				writer.print(babyName.getReocrdKey().getGender());
				writer.print(",");
				writer.print(babyName.getReocrdKey().getYear());
				writer.print(",");
				writer.print(babyName.getRecordValue().getFrequency());
				writer.print(System.lineSeparator());
			}
		}

	}
}
