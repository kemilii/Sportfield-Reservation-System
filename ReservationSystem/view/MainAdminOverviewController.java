package reservationSystem.view;

import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import reservationSystem.MainApp;
import reservationSystem.dao.RecordDao;
import reservationSystem.model.Record;

public class MainAdminOverviewController {
	@FXML
    private TableView<Record> reservationTable;
	@FXML
    private TableColumn<Record, String> classOrSubjectColumn;
    @FXML
    private TableColumn<Record, String> nameColumn;
    @FXML
    private TableColumn<Record, String> sportColumn;
    @FXML
    private TableColumn<Record, String> placeColumn;
    @FXML
    private TableColumn<Record, String> reserveDateColumn;
    @FXML
    private TableColumn<Record, String> reserveTimeColumn;
    
    /**
    * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
    * The table view will connect with the variables and be filled by reservation records selected.
    * @throws SQLException 
    */
    @FXML
    private void initialize() throws SQLException {
        // Initialize the table with the six columns.
        classOrSubjectColumn.setCellValueFactory(cellData -> cellData.getValue().classOrSubjectProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sportColumn.setCellValueFactory(cellData -> cellData.getValue().sportProperty());
        placeColumn.setCellValueFactory(cellData -> cellData.getValue().placeProperty());
        reserveDateColumn.setCellValueFactory(cellData -> cellData.getValue().reserveDateProperty());  
        reserveTimeColumn.setCellValueFactory(cellData -> cellData.getValue().reserveTimeProperty());
        
        //Generate and add observable list data to the table.
        ObservableList<Record> list = RecordDao.selectRecord();
        reservationTable.setItems(list);
    }
    
    /**
     * Called when the user clicks the delete button. There will be a alert if nothing is selected.
     * And before delete successfully, there will be a confirmation alert.
     * @throws SQLException 
     */
    @FXML
    private void handleDelete() throws SQLException {
        int selectedIndex = reservationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this record?");
        	Optional<ButtonType> btn = alert.showAndWait();
        	if (btn.get() == ButtonType.OK) {
            	//Delete from database.
            	Record selectedRecord = new Record();
            	selectedRecord = reservationTable.getSelectionModel().getSelectedItem();
            	RecordDao.deleteSelectedRecord(selectedRecord);
            	//Delete from tableView.
            	reservationTable.getItems().remove(selectedIndex);
        	}
        } else {
        	 Alert alert = new Alert(Alert.AlertType.WARNING, "Nothing is selected, please select one record before delete.");
		     alert.setTitle("Delete Unsuccessfully");
		     alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the edit button. A dialog for editing details
     *  for the selected record will be opened. And a warning dialog will pop up if nothing is selected.
     */
    @FXML
    private void handleEdit() {
    	 int selectedIndex = reservationTable.getSelectionModel().getSelectedIndex();
         if (selectedIndex >= 0) {
        	 Record selectedRecord = new Record();
         	 selectedRecord = reservationTable.getSelectionModel().getSelectedItem();
         	 MainApp.showAdminRecordEditDialog(selectedRecord);
         	 
         	 reservationTable.refresh(); //update the tableView
         } else {
         	 Alert alert = new Alert(Alert.AlertType.WARNING, "Nothing is selected, please select one record before edit.");
 		     alert.setTitle("Delete Unsuccessfully");
 		     alert.showAndWait();
         }
    }
}
