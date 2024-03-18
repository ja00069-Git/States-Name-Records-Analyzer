package edu.westga.cs1302.staterecords;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class is the entry point into the Tech Store application
 * 
 * @author CS1302
 * @version Spring 2023
 */
public class Main extends Application {

	private static final String WINDOW_TITLE = "Project 3 : Jabesi Abwe";
	private static final String GUI_FXML = "view/StateRecordsGui.fxml";

	/**
	 * Constructs a new Application object for the Tech Store program.
	 * 
	 * @precondition none
	 * @postcondition the object is ready to execute
	 */
	public Main() {
		super();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Pane thePane = this.loadGui();
			Scene theScene = new Scene(thePane);
			primaryStage.setScene(theScene);
			primaryStage.setTitle(WINDOW_TITLE);
			primaryStage.show();
		} catch (IllegalStateException | IOException anException) {
			anException.printStackTrace();
		}
	}

	private Pane loadGui() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(GUI_FXML));
		return (Pane) loader.load();
	}

	/**
	 * Launches the application.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param args - not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
