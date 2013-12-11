package com.grouphadel.dlsaa.storage;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.grouphadel.dlsaa.models.PartnerBranch;

public class PartnerBranchDAO extends BaseDAO {
	
	private SQLiteDatabase database;

	public PartnerBranchDAO(SQLiteDatabase database) {
		this.database = database;
	}

	public void add(PartnerBranch branch) {
		SQLiteStatement stmt = database
				.compileStatement("INSERT INTO PartnerBranches (businessID, address, latitude, longitude) "
						+ "VALUES (?, ?, ?, ?)");
		stmt.bindLong(1, branch.getBusiness().getId());
		stmt.bindString(2, branch.getAddress());
		stmt.bindDouble(3, branch.getLatitude());
		stmt.bindDouble(4, branch.getLongitude());

		stmt.executeInsert();
	}
}
