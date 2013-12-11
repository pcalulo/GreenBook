package com.grouphadel.dlsaa.models;

public class PartnerBranch extends DatabaseObject{
	private int businessID;
	private String address;
	private double longitude;
	private double latitude;
	
	public PartnerBranch() {
		this.businessID = 0;
		this.address = "";
		this.longitude = 0;
		this.latitude = 0;
	}
	
	public PartnerBranch(int businessID, String address, double longitude,
			double latitude) {
		super();
		this.businessID = businessID;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public int getBusinessID() {
		return businessID;
	}
	
	public void setBusinessID(int businessID) {
		this.businessID = businessID;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
}
