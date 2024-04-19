package reservationSystem.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import reservationSystem.dao.UserDao;
import reservationSystem.model.User;
import reservationSystem.util.DbUtil;

public class UserLoginOverviewController {
	@FXML
	TextField typeTextField;
	@FXML
	TextField usernameTextField;
	
	static User user = new User();
	static Connection conn;
	
	static{
		try {
			conn = DbUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Verify the user's information.
	 * @return Return true if the user's information is correct, otherwise return false.
	 * @throws SQLException
	 */
	private boolean verifyUser() throws SQLException {
		String classOrSubject = typeTextField.getText();
		String name = usernameTextField.getText();
		
		user = new User(classOrSubject, name);
		
		boolean ok = UserDao.verifyUser(user);
		return ok;
	}
	
	/**
	 * Called when the user clicks login. It will verify the user's information first, if correct, 
	 * switch to the main user interface, otherwise an error dialog will pop up.
	 * @param event
	 * @throws SQLException
	 * @throws IOException
	 */
	public void handleLogin(ActionEvent event) throws SQLException, IOException {
		if(verifyUser()) {
			SwitchController.switchToMainUser(event, user);
		}
		else {
			 Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect or empty fields exist, please correct invalid fields.");
		     alert.setTitle("Login Unsuccessfully");
		     alert.showAndWait();
		}
	}
	

	
}
