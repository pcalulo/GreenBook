package com.grouphadel.dlsaa.models;

public class DatabaseObject {
	public static final long DB_INVALID_ID = -1;
	
	private long id = DB_INVALID_ID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
