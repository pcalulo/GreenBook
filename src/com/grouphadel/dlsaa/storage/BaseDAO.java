package com.grouphadel.dlsaa.storage;

public class BaseDAO {
	private DBHelper dbHelper;

	public DBHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
}
