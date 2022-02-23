package records;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Records {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void doCustomerRecords(ActionEvent event) {
    	try {
			Parent rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("customersDisplayBoard/CustomerDisplayBoard.fxml"));
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doHawkerRecords(ActionEvent event) {
    	try {
			Parent rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("hawkersDisplayBoard/HawkersDisplayBoard.fxml"));
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {

    }
}