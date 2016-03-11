package com.jakkot.gps_mojatrasa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper{

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "dbGPS.db";
	
	private static final String SQL_CREATE_ENTRIES = " CREATE TABLE " + MyDatabaseInfo.TABLE_NAME + "("+ MyDatabaseInfo._ID + " INTEGER PRIMARY KEY, " + MyDatabaseInfo.COLUMN_NAME_DYSTANS + " DOUBLE, " + MyDatabaseInfo.COLUMN_NAME_GODZINY + " INTEGER, " + MyDatabaseInfo.COLUMN_NAME_MINUTY + " INTEGER, " + MyDatabaseInfo.COLUMN_NAME_SEKUNDY + " INTEGER " + ")";
	private static final String SQL_CREATE_ENTRIES2 = " CREATE TABLE " + MyDatabaseInfo.TABLE_NAME2 + "("+ MyDatabaseInfo.COLUMN_NAME_ID2 + " INTEGER PRIMARY KEY, " + MyDatabaseInfo.COLUMN_NAME_GPS + " INTEGER, " + MyDatabaseInfo.COLUMN_NAME_JEDNOSTKA + " INTEGER " + ")";
	
	public MyDatabase(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db){
		db.execSQL(SQL_CREATE_ENTRIES);
		db.execSQL(SQL_CREATE_ENTRIES2);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
		onUpgrade(db, oldVersion, newVersion);
	}
	
}