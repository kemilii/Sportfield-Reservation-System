package reservationSystem.view;

import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import reservationSystem.dao.RecordDao;
import reservationSystem.dao.SportfieldDao;
import reservationSystem.model.Record;
import reservationSystem.model.User;

public class UserNewReservationDialogController {
	@FXML
	private ChoiceBox<String> sportChoiceBox;
	@FXML
	private ChoiceBox<String> placeChoiceBox;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ChoiceBox<String> timeChoiceBox;
	@FXML
	Label lb = new Label();
	
	static String sport, place, date, time;
	static User user = UserLoginOverviewController.user;
	
	private Stage dialogStage;
	
    /**
    * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     * @throws SQLException 
     */
    @FXML
    private void initialize(){
    	showSportOptions();
    }
    
    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Set options for the sport choicebox and add a listener on it to change the options for places timely if sport changes and get the new sport value.
     */
    public void showSportOptions() {
    	//Add options
    	sportChoiceBox.setItems(FXCollections.observableArrayList("basketball", "volleyball", "badminton", "table tennis", "soccer"));
     	//Add listener
    	sportChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				sport = newValue;
				try {
					showAndListenPlaceOptions();
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    	});
    }
    
    /**
     * Set options for the place choicebox and add a listener on it so that the available time options and place can be updated timely.
     * Besides, the method for date listen will be called after the information of sport and place is got and listened.
     * @throws SQLException
     */
    public void showAndListenPlaceOptions() throws SQLException {
    	ObservableList<String> places = SportfieldDao.selectPlaces(sport);
    	//Add options
    	placeChoiceBox.setItems(places);
    	//Add listener
    	placeChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			place = newValue;
    			listenDate();
    			if(datePicker.getValue() != null) {
    				try {
						showAndListenAvailableTime();
					} catch (SQLException e) {
						e.printStackTrace();
					}
    			}
    			}
    	});
    }
    
    /**
     * Add a listener on the date so that the available time options can be updated timely and update the date.
     */
    public void listenDate() {
    	datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				date = newValue.format(df);
				try {
					showAndListenAvailableTime();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
    	});
    }
    
    /**
     * According to the values of sport, place and date, select available time periods and set them as options.
     * Besides, a listener is added to get the new time.
     * @throws SQLException
     */
    public void showAndListenAvailableTime() throws SQLException {
    	//Find the time periods those haven't been reserved
    	ObservableList<String> unavailableTime = RecordDao.selectUnavailableTime(sport, place, date);
    	ObservableList<String> availableTime = FXCollections.observableArrayList();
    	for(int i = 0; i <= 23; i++) {
    		String tempTime = "" + i;
    		if(!unavailableTime.contains(tempTime)) {
    			int endTime = i + 1;
    			tempTime = tempTime + ":00-" + endTime + ":00";
    			availableTime.add(tempTime);
    		}
    	}
    	//Add items
    	timeChoiceBox.setItems(availableTime);
    	//Add listener
    	timeChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			time = newValue;
    		}
    	});
    }
    
    /**
     * Called when the user clicks ok. It add the new record to the table view and database if the information is all filled,
     * if not, an error dialog will pop up.
     * @throws SQLException 
     */
    @FXML
    private void handleOk() throws SQLException{
    	if(sportChoiceBox.getValue() == null || placeChoiceBox.getValue() == null || datePicker.getValue() == null || timeChoiceBox.getValue() == null) {
    		// Show the error message.
    		String errorMessage = "There is empty field!";
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
    	}
    	else{
    		//update the tableView
    		Record record = new Record(user.getClassOrSubject(), user.getName(), sport, place, date, time);
    		user.getRecordList().add(record);
    		//update the database
    		RecordDao.addRecord(user.getClassOrSubject(), user.getName(), sport, place, date, time);
            dialogStage.close();
    	}
    }

    /**
     * Called when the user clicks cancel. It will close the dialog.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close(); 
    }
}
