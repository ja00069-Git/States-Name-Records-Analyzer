package edu.westga.cs1302.staterecords.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import edu.westga.cs1302.staterecords.model.NameRecordKey;
import edu.westga.cs1302.staterecords.model.NameRecordValue;
import edu.westga.cs1302.staterecords.resources.UI;
import edu.westga.cs1302.staterecords.viewmodel.StateRecordsViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Code Behind Class
 * 
 * @author jabes
 * @version Spring 2023
 */
public class StateRecordsCodeBehind {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private AnchorPane stateRecordsAnchorPane;
	@FXML
	private MenuItem aboutHelpMenu;
	@FXML
	private Button addButton;
	@FXML
	private ListView<NameRecordValue> babyNameRecordListView;
	@FXML
	private Button clearButton;
	@FXML
	private Button deleteButton;
	@FXML
	private MenuItem exitFileMenu;
	@FXML
	private RadioButton femaleRadioBtn;
	@FXML
	private TextField freqTextField;
	@FXML
	private RadioButton maleRadioBtn;
	@FXML
	private TextField nameTextField;
	@FXML
	private MenuItem openFileMenu;
	@FXML
	private Label sateLabel;
	@FXML
	private Label freqErrorLabel;
	@FXML
	private Label genderErrorLabel;
	@FXML
	private Label nameErrorLabel;
	@FXML
	private Label yearErrorLabel;
	@FXML
	private MenuItem saveFileMenu;
	@FXML
	private Button searchButton;
	@FXML
	private MenuItem sortNameYearSortMenu;
	@FXML
	private MenuItem sortYearFreqSortMenu;
	@FXML
	private Button updateButton;
	@FXML
	private ComboBox<Integer> yearComBox;
	@FXML
	private BarChart<String, Number> barChart;
	@FXML
	private RadioButton barChart3M3FRadioBtn;
	@FXML
	private RadioButton barChart6FRadioBtn;
	@FXML
	private RadioButton barChart6MRadioBtn;
	@FXML
	private Label barChartErrorLabel;
	@FXML
	private Button barChartNextBtn;
	@FXML
	private Button barChartPrevBtn;
	@FXML
	private LineChart<String, Number> lineChart;
	@FXML
	private TextField lineChartEndYear;
	@FXML
	private Label lineChartErrorLabel;
	@FXML
	private RadioButton lineChartFRadioBtn;
	@FXML
	private RadioButton lineChartMRadioBtn;
	@FXML
	private TextField lineChartNameTextField;
	@FXML
	private TextField lineChartStarYear;
	@FXML
	private Button lineChartSubmitBtn;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private CategoryAxis xAxisLineChar;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private NumberAxis yAxisLineChart;
	@FXML
	private TextField barChartYearTextField;
	private StateRecordsViewModel viewModel;
	private ObjectProperty<Integer> selectedYear;

	/**
	 * Constructor
	 */
	public StateRecordsCodeBehind() {
		this.viewModel = new StateRecordsViewModel();
		this.selectedYear = new SimpleObjectProperty<Integer>();
	}

	@FXML
	void initialize() {
		this.bindToViewModel();
		this.changeListers();
		this.barChartLister();
		this.barChartPrevNextButtonsHandlers();
		this.barChartRaidoButtonHandlers();
		this.lineChartListeners();

	}

	private void bindToViewModel() {
		this.babyNameRecordListView.itemsProperty().bindBidirectional(this.viewModel.babyNameListProperty());
		this.nameTextField.textProperty().bindBidirectional(this.viewModel.nameProperty());
		this.yearComBox.itemsProperty().bindBidirectional(this.viewModel.yearProperty());
		this.viewModel.selectedYearProperty().bind(this.selectedYear);
		this.freqTextField.textProperty().bindBidirectional(this.viewModel.freqProperty(),
				new FrequencyYearNumberConverter());
		this.bindGender(this.viewModel);
		// ------------------------Bar Chart----------------------------
		this.barChartYearTextField.textProperty().bindBidirectional(this.viewModel.barChartYearProperty());
		// ------------------------Line Chart----------------------------
		this.lineChartNameTextField.textProperty().bindBidirectional(this.viewModel.getNameProperty2());
		this.lineChartStarYear.textProperty().bindBidirectional(this.viewModel.getStartYear(),
				new FrequencyYearNumberConverter());
		this.lineChartEndYear.textProperty().bindBidirectional(this.viewModel.getEndYear(),
				new FrequencyYearNumberConverter());
		this.bindlineChartGenders(this.viewModel);
	}

	/**
	 * bind toggle results to view model
	 * 
	 * @param viewModel the view model
	 */
	public void bindGender(StateRecordsViewModel viewModel) {
		this.viewModel = viewModel;

		ToggleGroup genderToggleGroup = new ToggleGroup();
		this.maleRadioBtn.setToggleGroup(genderToggleGroup);
		this.femaleRadioBtn.setToggleGroup(genderToggleGroup);

		viewModel.genderProperty().bind(Bindings.createStringBinding(() -> {
			Toggle selectedToggle = genderToggleGroup.getSelectedToggle();
			if (selectedToggle != null) {
				String gender = ((RadioButton) selectedToggle).getText();
				return String.valueOf(gender.charAt(0));
			} else {
				return null;
			}
		}, genderToggleGroup.selectedToggleProperty()));
	}

	private void changeListers() {
		this.babyNameRecordListView.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldSelection, newSelection) -> {
					this.displaySelectedRecord(newSelection);
				});

		this.nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				return;
			}
			char firstChar = newValue.charAt(0);
			if (!Character.isLetter(firstChar)) {
				this.nameTextField.setText(oldValue);
				this.nameErrorLabel.setVisible(true);
				this.nameErrorLabel.setText("Only letters are allowed");
				return;
			}
			String formattedValue = Character.toUpperCase(firstChar) + newValue.substring(1).toLowerCase();
			if (!formattedValue.equals(newValue)) {
				this.nameTextField.setText(formattedValue);
			}
			if (!newValue.matches("[a-zA-Z]*")) {
				this.nameTextField.setText(oldValue);
				this.nameErrorLabel.setVisible(true);
				this.nameErrorLabel.setText("Only letters are allowed.");
			} else {
				this.nameErrorLabel.setVisible(false);
			}
		});

		this.yearComBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.yearErrorLabel.setVisible(false);
				this.selectedYear.set(newValue);
			} else {
				this.yearErrorLabel.setVisible(true);
				this.yearErrorLabel.setText("Please select a year.");
			}
		});

		this.freqTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				if (!newValue.isBlank()) {
					Integer.parseInt(newValue);
					this.freqErrorLabel.setVisible(false);
				}

			} catch (NumberFormatException ex) {
				this.freqTextField.setText(oldValue);
				this.freqErrorLabel.setVisible(true);
				this.freqErrorLabel.setText("Numbers are allowed.");
			}
		});

	}

	@FXML
	void btnAddOnClick(ActionEvent event) {
		try {
			this.viewModel.addRecord();
			this.showAlert(AlertType.INFORMATION, UI.Text.RECORD_ADDED, UI.Text.RECORD_HAS_BEEN_SUCCESSFULLY_ADDED);
			this.clearFields();
		} catch (IllegalArgumentException ex) {
			this.showAlert(AlertType.ERROR, UI.Text.INVALID_INPUT, ex.getMessage());
		} catch (Exception ex) {
			this.showAlert(AlertType.ERROR, UI.Text.COULD_NOT_ADD, ex.getMessage());
		}
	}

	@FXML
	void btnClearOnClick(ActionEvent event) {
		this.nameTextField.setText("");
		this.freqTextField.setText("");
		this.maleRadioBtn.setSelected(false);
		this.femaleRadioBtn.setSelected(false);
		this.yearComBox.getSelectionModel().clearSelection();
		this.viewModel.clear();
	}

	@FXML
	void btnDeleteOnClick(ActionEvent event) {

		try {
			this.viewModel.deleteRecord();
			this.showAlert(AlertType.INFORMATION, UI.Text.RECORD_DELETED, UI.Text.RECORD_HAS_BEEN_SUCCESSFULLY_DELETED);
		} catch (IllegalArgumentException ex) {
			this.showAlert(AlertType.ERROR, UI.Text.INVALID_INPUT, ex.getMessage());
		} catch (Exception ex) {
			this.showAlert(AlertType.ERROR, UI.Text.RECORD_NOT_FOUND,
					UI.Text.NO_RECORD_WAS_FOUND_FOR_THE_GIVEN_NAME_GENDER_AND_YEAR);
		}
	}

	@FXML
	void btnSearchOnClick(ActionEvent event) {
		try {
			if (this.viewModel.searchRecords()) {
				this.showAlert(AlertType.INFORMATION, UI.Text.FOUND_RESULTS, UI.Text.YOUR_SEARCH_RESULT_IS_DISPLAYED);
			} else {
				this.showAlert(AlertType.WARNING, UI.Text.NO_RESULTS_FOUND,
						UI.Text.YOUR_SEARCH_DID_NOT_MATCH_ANY_RECORDS);
			}
		} catch (Exception ex) {
			this.showAlert(AlertType.WARNING, UI.Text.INVALIDE_DATA_INTERED, ex.getMessage());
		}
	}

	@FXML
	void btnUpdateOnClick(ActionEvent event) {

		try {
			if (this.viewModel.updateRecord()) {
				this.showAlert(AlertType.INFORMATION, UI.Text.RECORD_UPDATED,
						UI.Text.RECORD_HAS_BEEN_SUCCESSFULLY_UPDATED);
				this.clearFields();
			} else {
				this.showAlert(AlertType.WARNING, UI.Text.RECORD_NOT_UPDATED,
						UI.Text.RECORD_WAS_NOT_UPDATED_PLEASE_TRY_AGAIN);
			}
		} catch (Exception ex) {
			this.showAlert(AlertType.WARNING, UI.Text.RECORD_NOT_UPDATED, ex.getMessage());
		}
	}

	@FXML
	void handleExitFile(ActionEvent event) {
		boolean confirmed = this.showConfirmation(UI.Text.EXIT_APPLICATION, UI.Text.ARE_YOU_SURE_YOU_WANT_TO_EXIT);
		if (confirmed) {
			Stage stage = (Stage) (this.stateRecordsAnchorPane).getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	void handleOpenFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(UI.Text.OPEN_STATE_RECORDS_FILE);
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("CSV Files", "*.csv"));
		File selectedFile = fileChooser.showOpenDialog(this.stateRecordsAnchorPane.getScene().getWindow());
		if (selectedFile != null) {
			try {
				this.viewModel.loadRecords(selectedFile.getAbsolutePath());
				this.sateLabel.setText("State: " + this.viewModel.stateNameAbriviation().getValue());
				this.showAlert(AlertType.INFORMATION, UI.Text.FILE_LOADED, UI.Text.FILE_HAS_BEEN_SUCCESSFULLY_LOADED);
			} catch (IOException ex) {
				this.showAlert(AlertType.ERROR, UI.Text.ERROR, UI.Text.AN_ERROR_OCCURRED_WHILE_LOADING_THE_FILE);
			} catch (IllegalFormatException ex) {
				System.err.println(ex.getMessage());
				this.showAlert(AlertType.ERROR, UI.Text.ERROR, UI.Text.INVALID_FORMAT_IN_THE_FILE);
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
				this.showAlert(AlertType.WARNING, UI.Text.NO_RECORDS_FOUND,
						UI.Text.NO_RECORDS_COULD_BE_FOUND_IN_THE_FILE);
			}
		}
	}

	@FXML
	void handleSaveFile(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog(this.viewModel.stateNameAbriviation().get());
		dialog.setTitle(UI.Text.SAVE_STATE_RECORDS);
		dialog.setHeaderText(UI.Text.ENTER_THE_STATE_NAME);
		dialog.setContentText(UI.Text.STATE_NAME);

		while (true) {
			Optional<String> result = dialog.showAndWait();
			if (!result.isPresent()) {
				return;
			}
			String stateName = result.get().trim();
			if (!stateName.isEmpty()) {
				try {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setInitialFileName(stateName + ".csv");
					fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"),
							new ExtensionFilter("Text Files", "*.txt"));
					File selectedFile = fileChooser.showSaveDialog(this.stateRecordsAnchorPane.getScene().getWindow());

					if (selectedFile != null) {
						this.viewModel.saveRecordsToFile(selectedFile, stateName);
						this.sateLabel.setText("State: " + stateName);
						return;
					}
				} catch (IOException ex) {
					this.showAlert(AlertType.ERROR, UI.Text.ERROR_SAVING_FILE,
							UI.Text.AN_ERROR_OCCURRED_WHILE_SAVING_THE_FILE);
					return;
				}
			}
		}
	}

	@FXML
	void handleSortNameYear(ActionEvent event) {
		this.viewModel.sortRecordsByNameAndYear();
	}

	@FXML
	void handleSortYearFreq(ActionEvent event) {
		this.viewModel.sortRecordsByYearAndFreq();
	}

	@FXML
	void hendleABout(ActionEvent event) {
		String aboutText = "State Records Viewer\n\n"
				+ "This program allows the user to view and manage state records of baby names.\n\n"
				+ "Developed by: Jabesi Abwe\n" + "Version: Spring 2023\n" + "Date: April 30, 2023";
		this.showAlert(AlertType.INFORMATION, UI.Text.ABOUT_THE_APP, aboutText);
	}

	private void displaySelectedRecord(NameRecordValue newSelection) {
		try {
			if (newSelection != null) {
				this.nameTextField.setText(newSelection.getKey().getName());
				this.yearComBox.setValue(newSelection.getKey().getYear());
				String gender = newSelection.getKey().getGender();
				if (gender.equals("M")) {
					this.maleRadioBtn.setSelected(true);
				} else if (gender.equals("F")) {
					this.femaleRadioBtn.setSelected(true);
				}
				this.freqTextField.setText(String.valueOf(newSelection.getFrequency()));
			}
		} catch (Exception ex) {
			this.showAlert(AlertType.ERROR, UI.Text.INVALID_INPUT, ex.getMessage());
		}
	}

	private boolean showConfirmation(String message, String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == ButtonType.OK;
	}

	private void showAlert(AlertType type, String title, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private void clearFields() {
		this.nameTextField.setText("");
		this.freqTextField.setText("");
		this.yearComBox.getSelectionModel().clearSelection();
		this.maleRadioBtn.isDisable();
		this.sateLabel.setText(this.sateLabel.getText());
	}

	// --------------------------------BAR CHAR-------------------------------------

	private void barChartLister() {

		this.barChartYearTextField.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				String yearText = this.barChartYearTextField.getText();
				if (!yearText.isEmpty()) {
					try {
						this.displayChart(3);
					} catch (NumberFormatException ex) {
						this.clearBarChart();
						this.showMessage("Invalid year entered");
					}
				}
			}
		});

		this.barChartYearTextField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				String yearText = this.barChartYearTextField.getText();
				if (!yearText.isEmpty()) {
					try {
						this.displayChart(3);
					} catch (NumberFormatException ex) {
						this.clearBarChart();
						this.showMessage("Invalid year entered");
					}
				}
			}
		});

	}

	private void barChartPrevNextButtonsHandlers() {
		this.barChartNextBtn.setOnAction(event -> {
			this.handleNextButtonClick();
		});

		this.barChartPrevBtn.setOnAction(event -> {
			this.handlePreviousButtonClick();
		});
	}

	private void barChartRaidoButtonHandlers() {
		ToggleGroup toggleGroup = new ToggleGroup();

		this.barChart3M3FRadioBtn.setToggleGroup(toggleGroup);
		this.barChart6MRadioBtn.setToggleGroup(toggleGroup);
		this.barChart6FRadioBtn.setToggleGroup(toggleGroup);

		this.barChart3M3FRadioBtn.setSelected(true);

		toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
			if (newToggle == null) {
				toggleGroup.selectToggle(oldToggle);
				this.clearBarChart();
			} else if (newToggle == this.barChart3M3FRadioBtn) {
				this.clearBarChart();
				this.displayChart(3);
			} else if (newToggle == this.barChart6MRadioBtn) {
				this.clearBarChart();
				this.displayChart(6);
			} else if (newToggle == this.barChart6FRadioBtn) {
				this.clearBarChart();
				this.displayChart(3);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void displayChart(int numNames) {
		int year = Integer.valueOf(this.barChartYearTextField.getText());
		try {
			this.viewModel.getDataByYear(year);
			this.clearBarChart();
			this.clearMessage();
			XYChart.Series<String, Number> maleSeries = new XYChart.Series<>();
			XYChart.Series<String, Number> femaleSeries = new XYChart.Series<>();
			maleSeries.setName("Male");
			femaleSeries.setName("Female");

			if (this.barChart3M3FRadioBtn.isSelected()) {
				for (NameRecordValue recordValue : this.viewModel.getTopNamesByYearAndGender(year, "M").subList(0, 3)) {
					NameRecordKey key = recordValue.getKey();
					maleSeries.getData().add(new XYChart.Data<>(key.getName(), recordValue.getFrequency()));
				}
				for (NameRecordValue recordValue : this.viewModel.getTopNamesByYearAndGender(year, "F").subList(0, 3)) {
					NameRecordKey key = recordValue.getKey();
					femaleSeries.getData().add(new XYChart.Data<>(key.getName(), recordValue.getFrequency()));
				}
			} else if (this.barChart6MRadioBtn.isSelected()) {
				List<NameRecordValue> topMaleRecords = this.viewModel.getTopNamesByYearAndGender(year, "M");
				for (int index = 0; index < Math.min(topMaleRecords.size(), numNames); index++) {
					NameRecordValue recordValue = topMaleRecords.get(index);
					NameRecordKey key = recordValue.getKey();
					maleSeries.getData().add(new XYChart.Data<>(key.getName(), recordValue.getFrequency()));
				}
			} else if (this.barChart6FRadioBtn.isSelected()) {
				List<NameRecordValue> topFemaleRecords = this.viewModel.getTopNamesByYearAndGender(year, "F");
				for (int index = 0; index < Math.min(topFemaleRecords.size(), numNames); index++) {
					NameRecordValue recordValue = topFemaleRecords.get(index);
					NameRecordKey key = recordValue.getKey();
					femaleSeries.getData().add(new XYChart.Data<>(key.getName(), recordValue.getFrequency()));
				}
			}

			this.barChart.getData().addAll(maleSeries, femaleSeries);
			maleSeries.getData().forEach(data -> data.getNode().setStyle("-fx-bar-fill: #87CEEB;"));
			femaleSeries.getData().forEach(data -> data.getNode().setStyle("-fx-bar-fill: #FFC0CB;"));
			this.barChart.setTitle("Top Names: " + year);

		} catch (Exception ex) {
			this.showMessage("No data available for year " + year);
		}
	}

	private void handlePreviousButtonClick() {
		int currentYear = Integer.parseInt(this.barChartYearTextField.getText());
		int previousYear = currentYear - 1;
		this.barChartYearTextField.setText(Integer.toString(previousYear));
		this.displayChart(previousYear);
	}

	private void handleNextButtonClick() {
		int currentYear = Integer.parseInt(this.barChartYearTextField.getText());
		int nextYear = currentYear + 1;
		this.barChartYearTextField.setText(Integer.toString(nextYear));
		this.displayChart(nextYear);
	}

	private void clearBarChart() {
		this.barChart.getData().clear();
	}

	private void showMessage(String message) {
		this.barChartErrorLabel.setText(message);
	}

	private void clearMessage() {
		this.barChartErrorLabel.setText("");
	}

	// ---------------------------------Line Chart-------------------------------

	private void lineChartListeners() {

		this.lineChartSubmitBtn.setOnAction(event -> {
			this.clearLineChart();
			this.displayLineChart();
		});

		this.lineChartNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				return;
			}
			char firstChar = newValue.charAt(0);
			if (!Character.isLetter(firstChar)) {
				this.lineChartNameTextField.setText(oldValue);
				this.lineChartErrorLabel.setVisible(true);
				this.lineChartErrorLabel.setText("Only letters are allowed");
				return;
			}
			String formattedValue = Character.toUpperCase(firstChar) + newValue.substring(1).toLowerCase();
			if (!formattedValue.equals(newValue)) {
				this.lineChartNameTextField.setText(formattedValue);
			}
			if (!newValue.matches("[a-zA-Z]*")) {
				this.lineChartNameTextField.setText(oldValue);
				this.lineChartErrorLabel.setVisible(true);
				this.lineChartErrorLabel.setText("Only letters are allowed.");
			} else {
				this.lineChartErrorLabel.setVisible(false);
			}
		});

	}

	private void displayLineChart() {
		try {
			this.clearLineChart();
			Map<Integer, Integer> nameFrequencyData = this.viewModel.getNameFrequencyData();
			XYChart.Series<String, Number> series = new XYChart.Series<>();

			for (Map.Entry<Integer, Integer> entry : nameFrequencyData.entrySet()) {
				series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
			}

			this.lineChart.getData().add(series);

		} catch (Exception ex) {
			this.lineChartErrorLabel.setVisible(true);
			this.lineChartErrorLabel.setText("No data available for " + this.lineChartNameTextField.getText());
		}
	}

	/**
	 * Binds line chart genders to the view model
	 * 
	 * @param viewModel the view model
	 */
	public void bindlineChartGenders(StateRecordsViewModel viewModel) {
		this.viewModel = viewModel;

		ToggleGroup genderToggleGroup = new ToggleGroup();
		this.lineChartMRadioBtn.setToggleGroup(genderToggleGroup);
		this.lineChartFRadioBtn.setToggleGroup(genderToggleGroup);

		viewModel.getGenderProperty2().bind(Bindings.createStringBinding(() -> {
			Toggle selectedToggle = genderToggleGroup.getSelectedToggle();
			if (selectedToggle != null) {
				String gender = ((RadioButton) selectedToggle).getText();
				return String.valueOf(gender.charAt(0));
			} else {
				return null;
			}
		}, genderToggleGroup.selectedToggleProperty()));
	}

	private void clearLineChart() {
		this.lineChart.getData().clear();
	}
}
