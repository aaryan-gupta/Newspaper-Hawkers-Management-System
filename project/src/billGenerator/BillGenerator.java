package billGenerator;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import files.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sms.*;

public class BillGenerator {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMobile;

    @FXML
    private ListView<String> listPapers;

    @FXML
    private ListView<String> listPrices;

    @FXML
    private Label lblDays;

    @FXML
    private Label lblGetDetails;
    
    @FXML
    private Label lblTotalBill;
    
    @FXML
    private Label lblDone;
    
    Connection connection;
    PreparedStatement ps;
    String prices[] = null;

    @FXML
    void doGetDetails(ActionEvent event) {
    	listPapers.getItems().clear();
    	listPrices.getItems().clear();
    	String mobileString = comboMobile.getEditor().getText();
    	try {
			ps = connection.prepareStatement("select * from customers where mobile=?");
			ps.setString(1, mobileString);
			ResultSet rSet = ps.executeQuery();
			if(rSet.next()) {
				String paperString[] = rSet.getString("papers").split(", ");
				for (String string : paperString) listPapers.getItems().add(string);
				prices = rSet.getString("prices").split(", ");
				for (String string : prices) {
					listPrices.getItems().add(string);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doPrepareBill(ActionEvent event) {
    	String mobileString = comboMobile.getEditor().getText();
    	try {
    		/* ps = connection.prepareStatement("select datediff(curdate(), dos) as diff from customers where mobile=?");
    		ps.setString(1, mobileString);
    		ResultSet rSet = ps.executeQuery();
    		int d = 0;
    		if(rSet.next()) d = rSet.getInt(d);
    		lblDays.setText(String.valueOf(d)); */
    		
    		/* Date billStartMorning = Date.valueOf(Data.dos.toLocalDate().atStartOfDay().toLocalDate());
        	Date todayEvening = Date.valueOf(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate());
    		Long days= (todayEvening.getTime()-billStartMorning.getTime())/86400000;
    		System.out.println(days);
    		date(); */
    		
			ps = connection.prepareStatement("select * from customers where mobile=?");
			ps.setString(1, mobileString);
			ResultSet rSet = ps.executeQuery();
//			Date blilMorningDate = Date.valueOf(data)
			if(rSet.next()) {
				int total = 0;
		    	for(int i=0;i<prices.length;i++) total += Integer.parseInt(prices[i]);
		    	
		    	Date date = rSet.getDate("dos");
		    	
		    	Date billStartMorning = Date.valueOf(date.toLocalDate().atStartOfDay().toLocalDate());
	        	Date todayEvening = Date.valueOf(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate());
	    		Long days= (todayEvening.getTime()-billStartMorning.getTime())/86400000;
	    		/* System.out.println(days);
	    		date(); */
	    		lblDays.setText(String.valueOf(days));
		    	lblTotalBill.setText(String.valueOf(total*days));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /* void date() {
    	String mobileString = comboMobile.getEditor().getText();
    	try {
    		ps = connection.prepareStatement("select curdate()");
    		System.out.println(ps.executeQuery());
			ps = connection.prepareStatement("select * from customers where mobile=?");
			ps.setString(1, mobileString);
			ResultSet rSet = ps.executeQuery();
			if(rSet.next()) {
				String dateString = String.valueOf(rSet.getString("dos"));
				System.out.println(dateString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    } */

    @FXML
    void doSaveAndSendSMS(ActionEvent event) {
    	try {
			ps = connection.prepareStatement("insert into billing (cust_mobile, noofdays, date_of_billing, amount, status) values (? ,? , curdate(), ?, 0)");
			ps.setString(1, comboMobile.getEditor().getText());
			ps.setInt(2, Integer.parseInt(lblDays.getText()));
			ps.setFloat(3, Float.parseFloat(lblTotalBill.getText()));
			ps.executeUpdate();
			
			Date todayEvening = Date.valueOf(LocalDate.now().plusDays(1).atStartOfDay().toLocalDate());
			ps = connection.prepareStatement("update customers set dos=?  where mobile=?");
			ps.setDate(1, todayEvening);
			ps.setString(2, comboMobile.getEditor().getText());
			ps.executeUpdate();
//			System.out.println("SAVED");
			lblDone.setText("DONE");
			
			String msgResponseString = SST_SMS.bceSunSoftSend("9532351442", "Hello World");
			System.out.println(msgResponseString);
			if(msgResponseString.indexOf("Exception") != -1) System.out.println("Check Internet Connection");
			else if(msgResponseString.indexOf("successfully") != -1) System.out.println("Sent");
			else System.out.println("Invalid Number");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert comboMobile != null : "fx:id=\"comboMobile\" was not injected: check your FXML file 'Billing.fxml'.";
        assert listPapers != null : "fx:id=\"listPapers\" was not injected: check your FXML file 'Billing.fxml'.";
        assert listPrices != null : "fx:id=\"listPrices\" was not injected: check your FXML file 'Billing.fxml'.";
        assert lblDays != null : "fx:id=\"lblDays\" was not injected: check your FXML file 'Billing.fxml'.";
        assert lblGetDetails != null : "fx:id=\"lblGetDetails\" was not injected: check your FXML file 'Billing.fxml'.";
        assert lblTotalBill != null : "fx:id=\"lblTotalBill\" was not injected: check your FXML file 'Billing.fxml'.";
        assert lblDone != null : "fx:id=\"lblDone\" was not injected: check your FXML file 'BillGenerator.fxml'.";
        connection = Connect.getConnection();
        ObservableList<String> oList = FXCollections.observableArrayList();
        try {
			ps = connection.prepareStatement("select * from customers");
			ResultSet rSet = ps.executeQuery();
			while(rSet.next()) oList.add(rSet.getString("mobile"));
			comboMobile.setItems(oList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}