package com.jakkot.gps_mojatrasa;

import android.provider.BaseColumns;

public class MyDatabaseInfo implements BaseColumns{

	public static final String TABLE_NAME = "Historia";
	public static final String COLUMN_NAME_ID1 = "id1";
	public static final String COLUMN_NAME_DYSTANS = "dystans";
	public static final String COLUMN_NAME_GODZINY = "godziny";
	public static final String COLUMN_NAME_MINUTY = "minuty";
	public static final String COLUMN_NAME_SEKUNDY = "sekundy";
	
	public static final String TABLE_NAME2 = "Ustawienia";
	public static final String COLUMN_NAME_ID2 = "id2";
	public static final String COLUMN_NAME_GPS = "gps";
	public static final String COLUMN_NAME_JEDNOSTKA = "jednostka";
}
