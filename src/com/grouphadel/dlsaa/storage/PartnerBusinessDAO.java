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
		
		stmt.executeInsert();
	}

	public void update(PartnerBusiness business) {
		if (business.getId() == DatabaseObject.DB_INVALID_ID) {
			throw new Error(
					"Cannot update an object that is not in the database");
		}

		SQLiteStatement stmt = database
				.compileStatement("UPDATE PartnerBusinesses SET "
						+ "name = ?, discount_description = ? "
						+ "WHERE rowid = ?");

		stmt.bindString(1, business.getName());
		stmt.bindString(2, business.getDiscountInfo());
		stmt.bindLong(3, business.getId());

		stmt.executeUpdateDelete();
	}

	public List<PartnerBusiness> getAll() {
		List<PartnerBusiness> businesses = new ArrayList<PartnerBusiness>();
		PartnerBusiness business;
		int curBusinessId;

		// TODO: Join with PartnerBranches
		Cursor cursor = database.rawQuery(
				"SELECT rowid, name, discount_description "
						+ "FROM PartnerBusinesses", null);
		
		while (cursor.moveToNext()) {
			business = new PartnerBusiness();
			business.setId(cursor.getLong(0));
			business.setName(cursor.getString(1));
			business.setDiscountInfo(cursor.getString(2));
			
			businesses.add(business);
		}

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

	public void delete(PartnerBusiness business) {
		Long[] args = new Long[] { business.getId() };

		try {
			database.execSQL("DELETE FROM Projects WHERE rowid = ?", args);
		} catch (SQLException ex) {
			Log.e(TAG, "Delete PartnerBusiness failed!");
			ex.printStackTrace();
		}
	}
	
	public void addSampleBusinessData() {
		PartnerBusiness business;
		
		business = new PartnerBusiness();
		business.setName("World Chicken");
		business.setDiscountInfo("5% off with cash");
		this.add(business);
		
		business = new PartnerBusiness();
		business.setName("Zen Tea");
		business.setDiscountInfo("20% off on all cash payments, on all products");
		this.add(business);
		
		business = new PartnerBusiness();
		business.setName("Giligan's Island Restaurant and Bar");
		business.setDiscountInfo("10% cash, 5% credit card. Not valid on promo");
		this.add(business);
	}
}
