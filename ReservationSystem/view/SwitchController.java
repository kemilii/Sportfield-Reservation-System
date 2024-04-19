package reservationSystem.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import reservationSystem.MainApp;
import reservationSystem.model.User;

public class SwitchController {

	static Stage stage;
	

    /**
     * Switch to User login interface.
     * @param event
     */
    public void switchToUserLogin(ActionEvent event) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UserLoginOverview.fxml"));
            AnchorPane startSelction = (AnchorPane) loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();//get stage

            Scene scene = new Scene(startSelction);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Switch to the main user interface.
     * @param event
     * @param user
     */
    public static void switchToMainUser(ActionEvent event, User user){
        try {
        	// Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainUserOverview.fxml"));
            AnchorPane startSelction = (AnchorPane) loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();//get stage

            Scene scene = new Scene(startSelction);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Switch to administrator's login interface.
     * @param event
     */
    public void switchToAdminLogin(ActionEvent event) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AdminLoginOverview.fxml"));
            AnchorPane startSelction = (AnchorPane) loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();//get stage

            Scene scene = new Scene(startSelction);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to the main administrator interface.
     * @param event
     */
    public static void switchToMainAdmin(ActionEvent event){
        try {
        	// Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainAdminOverview.fxml"));
            AnchorPane startSelction = (AnchorPane) loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();//get stage

            Scene scene = new Scene(startSelction);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
