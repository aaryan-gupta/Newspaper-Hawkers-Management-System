package billHistory;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import files.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
//import tableView.UserBean;

public class BillHistory {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton radioPaid;

    @FXML
    private ToggleGroup radio;

    @FXML
    private RadioButton radioUnpaid;

    @FXML
    private TableView<UserBeanBillHistory> tblFetchAllRecords;

    @FXML
    private TextField txtTotalAmount;

    Connection connection;
    PreparedStatement ps;
    
    @FXML
    void doFetchAllRecords(ActionEvent event) {
    	
    	TableColumn<UserBeanBillHistory, String> mobileColumn = new TableColumn<UserBeanBillHistory, String>("Mobile Number");
    	mobileColumn.setCellValueFactory(new PropertyValueFactory<>("cust_mobile"));
    	mobileColumn.setMinWidth(50);
    	
    	TableColumn<UserBeanBillHistory, Integer> daysColumn = new TableColumn<UserBeanBillHistory, Integer>("No of Days");
    	daysColumn.setCellValueFactory(new PropertyValueFactory<>("noofdays"));
    	daysColumn.setMinWidth(50);
    	
    	TableColumn<UserBeanBillHistory, Date> dateColumn = new TableColumn<UserBeanBillHistory, Date>("Date of Billing");
    	dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_of_billing"));
    	dateColumn.setMinWidth(50);
    	
    	TableColumn<UserBeanBillHistory, Float> amountColumn = new TableColumn<UserBeanBillHistory, Float>("Amount");
    	amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    	amountColumn.setMinWidth(50);
    	
    	tblFetchAllRecords.getColumns().clear();
    	tblFetchAllRecords.getColumns().addAll(mobileColumn, daysColumn, dateColumn, amountColumn);
    	ObservableList<UserBeanBillHistory> list = getAllRecords();
    	tblFetchAllRecords.setItems(list);
    	
    	
    }
    
    ObservableList<UserBeanBillHistory> getAllRecords() {
    	ObservableList<UserBeanBillHistory> list = FXCollections.observableArrayList();
    	String mobileString = "";
		int days = 0;
		Date date = null;
		float amount = 0;
    	if(radioPaid.isSelected()) {
    		try {
				ps = connection.prepareStatement("select * from billing where status=1");
				ResultSet resultSet = ps.executeQuery();
				
				while(resultSet.next()) {
					if((mobileString == resultSet.getString("cust_mobile")) && (date == resultSet.getDate("date_of_billing")) && (amount == resultSet.getFloat("amount"))) continue;
					else {
						mobileString = resultSet.getString("cust_mobile");
						days = resultSet.getInt("noofdays");
						date = resultSet.getDate("date_of_billing");
						amount = resultSet.getFloat("amount");
						UserBeanBillHistory history = new UserBeanBillHistory(mobileString, days, date, amount);
						list.add(history);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	else if(radioUnpaid.isSelected()) {
    		try {
				ps = connection.prepareStatement("select * from billing where status=0");
				ResultSet resultSet = ps.executeQuery();
				while(resultSet.next()) {
					mobileString = resultSet.getString("cust_mobile");
					days = resultSet.getInt("noofdays");
					date = resultSet.getDate("date_of_billing");
					amount = resultSet.getFloat("amount");
					UserBeanBillHistory history = new UserBeanBillHistory(mobileString, days, date, amount);
					list.add(history);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return list;
    }

    @FXML
    void initialize() {
        assert radioPaid != null : "fx:id=\"radioPaid\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert radio != null : "fx:id=\"radio\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert radioUnpaid != null : "fx:id=\"radioUnpaid\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert tblFetchAllRecords != null : "fx:id=\"tblFetchAllRecords\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert txtTotalAmount != null : "fx:id=\"txtTotalAmount\" was not injected: check your FXML file 'BillHistory.fxml'.";
        connection = Connect.getConnection();
    }
}
