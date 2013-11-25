package com.grouphadel.dlsaa.storage;

import android.content.Context;
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
		db.execSQL("CREATE TABLE PartnerBusinesses (name TEXT, discount_description TEXT)");
		db.execSQL("CREATE TABLE PartnerBranches (businessID INTEGER, address TEXT, latitude REAL, "
				+ "longitude REAL)");
		
		// For testing/demo!
		addSampleData(db);
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
	
	private void addSampleData(SQLiteDatabase db) {
		PartnerBusinessDAO dao = createBusinessDAO(db);
		dao.addSampleBusinessData();
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
