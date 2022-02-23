package dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Dashboard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void doBillCollector(MouseEvent event) {
    	try {
			Parent rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("billCollector/BillCollector.fxml"));
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doBillGenerator(MouseEvent event) {
    	try {
			Parent rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("billGenerator/BillGenerator.fxml"));
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doCustomers(MouseEvent event) {
    	try {
			Parent rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("customers/Customers.fxml"));
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doHawkers(MouseEvent event) {
    	try {
			Parent rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("hawkersControlPanel/Hawkers.fxml"));
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doPapers(MouseEvent event) {
    	try {
			Parent rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("paperMaster/PaperMaster.fxml"));
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doRecords(MouseEvent event) {
    	try {
			Parent rootParent = FXMLLoader.load(getClass().getClassLoader().getResource("records/Records.fxml"));
			Scene scene = new Scene(rootParent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void doLogout(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void initialize() {
    	
    }
}
