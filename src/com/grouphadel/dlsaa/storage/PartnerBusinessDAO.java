package com.grouphadel.dlsaa.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.grouphadel.dlsaa.models.DatabaseObject;
import com.grouphadel.dlsaa.models.PartnerBusiness;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class PartnerBusinessDAO extends BaseDAO {

	private SQLiteDatabase database;
	private static String TAG = "PartnerBusinessDAO";

	public PartnerBusinessDAO(SQLiteDatabase database) {
		this.database = database;
	}

	public void add(PartnerBusiness business) {
		SQLiteStatement stmt = database
				.compileStatement("INSERT INTO PartnerBusinesses (name, discount_description) "
						+ "VALUES (?, ?)");
		stmt.bindString(1, business.getName());
		stmt.bindString(2, business.getDiscountInfo());
	}

	public void update(PartnerBusiness business) {
		if (business.getId() == DatabaseObject.DB_INVALID_ID) {
			throw new Error("Cannot update an object that is not in the database");
		}
		
		SQLiteStatement stmt = database
				.compileStatement("UPDATE PartnerBusinesses SET "
						+ "name = ?, discount_description = ? " +
						"WHERE rowid = ?");
		
		stmt.bindString(1, business.getName());
		stmt.bindString(2, business.getDiscountInfo());
		
		stmt.executeUpdateDelete();
	}

	public List<PartnerBusiness> getAll() {
		List<PartnerBusiness> businesses = new ArrayList<PartnerBusiness>();

		return businesses;
	}

	public PartnerBusiness getById(long id) {
		Log.v(TAG, "STUB: PartnerBusiness.getById");
		return new PartnerBusiness();
	}

	public PartnerBusiness getByName(String name) {
		Log.v(TAG, "STUB: PartnerBusiness.getByName");
		return new PartnerBusiness();
	}

	public void delete(PartnerBusiness project) {

	}
}
