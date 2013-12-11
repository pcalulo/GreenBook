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
				.compileStatement("INSERT INTO PartnerBusinesses (name, discount_description, category) "
						+ "VALUES (?, ?, ?)");
		stmt.bindString(1, business.getName());
		stmt.bindString(2, business.getDiscountInfo());
		stmt.bindString(3, business.getCategory());

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
		PartnerBusiness business = null;
		PartnerBranch branch = null;
		long lastBusinessId, curBusinessId;

		lastBusinessId = -1;

		Cursor cursor = database
				.rawQuery(
						"SELECT bu.rowid, bu.name, bu.discount_description, bu.category, "
								+ "br.rowid, br.businessID, br.address, br.latitude, br.longitude "
								+ "FROM PartnerBusinesses bu LEFT JOIN PartnerBranches br "
								+ "ON bu.rowid = br.businessID "
								+ "ORDER BY bu.name, bu.rowid", null);

		while (cursor.moveToNext()) {
			curBusinessId = cursor.getLong(0);

			if (lastBusinessId == -1 || curBusinessId != lastBusinessId) {
				business = new PartnerBusiness();
				business.setId(cursor.getLong(0));
				business.setName(cursor.getString(1));
				business.setDiscountInfo(cursor.getString(2));
				business.setCategory(cursor.getString(3));

				businesses.add(business);
			}

			// Check whether the business has at least one branch
			String branchAddr = cursor.getString(6);
			if (branchAddr != null) {
				branch = new PartnerBranch();
				branch.setId(cursor.getLong(4));
				branch.setBusiness(business);
				branch.setAddress(branchAddr);
				branch.setLatitude(cursor.getLong(7));
				branch.setLongitude(cursor.getLong(8));

				business.addBranch(branch);
			}
		}

		return businesses;
	}

	public List<PartnerBusiness> getAllNearby(String category) {
		List<PartnerBusiness> businesses = new ArrayList<PartnerBusiness>();
		PartnerBusiness business = null;
		PartnerBranch branch = null;
		long lastBusinessId, curBusinessId;

		lastBusinessId = -1;
		String[] args = new String[] { category };
		
		Log.d("PartnerBusinessDAO", "Filtering by category: '" + category + "'");

		Cursor cursor = database
				.rawQuery(
						"SELECT bu.rowid, bu.name, bu.discount_description, bu.category, "
								+ "br.rowid, br.businessID, br.address, br.latitude, br.longitude "
								+ "FROM PartnerBusinesses bu LEFT JOIN PartnerBranches br "
								+ "ON bu.rowid = br.businessID "
								+ "WHERE bu.category = ? ORDER BY bu.name, bu.rowid",
						args);

		while (cursor.moveToNext()) {
			curBusinessId = cursor.getLong(0);

			if (lastBusinessId == -1 || curBusinessId != lastBusinessId) {
				business = new PartnerBusiness();
				business.setId(cursor.getLong(0));
				business.setName(cursor.getString(1));
				business.setDiscountInfo(cursor.getString(2));
				business.setCategory(cursor.getString(3));

				businesses.add(business);
			}

			// Check whether the business has at least one branch
			String branchAddr = cursor.getString(6);
			if (branchAddr != null) {
				branch = new PartnerBranch();
				branch.setId(cursor.getLong(4));
				branch.setBusiness(business);
				branch.setAddress(branchAddr);
				branch.setLatitude(cursor.getLong(7));
				branch.setLongitude(cursor.getLong(8));

				business.addBranch(branch);
			}
		}

		cursor.close();
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

	public double getNearBusinesses(double lat1, double lon1, double lat2,
			double lon2) {
		double R = 6378.137;
		double dLat = (lat2 - lat1) * Math.PI / 180;
		double dLon = (lon2 - lon1) * Math.PI / 180;
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(lat1 * Math.PI / 180)
				* Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;

		return d * 1000;// in meters
	}
}
