package hawkersControlPanel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
import files.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.scene.input.MouseEvent;

public class Hawkers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtSalary;

    @FXML
    private ListView<String> listAreas;
    
    @FXML
    private ImageView preview;

    @FXML
    private Button btnChoosePic;

    @FXML
    private Label lblSave;

    @FXML
    private Label lblUpdate;

    @FXML
    private Label lblRemove;

    @FXML
    private ComboBox<String> comboHawkerName;

    @FXML
    private Label lblPicPath;

    @FXML
    private TextArea txtAddress;
    
    @FXML
    private Label lblSearch;
    
    @FXML
    private Label lblAreas;
    
    @FXML
    private ListView<String> listAreasShow;
    
    @FXML
    private Label lblError;
    
    Connection connection;
    PreparedStatement ps;

    @FXML
    void doBtnChoosePic(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	File file = fileChooser.showOpenDialog(null);
    	String lblString = file.getAbsolutePath();
    	lblPicPath.setText(lblString);
    	FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	Image image = new Image(fileInputStream);
    	preview.setImage(image);
    }
    
    @FXML
    void doBtnClear(ActionEvent event) {
    	comboHawkerName.getEditor().clear();
    	txtMobile.clear();
    	txtSalary.clear();
    	txtAddress.clear();
    	lblSearch.setText("");
    	lblPicPath.setText("");
    	lblSave.setText("");
    	lblUpdate.setText("");
    	lblRemove.setText("");
    	lblAreas.setText("");
    	preview.setImage(null);
    	listAreasShow.getItems().clear();
    	listAreas.getSelectionModel().clearSelection();
    	lblError.setText("");
    }

    @FXML
    void doBtnRemove(ActionEvent event) {
    	try {
    		if(comboHawkerName.getEditor().getText().length() == 0) lblError.setText("\"Name\" field is MANDATORY");
    		else {
				ps = connection.prepareStatement("delete from hawkers where name=?");
				ps.setString(1, comboHawkerName.getEditor().getText());
				ps.executeUpdate();
				lblRemove.setText("DELETED");
				lblError.setText("");
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	lblSave.setText("");
    	lblUpdate.setText("");
    }

    @FXML
    void doBtnSave(ActionEvent event) {
    	try {
//    		if((txtSalary.getText().length() == 0)||(comboHawkerName.getEditor().getText().length() == 0)||(txtMobile.getText().length() == 0)||(txtAddress.getText().length() == 0)||(lblPicPath.getText().length() == 0)) lblError.setText("All fields are MANDATORY");
//    		else {
    			System.out.println("-1");
    			listAreasShow.getItems().clear();
    			System.out.println("0");
				ps = connection.prepareStatement("insert into hawkers values (?, ?, ?, ?, ?, ?, curdate())");
//				System.out.println("1");
				lblSave.setText("SAVED1");
				ps.setString(1, comboHawkerName.getEditor().getText());
				ps.setString(2, txtMobile.getText());
				ps.setString(3, txtAddress.getText());
	//			ps.setString(4, .getText());
	//			ps.setString(4, "AREAS");
				lblSave.setText("2");
				ObservableList<String> list = listAreas.getSelectionModel().getSelectedItems();
				String allString = "";
				for(String string : list) allString = allString + string + ", ";
				lblAreas.setText(allString);
				ps.setString(4, allString);
				lblSave.setText("3");
				ps.setString(5, lblPicPath.getText());
	//			ps.setString(5, "PICS");
				ps.setInt(6, Integer.parseInt(txtSalary.getText()));
				String listAreaShowString[] = lblAreas.getText().split(", ");
				for(String a : listAreaShowString)
					listAreasShow.getItems().add(a);
				lblSave.setText("4");
				if(lblAreas.getText().length() == 0) {
					lblError.setText("Select the AREA");
					System.out.println("5if");
				}
				else {
					System.out.println("5");
					ps.executeUpdate();
					lblSave.setText("SAVED");
					lblError.setText("");
				}
//    		}
		} catch (Exception e) {
			e.printStackTrace();
			lblError.setText("DUPLICATE entry for \"" + comboHawkerName.getEditor().getText() + "\"");
		}
    	lblUpdate.setText("");
    	lblRemove.setText("");
    }

    @FXML
    void doBtnSearch(ActionEvent event) {
    	listAreasShow.getItems().clear();
    	String paperString = comboHawkerName.getEditor().getText();
    	try {
			ps = connection.prepareStatement("select * from hawkers where name=?");
			ps.setString(1, paperString);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				String paperNameString = resultSet.getString("name");
				comboHawkerName.getEditor().setText(paperNameString);
//				float price = resultSet.getFloat("price");
//				txtPrice.setText(String.valueOf(price));
				txtMobile.setText(resultSet.getString("mobile"));
				txtSalary.setText(String.valueOf(resultSet.getString("salary")));
//				listAreas.setText(resultSet.getString("mobile"));
				lblAreas.setText(resultSet.getString("areas"));
				txtAddress.setText(resultSet.getString("address"));
				lblPicPath.setText(resultSet.getString("aadhaarpic"));
				lblSearch.setText("");
				String listAreaShowString[] = resultSet.getString("areas").split(", ");
				for(String a : listAreaShowString) listAreasShow.getItems().add(a);
				try {
					FileInputStream fileInputStream = new FileInputStream(resultSet.getString("aadhaarpic"));
					Image image = new Image(fileInputStream);
					preview.setImage(image);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			else lblSearch.setText("INVALID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	lblUpdate.setText("");
    	lblRemove.setText("");
    }

    @FXML
    void doBtnUpdate(ActionEvent event) {
    	listAreasShow.getItems().clear();
    	String paperString = comboHawkerName.getEditor().getText();
    	try {
			ps = connection.prepareStatement("select * from hawkers where name=?");
			ps.setString(1, paperString);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
		    	try {
					ps = connection.prepareStatement("update hawkers set mobile=?, address=?, areas=?, aadhaarpic=?, salary=? where name=?");
		//			ps.setString(1, comboHawkerName.getEditor().getText());
					ps.setString(1, txtMobile.getText());
					ps.setString(2, txtAddress.getText());
		//			ps.setString(3, .getText());
		//			ps.setString(3, "AREAS");
					ObservableList<String> list = listAreas.getSelectionModel().getSelectedItems();
					String allString = "";
					for(String string : list) allString = allString + string + ", ";
					lblAreas.setText(allString);
					ps.setString(3, allString);
					ps.setString(4, lblPicPath.getText());
		//			ps.setString(4, "PICS");
					ps.setInt(5, Integer.parseInt(txtSalary.getText()));
					ps.setString(6, comboHawkerName.getEditor().getText());
					String listAreaShowString[] = lblAreas.getText().split(", ");
					for(String a : listAreaShowString) listAreasShow.getItems().add(a);
					ps.executeUpdate();
					lblUpdate.setText("UPDATED");
					lblError.setText("");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else lblUpdate.setText("INVALID");
    	}
    	catch (Exception e) {
    		e.printStackTrace();
		}
    	lblSave.setText("");
    	lblRemove.setText("");
    }

    @FXML
    void doComboHawkerName(MouseEvent event) {
    	ObservableList<String> observableList = FXCollections.observableArrayList();
    	try {
			ps = connection.prepareStatement("select * from hawkers");
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) observableList.add(resultSet.getString("name"));
			comboHawkerName.setItems(observableList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
    	assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert txtSalary != null : "fx:id=\"txtSalary\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert listAreas != null : "fx:id=\"listAreas\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert btnChoosePic != null : "fx:id=\"btnChoosePic\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert lblSave != null : "fx:id=\"lblSave\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert lblUpdate != null : "fx:id=\"lblUpdate\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert lblRemove != null : "fx:id=\"lblRemove\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert comboHawkerName != null : "fx:id=\"comboHawkerName\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert lblPicPath != null : "fx:id=\"lblPicPath\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert lblSearch != null : "fx:id=\"lblSearch\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert lblAreas != null : "fx:id=\"lblAreas\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert preview != null : "fx:id=\"preview\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert listAreasShow != null : "fx:id=\"listAreasShow\" was not injected: check your FXML file 'Hawkers.fxml'.";
        assert lblError != null : "fx:id=\"lblError\" was not injected: check your FXML file 'Hawkers.fxml'.";
        connection = Connect.getConnection();
        listAreas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listAreas.getItems().addAll("Ganesh Nagar", "Namdev Nagar", "Sarabha Nagar", "Nai Basti");
        lblSave.setText("STARTING");
    }
}