package com.specialkid.main;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class InfoDataSource {

	private SQLiteDatabase database;
	private InfoSQLiteHelper dbHelper;

	private String[] allColumns = { InfoSQLiteHelper.COLUMN_ID,
			InfoSQLiteHelper.COLUMN_NAME, InfoSQLiteHelper.COLUMN_PHONE, InfoSQLiteHelper.COLUMN_VERSION };

	public InfoDataSource(Context context) {
		dbHelper = new InfoSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	private Info cursorToInfo(Cursor cursor) {
		
		Info info = new Info();
		info.setId(cursor.getLong(0));
		info.setName(cursor.getString(1));
		info.setPhone(cursor.getString(2));
		info.setVersion(cursor.getString(3));
		return info;
	}

	
	public List<Info> getAllInfo() {
	    List<Info> infos = new ArrayList<Info>();

	    Cursor cursor = database.query(InfoSQLiteHelper.TABLE_INFO,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Info info = cursorToInfo(cursor);
	      infos.add(info);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return infos;
	  }
	
	
	public Info createInfo(String name, String phone, String version) {

		ContentValues values = new ContentValues();
		values.put(InfoSQLiteHelper.COLUMN_NAME, name);
		values.put(InfoSQLiteHelper.COLUMN_PHONE, phone);
		values.put(InfoSQLiteHelper.COLUMN_VERSION, version);

		
		//This is the query to insert the data.
		long insertId = database
				.insert(InfoSQLiteHelper.TABLE_INFO, null, values);
		
		// This is query to search the data with a particular id.
		Cursor cursor = database.query(InfoSQLiteHelper.TABLE_INFO, allColumns,
				InfoSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		
		cursor.moveToFirst();

		Info info = cursorToInfo(cursor);
		cursor.close();
		return info;
	}
	
	
	public void deleteDataFromInfoTable() {
		 database.delete(InfoSQLiteHelper.TABLE_INFO, null, null);
	}

}
