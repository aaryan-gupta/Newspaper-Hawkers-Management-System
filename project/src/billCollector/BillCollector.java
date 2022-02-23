package billCollector;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BillCollector {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtFetchBill;

    @FXML
    private ListView<String> listDateOfBilling;

    @FXML
    private ListView<Float> listBillAmount;

    @FXML
    private Label lblTotalBill;
    
    @FXML
    private Label lblBillPaid;
    
    Connection connection;
    PreparedStatement ps;

    @FXML
    void doBillPaid(ActionEvent event) {
    	String mobileString = txtFetchBill.getText();
    	try {
			ps = connection.prepareStatement("update billing set status=1 where status=0 and cust_mobile=?");
//			ps.setInt(1, 1);
			ps.setString(1, mobileString);
			ps.executeUpdate();
//			System.out.println("DONE");
			lblBillPaid.setText("DONE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void doFetchBill(ActionEvent event) {
    	listDateOfBilling.getItems().clear();
    	listBillAmount.getItems().clear();
    	float total=0;
    	String mobileString = txtFetchBill.getText();
    	ObservableList<String> oList = FXCollections.observableArrayList();
    	ObservableList<Float> oListF = FXCollections.observableArrayList();
    	try {
			ps = connection.prepareStatement("select distinct date_of_billing from billing where status=0 and cust_mobile=?");
			ps.setString(1, mobileString);
			ResultSet rSet = ps.executeQuery();
			while(rSet.next()) oList.add(rSet.getString("date_of_billing"));
			listDateOfBilling.getItems().addAll(oList);
			ps = connection.prepareStatement("select distinct amount from billing where status=0 and cust_mobile=?");
			ps.setString(1, mobileString);
			rSet = ps.executeQuery();
			while(rSet.next()) oListF.add(rSet.getFloat("amount"));
			for(int i=0;i<oListF.size();i++) total += oListF.get(i);
			lblTotalBill.setText(String.valueOf(total));
			listBillAmount.getItems().addAll(oListF);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    @FXML
    void initialize() {
        assert txtFetchBill != null : "fx:id=\"txtFetchBill\" was not injected: check your FXML file 'BillCollector.fxml'.";
        assert listDateOfBilling != null : "fx:id=\"listDateOfBilling\" was not injected: check your FXML file 'BillCollector.fxml'.";
        assert listBillAmount != null : "fx:id=\"listBillAmount\" was not injected: check your FXML file 'BillCollector.fxml'.";
        assert lblTotalBill != null : "fx:id=\"lblTotalBill\" was not injected: check your FXML file 'BillCollector.fxml'.";
        assert lblBillPaid != null : "fx:id=\"lblBillPaid\" was not injected: check your FXML file 'BillCollector.fxml'.";
        connection = Connect.getConnection();
    }
}