package models;

public class Store {
	private String name;
	private String discountInfo;
	
	public Store() {
		setName("Example Store");
		setDiscountInfo("0% off on everything");
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
