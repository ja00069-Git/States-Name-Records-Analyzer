package edu.westga.cs1302.staterecords.datatier;

import java.io.IOException;
import java.util.List;

import edu.westga.cs1302.staterecords.model.NameRecordValue;

/**
 * Text loader interface
 * 
 * @author jabes
 * @version Spring 2023
 */
public interface TextLoader {

	/**
	 * Load text data
	 * @precondition none
	 * @postcondition none
	 * @return List of loaded data
	 */
	List<NameRecordValue> loadData() throws IOException;
}
