package reservationSystem.view;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import reservationSystem.dao.RecordDao;
import reservationSystem.dao.SportfieldDao;
import reservationSystem.model.Record;

public class UserRecordEditDialogController {
	@FXML
    private TextField sportTextField;
	@FXML
    private TextField placeTextField;
	@FXML
    private TextField dateTextField; 
	@FXML
    private TextField timeTextField;

	private Stage dialogStage;
    static Record oldRecord = new Record();
    static Record newRecord;  
    

//    @FXML
//    private void initialize() {
//    }
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * As the scene changes, the record's information will lost, this method is used to get the record's information.
     * @param selectedRecord
     */
    public void conductRecord(Record selectedRecord) {
    	newRecord = selectedRecord;
    	oldRecord.setSport(selectedRecord.getSport());
    	oldRecord.setPlace(selectedRecord.getPlace());
    	oldRecord.setReserveDate(selectedRecord.getReserveDate());
    	oldRecord.setReserveTime(selectedRecord.getReserveTime());
    }

    /**
     * Set the old values of reservation, so that they can be showed on the interface as default values.
     */
    public void setRecord() {
        sportTextField.setText(oldRecord.getSport());
        placeTextField.setText(oldRecord.getPlace());
        dateTextField.setText(oldRecord.getReserveDate());
        timeTextField.setText(oldRecord.getReserveTime());
    }

    /**
     * Called when the user clicks ok. It will validate the information of reservation user entried first,
     * if all valid, the reservation information will be updated on the table view and in the database.
     * @throws SQLException 
     */
    @FXML
    private void handleOk() throws SQLException {
        if (isInputValid()) {
        	//update the table view
        	newRecord.setSport(sportTextField.getText());
        	newRecord.setPlace(placeTextField.getText());
        	newRecord.setReserveDate(dateTextField.getText());
        	newRecord.setReserveTime(timeTextField.getText());
        	
        	//update the database
        	RecordDao.updateUserSelectedRecord(oldRecord, newRecord);
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
      
    /**
     * Validates the user input in the text fields, including validating the empty fields, 
     * user information, sportfield information, date or time format, length of time and 
     * whether the reservation has been made. If anything is incorrect, an alert dialog will
     * pop up and show where need to be correct.
     * 
     * @return true if the input is valid
     * @throws SQLException 
     */
    private boolean isInputValid() throws SQLException {
    	String sport = sportTextField.getText();
    	String place = placeTextField.getText();
    	String date = dateTextField.getText();
    	String time = timeTextField.getText();
    	String errorMessage = "";

    	if(sport == null || place == null || date == null || time == null) {
    		errorMessage += "There is empty field!";
    	}
    	else if(sport.trim().length() <= 0 || place.trim().length() <= 0 || date.trim().length() <= 0 || time.trim().length() <= 0) {
        	errorMessage += "There is empty field!";
        }
    	else{
    		if(!SportfieldDao.findSportfield(sport, place)) {
            	errorMessage += "There is incorrect sport or place!";
            }
            if(!isEntryPatternCorrect("\\d{4}\\-\\d{1,2}\\-\\d{1,2}", date)) {
            	errorMessage += "The date entry format is incorrect!";
            }
            if(!isEntryPatternCorrect("\\d{1,2}\\:00-\\d{1,2}\\:00", time)) {
            	errorMessage += "The time entry format is incorrect!";
            }
            else if(Integer.parseInt(time.substring(0, 2)) != (Integer.parseInt(time.substring(6, 8))-1)) {
                errorMessage += "One reservation lasts 1 hour!";
            }
    	}
        if (errorMessage.length() == 0) {
            if(RecordDao.findRecord(sport, place, date, time)) {
            	errorMessage += "This reservation is not available!";
            }
            else return true;
        } 
        // Show the error message.
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMessage);
        
        alert.showAndWait();
        return false;
        }
    
    /**
	 * Verify whether the entry (date or time) matches the format required.
	 * @param pattern
	 * @param str
	 * @return Return true if the entry matches the format required, otherwise return false.
	 */
	public static boolean isEntryPatternCorrect(String pattern, String str) {
		return str.matches(pattern);
	}
}

