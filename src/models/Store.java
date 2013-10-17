package models;

public class Store {
	private String name;
	private String discountInfo;
	
	public Store() {
		setName("Example Store");
		setDiscountInfo("0% off on all cash purchases, -0% off on card purchases");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscountInfo() {
		return discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}
}
