package reservationSystem.view;

import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonType;
import reservationSystem.MainApp;
import reservationSystem.dao.RecordDao;
import reservationSystem.model.Record;
import reservationSystem.model.User;

public class MainUserOverviewController {
    @FXML
    private TableView<Record> previousReservationTable;
    @FXML
    private TableColumn<Record, String> sportColumn;
    @FXML
    private TableColumn<Record, String> placeColumn;
    @FXML
    private TableColumn<Record, String> reserveDateColumn;
    @FXML
    private TableColumn<Record, String> reserveTimeColumn;
    
    static User user = UserLoginOverviewController.user;
    
    /**
    * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
    * The table view will connect with the variables and be filled by reservation records of the specific user selected.
     * @throws SQLException 
     */
    @FXML
    private void initialize() throws SQLException {
        // Initialize the table with the four columns.
        sportColumn.setCellValueFactory(cellData -> cellData.getValue().sportProperty());
        placeColumn.setCellValueFactory(cellData -> cellData.getValue().placeProperty());
        reserveDateColumn.setCellValueFactory(cellData -> cellData.getValue().reserveDateProperty());  
        reserveTimeColumn.setCellValueFactory(cellData -> cellData.getValue().reserveTimeProperty());
        
        //Generate and add observable list data to the table.
		RecordDao.selectSpecificUserRecord(user);
        previousReservationTable.setItems(user.getRecordList());
    }
    
    /**
     * Called when the user clicks the delete button. There will be a alert if nothing is selected.
     * And before delete successfully, there will be a confirmation alert.
     * @throws SQLException 
     */
    @FXML
    private void handleDelete() throws SQLException {
        int selectedIndex = previousReservationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this record?");
        	Optional<ButtonType> btn = alert.showAndWait();
        	if (btn.get() == ButtonType.OK) {
            	//Delete from database.
            	Record selectedRecord = new Record();
            	selectedRecord = previousReservationTable.getSelectionModel().getSelectedItem();
            	RecordDao.deleteSelectedRecord(selectedRecord);
            	//Delete from tableView.
            	previousReservationTable.getItems().remove(selectedIndex);
        	}
        } else {
        	 Alert alert = new Alert(Alert.AlertType.WARNING, "Nothing is selected, please select one record before delete.");
		     alert.setTitle("Delete Unsuccessfully");
		     alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the edit button. Opens a dialog to edit details 
     * for the selected record. And a warning dialog will pop up if nothing is selected.
     */
    @FXML
    private void handleEdit() {
    	 int selectedIndex = previousReservationTable.getSelectionModel().getSelectedIndex();
         if (selectedIndex >= 0) {
        	 Record selectedRecord = new Record();
         	 selectedRecord = previousReservationTable.getSelectionModel().getSelectedItem();
         	 MainApp.showRecordEditDialog(selectedRecord);
         } else {
         	 Alert alert = new Alert(Alert.AlertType.WARNING, "Nothing is selected, please select one record before edit.");
 		     alert.setTitle("Delete Unsuccessfully");
 		     alert.showAndWait();
         }
    }
    
	  /**
	   * Called when the user clicks the new button. And a dialog for editing the
	   * reservation information will be opened.
	   * @throws SQLException
	   */
	 @FXML
	 private void handleNew() throws SQLException {
	 	 MainApp.showNewReservationDialog(user);
	 }
	 
	

}
