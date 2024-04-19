package reservationSystem.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AdminLoginOverviewController {
	@FXML
	TextField nameTextField;
	@FXML
	TextField passwordTextField;
	
	/**
	 * Verify the name and password of administrator.
	 * @return Return true if the information is correct, otherwise return false.
	 * @throws SQLException
	 */

	private static ResourceBundle bundle = ResourceBundle.getBundle("reservationSystem/resources/admin");
	
	private boolean verifyAdmin() throws SQLException {
		String name = bundle.getString("name");
		String password = bundle.getString("password");
		
		if(nameTextField.getText().equals(name) && passwordTextField.getText().equals(password)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Called when the user clicks ok. It will verify the user's information first,
	 * if correct, switch to user main interface, if incorrect an alert dialog will pop up.
	 * @param event
	 * @throws SQLException
	 * @throws IOException
	 */
	public void handleLogin(ActionEvent event) throws SQLException, IOException {
		if(verifyAdmin()) {
			SwitchController.switchToMainAdmin(event);
		}
		else {
			 Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect or empty fields exist, please correct invalid fields.");
		     alert.setTitle("Login Unsuccessfully");
		     alert.showAndWait();
		}
	}
}
