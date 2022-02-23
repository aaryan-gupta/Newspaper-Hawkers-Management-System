package billHistory;

import java.sql.Date;

public class UserBeanBillHistory {
	String cust_mobile;
	int noofdays;
	Date date_of_billing;
	float amount;
	public UserBeanBillHistory(String cust_mobile, int noofdays, Date date_of_billing, float amount) {
		super();
		this.cust_mobile = cust_mobile;
		this.noofdays = noofdays;
		this.date_of_billing = date_of_billing;
		this.amount = amount;
	}
	public String getCust_mobile() {
		return cust_mobile;
	}
	public void setCust_mobile(String cust_mobile) {
		this.cust_mobile = cust_mobile;
	}
	public int getNoofdays() {
		return noofdays;
	}
	public void setNoofdays(int noofdays) {
		this.noofdays = noofdays;
	}
	public Date getDate_of_billing() {
		return date_of_billing;
	}
	public void setDate_of_billing(Date date_of_billing) {
		this.date_of_billing = date_of_billing;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

}
