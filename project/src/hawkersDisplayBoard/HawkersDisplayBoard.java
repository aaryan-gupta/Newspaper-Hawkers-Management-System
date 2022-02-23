package hawkersDisplayBoard;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

//import customersDisplayBoard.UserBeanCustomers;
import files.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HawkersDisplayBoard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    Connection connection;
    PreparedStatement ps;

    @FXML
    private TableView<UserBeanHawkers> tblFetchRecords;

    @FXML
    void doFetchRecords(ActionEvent event) {
    	TableColumn<UserBeanHawkers, String> nameColumn = new TableColumn<UserBeanHawkers, String>("Name");
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	nameColumn.setMinWidth(100);
    	TableColumn<UserBeanHawkers, String> mobileColumn = new TableColumn<UserBeanHawkers, String>("Mobile");
    	mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobileColumn.setMinWidth(100);
    	TableColumn<UserBeanHawkers, String> AddressColumn = new TableColumn<UserBeanHawkers, String>("Address");
    	AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    	AddressColumn.setMinWidth(100);
    	TableColumn<UserBeanHawkers, String> areasColumn = new TableColumn<UserBeanHawkers, String>("Areas");
    	areasColumn.setCellValueFactory(new PropertyValueFactory<>("areas"));
    	areasColumn.setMinWidth(100);
    	TableColumn<UserBeanHawkers, Float> salaryColumn = new TableColumn<UserBeanHawkers, Float>("Salary");
    	salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
    	salaryColumn.setMinWidth(100);
    	TableColumn<UserBeanHawkers, Date> dateColumn = new TableColumn<UserBeanHawkers, Date>("Date of Joining");
    	dateColumn.setCellValueFactory(new PropertyValueFactory<>("doj"));
    	dateColumn.setMinWidth(100);
    	getFetchRecords();
    	tblFetchRecords.getColumns().clear();
    	tblFetchRecords.getColumns().addAll(nameColumn, mobileColumn, AddressColumn, areasColumn, salaryColumn, dateColumn);
    	ObservableList<UserBeanHawkers> list = getFetchRecords();
    	tblFetchRecords.setItems(list);
    }
    
    ObservableList<UserBeanHawkers> getFetchRecords() {
    	ObservableList<UserBeanHawkers> list = FXCollections.observableArrayList();
    	try {
			ps = connection.prepareStatement("select * from hawkers");
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				String nameString = resultSet.getString("name");
				String mobileString = resultSet.getString("mobile");
				String addressString = resultSet.getString("address");
				String areasString = resultSet.getString("areas");
				float salary = resultSet.getFloat("salary");
				Date doj = resultSet.getDate("doj");
				UserBeanHawkers bean = new UserBeanHawkers(nameString, mobileString, addressString, areasString, salary, doj);
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return list;
    }

    @FXML
    void initialize() {
        assert tblFetchRecords != null : "fx:id=\"tblFetchRecords\" was not injected: check your FXML file 'HawkersDisplayBoard.fxml'.";
        connection = Connect.getConnection();
    }
}