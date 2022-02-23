package paperMaster;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import files.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class PaperMaster {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboPaperTitle;

    @FXML
    private TextField txtPrice;

    @FXML
    private Label lblSave;

    @FXML
    private Label lblUpdate;

    @FXML
    private Label lblRemove;

    @FXML
    private Label lblFetch;
    
    @FXML
    private Label lblError;
    
    Connection connection;
    PreparedStatement ps;

    @FXML
    void doBtnClear(ActionEvent event) {
    	comboPaperTitle.getEditor().clear();
    	txtPrice.clear();
    	lblFetch.setText("");
    	lblSave.setText("");
    	lblUpdate.setText("");
    	lblRemove.setText("");
    	lblError.setText("");
    }
    
    @FXML
    void doBtnFetch(ActionEvent event) {
    	String paperString = comboPaperTitle.getEditor().getText();
    	try {
    		if(comboPaperTitle.getEditor().getText().length() == 0) lblError.setText("\"Paper\" field is MANDATORY");
    		else {
				ps = connection.prepareStatement("select * from papers where paper=?");
				ps.setString(1, paperString);
				ResultSet resultSet = ps.executeQuery();
				if(resultSet.next()) {
					String paperNameString = resultSet.getString("paper");
					comboPaperTitle.getEditor().setText(paperNameString);
					float price = resultSet.getFloat("price");
					txtPrice.setText(String.valueOf(price));
					lblFetch.setText("");
					lblError.setText("");
				}
				else {
					lblFetch.setText("INVALID");
					lblError.setText("");
				}
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doBtnRemove(ActionEvent event) {
    	try {
    		if(comboPaperTitle.getEditor().getText().length() == 0) lblError.setText("\"Paper\" field is MANDATORY");
    		else {
				ps = connection.prepareStatement("delete from papers where paper=?");
				ps.setString(1, comboPaperTitle.getEditor().getText());
				ps.executeUpdate();
				lblRemove.setText("DELETED");
				lblError.setText("");
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	lblSave.setText("");
    	lblUpdate.setText("");
    	lblFetch.setText("");
    }

    @FXML
    void doBtnSave(ActionEvent event) {
    	try {
			if((comboPaperTitle.getEditor().getText().length() == 0) || (txtPrice.getText().length() == 0)) lblError.setText("All fields are MANDATORY");
			else {
				ps = connection.prepareStatement("insert into papers values (?, ?)");
				ps.setString(1, comboPaperTitle.getEditor().getText());
				ps.setFloat(2, Float.parseFloat(txtPrice.getText()));
				ps.executeUpdate();
				lblSave.setText("SAVED");
				lblError.setText("");
			}
		} catch (SQLException e) {
			lblError.setText("DUPLICATE entry for \"" + comboPaperTitle.getEditor().getText() + "\"");
		}
    	lblUpdate.setText("");
    	lblRemove.setText("");
    	lblFetch.setText("");
    }

    @FXML
    void doBtnUpdate(ActionEvent event) {
    	try {
    		if((comboPaperTitle.getEditor().getText().length() == 0) || (txtPrice.getText().length() == 0)) lblError.setText("All fields are MANDATORY");
    		else {
				ps = connection.prepareStatement("update papers set price=? where paper=?");
				ps.setString(2, comboPaperTitle.getEditor().getText());
				ps.setFloat(1, Float.parseFloat(txtPrice.getText()));
				ps.executeUpdate();
				lblUpdate.setText("UPDATED");
				lblError.setText("");
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	lblSave.setText("");
    	lblRemove.setText("");
    	lblFetch.setText("");
    }

    @FXML
    void doComboPaperTitle(MouseEvent event) {
    	ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
			ps = connection.prepareStatement("select * from papers");
			ResultSet resultSet = ps.executeQuery();
	        while(resultSet.next()) observableList.add(resultSet.getString("paper"));
	        comboPaperTitle.setItems(observableList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
    	assert comboPaperTitle != null : "fx:id=\"comboPaperTitle\" was not injected: check your FXML file 'PaperMaster.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'PaperMaster.fxml'.";
        assert lblSave != null : "fx:id=\"lblSave\" was not injected: check your FXML file 'PaperMaster.fxml'.";
        assert lblUpdate != null : "fx:id=\"lblUpdate\" was not injected: check your FXML file 'PaperMaster.fxml'.";
        assert lblRemove != null : "fx:id=\"lblRemove\" was not injected: check your FXML file 'PaperMaster.fxml'.";
        assert lblFetch != null : "fx:id=\"lblFetch\" was not injected: check your FXML file 'PaperMaster.fxml'.";
        assert lblError != null : "fx:id=\"lblError\" was not injected: check your FXML file 'PaperMaster.fxml'.";
        connection = Connect.getConnection();
    }
}