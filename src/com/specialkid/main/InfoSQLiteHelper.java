package com.specialkid.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InfoSQLiteHelper extends SQLiteOpenHelper {
	
	
	public static final String TABLE_INFO = "info";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_VERSION = "version";
	
	
	private static final String DATABASE_NAME = "commments.db";
	private static final int DATABASE_VERSION = 8;
	
	private static final String DATABASE_CREATE = "create table "
		      + TABLE_INFO + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_NAME
		      + " text not null,   "
		      +  COLUMN_PHONE +" text not null,  "
		      +  COLUMN_VERSION +" text not null)  "
		      ;


	public InfoSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(InfoSQLiteHelper.class.getName(),
	            "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
	        onCreate(db);

		
	}
	
	

}
