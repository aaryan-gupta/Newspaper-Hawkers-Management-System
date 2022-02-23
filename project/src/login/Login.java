package login;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import files.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtUid;

    @FXML
    private PasswordField txtPwd;

    @FXML
    private Label lblCheck;
    
    Connection connection;
    PreparedStatement ps;

    @FXML
    void doSubmit(ActionEvent event) {
    	String uidString = txtUid.getText();
    	String pwdString = txtPwd.getText();
    	try {
			ps = connection.prepareStatement("select * from login where uid=? and pwd=?");
			ps.setString(1, uidString);
			ps.setString(2, pwdString);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				try {
					Parent rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/Dashboard.fxml"));
					Scene scene = new Scene(rootParent);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.show();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				txtUid.setText("");
				txtPwd.setText("");
				lblCheck.setText("");
			}
			else lblCheck.setText("INVALID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert txtUid != null : "fx:id=\"txtUid\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtPwd != null : "fx:id=\"txtPwd\" was not injected: check your FXML file 'Login.fxml'.";
        assert lblCheck != null : "fx:id=\"lblCheck\" was not injected: check your FXML file 'Login.fxml'.";
        connection = Connect.getConnection();
    }
}
