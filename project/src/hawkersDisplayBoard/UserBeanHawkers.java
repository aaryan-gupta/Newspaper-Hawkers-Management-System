package hawkersDisplayBoard;

import java.sql.Date;

public class UserBeanHawkers {
	String name, mobile, address, areas;
	float salary;
	Date doj;
	
	public UserBeanHawkers(String name, String mobile, String address, String areas, float salary, Date doj) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.areas = areas;
		this.salary = salary;
		this.doj = doj;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
}
