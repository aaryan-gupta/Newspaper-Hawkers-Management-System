package customersDisplayBoard;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerDisplayBoard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboAreas;

    @FXML
    private ComboBox<String> comboPapers;

    @FXML
    private TableView<UserBeanCustomers> tblShow;
    
    Connection connection;
    PreparedStatement ps;

    @FXML
    void doFetchData(ActionEvent event) {
    	tableContents();
    	ObservableList<UserBeanCustomers> list = getFetchData();
    	tblShow.setItems(list);
    }

    @FXML
    void doFetchRecords(ActionEvent event) {
    	tableContents();
    	ObservableList<UserBeanCustomers> list = getFetchRecords();
    	tblShow.setItems(list);
    }
    
    @FXML
    void doShowAll(ActionEvent event) {
    	tableContents();
    	ObservableList<UserBeanCustomers> list = getShowAll();
    	tblShow.setItems(list);
    }
    
    void tableContents() {
    	TableColumn<UserBeanCustomers, String> nameColumn = new TableColumn<UserBeanCustomers, String>("Name");
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	nameColumn.setMinWidth(100);
    	TableColumn<UserBeanCustomers, String> mobileColumn = new TableColumn<UserBeanCustomers, String>("Mobile");
    	mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobileColumn.setMinWidth(100);
    	TableColumn<UserBeanCustomers, String> AddressColumn = new TableColumn<UserBeanCustomers, String>("Address");
    	AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    	AddressColumn.setMinWidth(100);
    	TableColumn<UserBeanCustomers, String> areaColumn = new TableColumn<UserBeanCustomers, String>("Area");
    	areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
    	areaColumn.setMinWidth(100);
    	TableColumn<UserBeanCustomers, String> papersColumn = new TableColumn<UserBeanCustomers, String>("Papers");
    	papersColumn.setCellValueFactory(new PropertyValueFactory<>("papers"));
    	papersColumn.setMinWidth(100);
    	
    	tblShow.getColumns().clear();
    	tblShow.getColumns().addAll(nameColumn, mobileColumn, AddressColumn, areaColumn, papersColumn);
    }
    
    ObservableList<UserBeanCustomers> getFetchRecords() {
    	ObservableList<UserBeanCustomers> list = FXCollections.observableArrayList();
    	String comboAreaString = comboAreas.getSelectionModel().getSelectedItem();
    	try {
			ps = connection.prepareStatement("select * from customers where area=?");
			ps.setString(1, comboAreaString);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				String nameString = set.getString("name");
				String mobileString = set.getString("mobile");
				String addressString = set.getString("address");
				String areaString = set.getString("area");
				String papersString = set.getString("papers");
				UserBeanCustomers bean = new UserBeanCustomers(nameString, mobileString, addressString, areaString, papersString);
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return list;
    }
    
    ObservableList<UserBeanCustomers> getFetchData() {
    	ObservableList<UserBeanCustomers> list = FXCollections.observableArrayList();
    	String comboPapersString = comboPapers.getSelectionModel().getSelectedItem();
//    	System.out.println(comboPapersString);
    	try {
			ps = connection.prepareStatement("select * from customers");
//			ps.setString(1, comboPapersString);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				if(set.getString("papers").contains(comboPapersString)) {
					String nameString = set.getString("name");
					String mobileString = set.getString("mobile");
					String addressString = set.getString("address");
					String areaString = set.getString("area");
					String papersString = set.getString("papers");
					UserBeanCustomers bean = new UserBeanCustomers(nameString, mobileString, addressString, areaString, papersString);
					list.add(bean);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return list;
    }
    
    ObservableList<UserBeanCustomers> getShowAll() {
    	ObservableList<UserBeanCustomers> list = FXCollections.observableArrayList();
//    	String comboPapersString = comboPapers.getSelectionModel().getSelectedItem();
    	try {
			ps = connection.prepareStatement("select * from customers");
//			ps.setString(1, comboPapersString);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
//				if(set.getString("papers").contains(comboPapersString)) {
					String nameString = set.getString("name");
					String mobileString = set.getString("mobile");
					String addressString = set.getString("address");
					String areaString = set.getString("area");
					String papersString = set.getString("papers");
					UserBeanCustomers bean = new UserBeanCustomers(nameString, mobileString, addressString, areaString, papersString);
					list.add(bean);
//				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return list;
    }

    @FXML
    void initialize() {
        assert comboAreas != null : "fx:id=\"comboAreas\" was not injected: check your FXML file 'DisplayBoard.fxml'.";
        assert comboPapers != null : "fx:id=\"comboPapers\" was not injected: check your FXML file 'DisplayBoard.fxml'.";
        assert tblShow != null : "fx:id=\"tblShow\" was not injected: check your FXML file 'DisplayBoard.fxml'.";
        connection = Connect.getConnection();
        ObservableList<String> areasList = FXCollections.observableArrayList();
        try {
			ps = connection.prepareStatement("select distinct area from customers");
			ResultSet set = ps.executeQuery();
			while(set.next()) areasList.add(set.getString("area"));
			comboAreas.setItems(areasList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        ObservableList<String> papersList = FXCollections.observableArrayList();
        try {
			ps = connection.prepareStatement("select distinct paper from papers");
			ResultSet set = ps.executeQuery();
			while(set.next()) papersList.add(set.getString("paper"));
			comboPapers.setItems(papersList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}