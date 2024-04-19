package reservationSystem;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import reservationSystem.model.Record;
import reservationSystem.model.User;
import reservationSystem.view.AdminRecordEditDialogController;
import reservationSystem.view.UserNewReservationDialogController;
import reservationSystem.view.UserRecordEditDialogController;

public class MainApp extends Application {
	
	Stage primaryStage;
	
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Reservation System");

        initStartSelection();
    }
    
    /**
     * Initialises the start interface.
     */
    public void initStartSelection() {
        try {
        	//Load start overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StartOverview.fxml"));
            AnchorPane startSelction = (AnchorPane) loader.load();
            
            //Show the scene containing the root layout.
            Scene scene = new Scene(startSelction);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    /**
     * Opens a dialog to edit details for the specified record of the specific user.
     * @param selectedRecord
     */
    public static void showRecordEditDialog(Record selectedRecord) {
        try {
            //Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UserRecordEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            //Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Record");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            //Set the record into the controller.
            UserRecordEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.conductRecord(selectedRecord);
            controller.setRecord();
            
            //Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to enter details for a new reservation. 
     * @param user
     * @throws SQLException
     */
    public static void showNewReservationDialog(User user) throws SQLException {
        try {
            //Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UserNewReservationDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
             
            //Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            //Set the person into the controller.
            UserNewReservationDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            
            //Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified record.
     * @param selectedRecord
     */
    public static void showAdminRecordEditDialog(Record selectedRecord) {
        try {
            //Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AdminRecordEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            //Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Record");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            //Set the record into the controller.
            AdminRecordEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.conductRecord(selectedRecord);
            controller.setRecord();
            
            //Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

