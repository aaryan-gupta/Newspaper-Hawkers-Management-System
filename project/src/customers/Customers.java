package customers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import files.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
//import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Customers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtMobile;

    @FXML
    private Label lblSearch;

    @FXML
    private TextArea listAddress;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private Label lblHawker;

    @FXML
    private ListView<String> listPaper;

    @FXML
    private ListView<String> listPrice;

    @FXML
    private Label lblClear;

    @FXML
    private Label lblSave;

    @FXML
    private Label lblUpdate;

    @FXML
    private Label lblRemove;

    @FXML
    private Label lblPapers;

    @FXML
    private Label lblError;
    
    Connection connection;
    PreparedStatement ps;
    String paperString = "", priceString = "";
    ObservableList<String> oList = FXCollections.observableArrayList();

    @FXML
    void doBtnClear(ActionEvent event) {
    	txtName.clear();
    	txtMobile.clear();
//    	lblClear.setText("");
    	lblError.setText("");
    	lblHawker.setText("");
    	lblPapers.setText("");
    	lblRemove.setText("");
    	lblSave.setText("");
    	lblSearch.setText("");
    	lblUpdate.setText("");
    	listAddress.clear();
    	listPaper.getSelectionModel().clearSelection();
    	listPrice.getItems().clear();
    	
    	try {
			ps = connection.prepareStatement("select * from papers");
			ResultSet resultSet = ps.executeQuery();
			ObservableList<String> oList = FXCollections.observableArrayList();
			while (resultSet.next()) oList.add(resultSet.getString("paper"));
			listPaper.setItems(oList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doBtnRemove(ActionEvent event) {
    	try {
			ps = connection.prepareStatement("delete from customers where mobile=?");
			ps.setString(1, txtMobile.getText());
			ps.executeUpdate();
			lblRemove.setText("DELETED");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doBtnSave(ActionEvent event) {
    	try {
			ps = connection.prepareStatement("insert into customers values (?, ?, ?, ?, ?, ?, ?, curdate())");
			ps.setString(1, txtName.getText());
			ps.setString(2, txtMobile.getText());
			ps.setString(3, listAddress.getText());
			ps.setString(4, comboArea.getSelectionModel().getSelectedItem());
			
			/*
			ObservableList<String> paperList = listPaper.getSelectionModel().getSelectedItems();
			String paperString = "";
			for (String string : paperList) paperString = paperString + string + ", ";
			lblPapers.setText(paperString);
			ps.setString(5, paperString);
			*/
			
			/*
			ObservableList<Integer> paperListIndex = listPaper.getSelectionModel().getSelectedIndices();
			String paperIndexString = "";
			for (Integer integer : paperListIndex) paperIndexString = paperIndexString + integer + ", ";
			
			ObservableList<Integer> priceListIndex = listPaper.getSelectionModel().getSelectedIndices();
			String priceIndexString = "";
			for (Integer integer : priceListIndex) priceIndexString = priceIndexString + integer + ", ";
			*/
			
			/*
			ObservableList<String> priceList = listPrice.getSelectionModel().getSelectedItems();
			String priceString = "";
			for (String string : priceList) priceString = priceString + string + ", ";
			*/
			ps.setString(5, paperString);
			ps.setString(6, priceString);
			ps.setString(7, lblHawker.getText());
			
			ps.executeUpdate();
			lblSave.setText("SAVED");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	lblSearch.setText("");
    	lblUpdate.setText("");
    	lblRemove.setText("");
    	lblError.setText("");
    }

    @FXML
    void doBtnSearch(ActionEvent event) {
    	try {
			ps = connection.prepareStatement("select * from customers where mobile=?");
			ps.setString(1, txtMobile.getText());
			ResultSet rSet = ps.executeQuery();
			if(rSet.next()) {
				txtName.setText(rSet.getString("name"));
				listAddress.setText(rSet.getString("address"));
				comboArea.getSelectionModel().select(rSet.getString("area"));
				lblPapers.setText(rSet.getString("papers"));
				lblHawker.setText(rSet.getString("hawker"));
			}
			else lblError.setText("INVALID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doBtnUpdate(ActionEvent event) {
    	try {
			ps = connection.prepareStatement("update customers set name=?, address=?, area=?, papers=?, prices=?, hawker=? where mobile=?");
			ps.setString(1, txtName.getText());
//			ps.setString(2, txtMobile.getText());
			ps.setString(2, listAddress.getText());
			ps.setString(3, comboArea.getSelectionModel().getSelectedItem());
			ps.setString(4, paperString);
			ps.setString(5, priceString);
			ps.setString(6, lblHawker.getText());
			ps.setString(7, txtMobile.getText());
			ps.executeUpdate();
			lblSave.setText("UPDATED");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	lblSearch.setText("");
    	lblSave.setText("");
    	lblRemove.setText("");
    	lblError.setText("");
    }
    
    @FXML
    void doComboArea(ActionEvent event) {
    	try {
			ps = connection.prepareStatement("select * from hawkers");
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				if(resultSet.getString("areas").contains(comboArea.getSelectionModel().getSelectedItem())) {
//					System.out.println(comboArea.getSelectionModel().getSelectedItem() + "\n" + resultSet.getString("areas"));
					lblHawker.setText(resultSet.getString("name"));
					break;
				}
				else lblHawker.setText("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void doListPaper(MouseEvent event) {
    	try {
			ps = connection.prepareStatement("select * from papers where paper=?");
			ps.setString(1, listPaper.getSelectionModel().getSelectedItem());
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				oList.add(resultSet.getString("price"));
				paperString = paperString + listPaper.getSelectionModel().getSelectedItem() + ", ";
				priceString = priceString + resultSet.getString("price") + ", ";
				listPaper.getItems().remove(listPaper.getSelectionModel().getSelectedItem());
			}
			/*
			String totalString = paperString + priceString;
			lblPapers.setText(totalString);
			*/
			lblPapers.setText(paperString);
			listPrice.setItems(oList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void doClearPapers(ActionEvent event) {
    	paperString = "";
    	listPaper.getSelectionModel().clearSelection();
    	listPrice.getItems().clear();
    	lblPapers.setText("");
    	try {
			ps = connection.prepareStatement("select * from papers");
			ResultSet resultSet = ps.executeQuery();
			ObservableList<String> oList = FXCollections.observableArrayList();
			while (resultSet.next()) oList.add(resultSet.getString("paper"));
			listPaper.setItems(oList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
    	assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'Customers.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'Customers.fxml'.";
        assert lblSearch != null : "fx:id=\"lblSearch\" was not injected: check your FXML file 'Customers.fxml'.";
        assert listAddress != null : "fx:id=\"listAddress\" was not injected: check your FXML file 'Customers.fxml'.";
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'Customers.fxml'.";
        assert lblHawker != null : "fx:id=\"lblHawker\" was not injected: check your FXML file 'Customers.fxml'.";
        assert listPaper != null : "fx:id=\"listPaper\" was not injected: check your FXML file 'Customers.fxml'.";
        assert listPrice != null : "fx:id=\"listPrice\" was not injected: check your FXML file 'Customers.fxml'.";
        assert lblClear != null : "fx:id=\"lblClear\" was not injected: check your FXML file 'Customers.fxml'.";
        assert lblSave != null : "fx:id=\"lblSave\" was not injected: check your FXML file 'Customers.fxml'.";
        assert lblUpdate != null : "fx:id=\"lblUpdate\" was not injected: check your FXML file 'Customers.fxml'.";
        assert lblRemove != null : "fx:id=\"lblRemove\" was not injected: check your FXML file 'Customers.fxml'.";
        assert lblPapers != null : "fx:id=\"lblPapers\" was not injected: check your FXML file 'Customers.fxml'.";
        assert lblError != null : "fx:id=\"lblError\" was not injected: check your FXML file 'Customers.fxml'.";
        connection = Connect.getConnection();
        comboArea.getItems().addAll("Ganesh Nagar", "Namdev Nagar", "Sarabha Nagar", "Nai Basti");
//        listPaper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
			ps = connection.prepareStatement("select * from papers");
			ResultSet resultSet = ps.executeQuery();
			ObservableList<String> oList = FXCollections.observableArrayList();
			while (resultSet.next()) oList.add(resultSet.getString("paper"));
			listPaper.setItems(oList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        /*
        try {
			ps = connection.prepareStatement("select * from papers");
			ResultSet resultSet = ps.executeQuery();
			ObservableList<String> oList = FXCollections.observableArrayList();
			while (resultSet.next()) oList.add(resultSet.getString("price"));
			listPrice.setItems(oList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
    }
}