package com.grouphadel.dlsaa.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.grouphadel.dlsaa.models.PartnerBranch;
import com.grouphadel.dlsaa.models.PartnerBusiness;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DBHelper acts as a central utility for managing SQLite database access. Only
 * a single database connection to the database will be used for the entire app,
 * making all database operations execute serially, and keeping us safe from the
 * complications of concurrent access.
 * 
 * @author lugkhast
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "dlsaa.db";
	private static DBHelper instance = null;
	private static String TAG = "DBHelper";

	private Context context;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Store deadline as milliseconds since Unix epoch
		Log.d(TAG, "Creating database tables");
		db.execSQL("CREATE TABLE PartnerBusinesses (name TEXT, discount_description TEXT, category TEXT)");
		db.execSQL("CREATE TABLE PartnerBranches (businessID INTEGER, address TEXT, latitude REAL, "
				+ "longitude REAL)");

		loadPackagedBusinessData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public PartnerBusinessDAO createBusinessDAO() {
		return new PartnerBusinessDAO(this.getWritableDatabase());
	}

	public PartnerBusinessDAO createBusinessDAO(SQLiteDatabase database) {
		return new PartnerBusinessDAO(database);
	}

	public void loadPackagedBusinessData(SQLiteDatabase database) {
		PartnerBusiness business;
		PartnerBranch branch;
		PartnerBusinessDAO businessDao = new PartnerBusinessDAO(database);
		PartnerBranchDAO branchDao = new PartnerBranchDAO(database);
		
		database.execSQL("DELETE FROM PartnerBusinesses");
		database.execSQL("DELETE FROM PartnerBranches");

		business = new PartnerBusiness();
		business.setName("World Chicken");
		business.setDiscountInfo("5% off with cash");
		business.setCategory(PartnerBusiness.CATEGORY_RESTAURANT);
		businessDao.add(business);
		branch = new PartnerBranch();
		branch.setBusiness(business);
		branch.setAddress("10-11A University Mall, 2507 Taft Avenue, Malate, Manila");
		branch.setLatLong(14.563133, 120.994389);
		branchDao.add(branch);

		business = new PartnerBusiness();
		business.setName("Zen Tea");
		business.setDiscountInfo("20% off on all cash payments, on all products");
		business.setCategory(PartnerBusiness.CATEGORY_RESTAURANT);
		businessDao.add(business);
		branch = new PartnerBranch();
		branch.setBusiness(business);
		branch.setAddress("Unit 114 EGI Taft Tower, Malate, Manila");
		branch.setLatLong(14.5661,120.993104);
		branchDao.add(branch);

		business = new PartnerBusiness();
		business.setName("The Green Place Eatery and Catering Services");
		business.setDiscountInfo("10% cash, 5% credit card. Not valid on promo");
		business.setCategory(PartnerBusiness.CATEGORY_RESTAURANT);
		businessDao.add(business);
		branch = new PartnerBranch();
		branch.setBusiness(business);
		branch.setAddress("2263-B Taft Avenue, Malate, Manila");
		branch.setLatLong(14.574197, 120.989553);
		branchDao.add(branch);
	}

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			initialize(context);
		}
		return instance;
	}

	public static void initialize(Context context) {
		Log.d(TAG, "Initialized");
		instance = new DBHelper(context);
	}
}
