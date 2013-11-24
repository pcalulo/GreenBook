package com.grouphadel.dlsaa.models;

public class PartnerBusiness {
	private String name;
	private String discountInfo;
	
	public PartnerBusiness() {
		setName("Example PartnerBusiness");
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
