package com.grouphadel.dlsaa.models;

public class PartnerBusiness extends DatabaseObject {
	// Categories!
	public static final String CATEGORY_CLOTHING = "Clothing and Footwear";
	public static final String CATEGORY_CULTURE = "Culture and Arts";
	public static final String CATEGORY_FLOWER = "Flower Shops";
	public static final String CATEGORY_GADGETRY = "Gadgetry";
	public static final String CATEGORY_HEALTH = "Health and Wellness";
	public static final String CATEGORY_HOTEL = "Hotels and Resorts";
	public static final String CATEGORY_HOBBIES = "Hobbies and Households";
	public static final String CATEGORY_MOTORING = "Motoring";
	public static final String CATEGORY_PHOTOGRAPHY = "Photography";
	public static final String CATEGORY_OPTICAL = "Optical Shops";
	public static final String CATEGORY_RESTAURANT = "Restaurants and Bars";
	public static final String CATEGORY_TRAVEL = "Travels and Tours";

	private String name;
	private String discountInfo;
	private String category;

	// This is a bit hackish
	private PartnerBranch nearestBranch;

	public PartnerBusiness() {
		setName("Example PartnerBusiness");
		setDiscountInfo("0% off on all cash purchases, -0% off on card purchases");
		setCategory(CATEGORY_RESTAURANT);
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public PartnerBranch getNearestBranch() {
		return nearestBranch;
	}

	public void setNearestBranch(PartnerBranch nearestBranch) {
		this.nearestBranch = nearestBranch;
	}
}
