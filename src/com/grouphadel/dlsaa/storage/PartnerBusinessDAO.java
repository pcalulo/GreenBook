package com.grouphadel.dlsaa.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.grouphadel.dlsaa.models.DatabaseObject;
import com.grouphadel.dlsaa.models.PartnerBranch;
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
				"SELECT rowid, name, discount_description, businessID, address, longitude, latitude "
						+ "FROM PartnerBusinesses " +
						"LEFT JOIN PartnerBranches ON PartnerBusiness.rowid = PartnerBranches.businessID", null);
		
		while (cursor.moveToNext()) {
			business = new PartnerBusiness();
			business.setId(cursor.getLong(0));
			business.setName(cursor.getString(1));
			business.setDiscountInfo(cursor.getString(2));
			
//			Gets a branch of the business (NOT THE NEAREST ONE)
			business.setNearestBranch(new PartnerBranch(cursor.getInt(3), cursor.getString(4), cursor.getDouble(5), cursor.getDouble(6)));
			
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
	
	public double getNearBusinesses(double lat1, double lon1, double lat2, double lon2){
		double R = 6378.137;
		double dLat = (lat2 - lat1) * Math.PI / 180;
		double dLon = (lon2 - lon1) * Math.PI / 180;
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c;
		
		return d * 1000;//in meters
	}
}
