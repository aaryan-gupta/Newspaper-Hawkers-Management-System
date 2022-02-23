package customersDisplayBoard;

public class UserBeanCustomers {
	String name, mobile, address, area, papers;

	public UserBeanCustomers(String name, String mobile, String address, String area, String papers) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.area = area;
		this.papers = papers;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}
}
