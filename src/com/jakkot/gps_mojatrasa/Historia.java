package com.jakkot.gps_mojatrasa;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Historia extends Activity {
	
	TextView TvDystans, TvGodziny, TvMinuty, TvSekundy, TvID, TvSrednia;

	private MyDatabase theMyDatabase;
	private SQLiteDatabase theSQLiteDatabase;
	
	private final static String LOG_TAG = "Jakkot";
	
	double dystans, dystans1, srednia = 0;
	int godziny, minuty, sekundy, godziny1, minuty1, sekundy1, licznik;
	long aID;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historia);
		
		dystans1 = 0; godziny1 = 0; minuty1 = 0; sekundy1 = 0; licznik = 0;
		
		theMyDatabase = new MyDatabase(this);
		theSQLiteDatabase = theMyDatabase.getWritableDatabase();
	
		TvDystans = (TextView) findViewById(R.id.dystans);
		TvGodziny = (TextView) findViewById(R.id.godziny);
		TvMinuty = (TextView) findViewById(R.id.minuty);
		TvSekundy = (TextView) findViewById(R.id.sekundy);
		TvID = (TextView) findViewById(R.id.tvID);
		TvSrednia= (TextView) findViewById(R.id.srednia);
			
		onClick_ReadAll();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.pomiary:
        	Intent intent1 = new Intent(this, Pomiary.class);
            startActivity(intent1);
            finish();
            return true;
        case R.id.ustawienia:
            Intent intent2 = new Intent(this, Ustawienia.class);
            startActivity(intent2);
            finish();
            return true;
        case R.id.informacje:
            Intent intent3 = new Intent(this, Informacje.class);
            startActivity(intent3);
            finish();
            return true;
        case R.id.wylaczTak:
            finish();
            System.exit(0);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	
	
	public void onClick_ReadAll(){
		
		String[] astrProjection = { MyDatabaseInfo._ID, MyDatabaseInfo.COLUMN_NAME_DYSTANS, MyDatabaseInfo.COLUMN_NAME_GODZINY, MyDatabaseInfo.COLUMN_NAME_MINUTY, MyDatabaseInfo.COLUMN_NAME_SEKUNDY };
		Cursor aCursor = theSQLiteDatabase.query(MyDatabaseInfo.TABLE_NAME, astrProjection, null, null, null, null, null);
		displayValues(aCursor);
	}
	
	private void displayValues(Cursor aCursor) {
		// TODO Auto-generated method stub
		if(aCursor == null){
			// Failed
		}
		else if(!aCursor.moveToFirst()){
			// Cursor Empty, Failed
		}
		else {
			
				int anColumnIndex_Id = aCursor.getColumnIndexOrThrow(MyDatabaseInfo._ID);
				int anColumnIndex_dystans = aCursor.getColumnIndexOrThrow(MyDatabaseInfo.COLUMN_NAME_DYSTANS);
				int anColumnIndex_godziny = aCursor.getColumnIndex(MyDatabaseInfo.COLUMN_NAME_GODZINY);
				int anColumnIndex_minuty = aCursor.getColumnIndex(MyDatabaseInfo.COLUMN_NAME_MINUTY);
				int anColumnIndex_sekundy = aCursor.getColumnIndex(MyDatabaseInfo.COLUMN_NAME_SEKUNDY);
				do{
					
					aID = aCursor.getLong(anColumnIndex_Id);
					dystans = aCursor.getDouble(anColumnIndex_dystans);
					godziny = aCursor.getInt(anColumnIndex_godziny);
					minuty = aCursor.getInt(anColumnIndex_minuty);
					sekundy = aCursor.getInt(anColumnIndex_sekundy);
					
					licznik++;
					dystans1 += dystans;
					
					sekundy1 += sekundy;
					if(sekundy1 > 59){
						sekundy1 = sekundy1 - 60;
						minuty++;
					}
					
					minuty1 += minuty;
					if(minuty1 >59){
						minuty1 = minuty1 - 60;
						godziny++;
					}
						
					godziny1 += godziny;
					Log.i(LOG_TAG,"ID: "+ aID + ". D: "+ dystans + ". G: "+ godziny + ". M: " + minuty + ". S: " + sekundy + " .");
					
				} while(aCursor.moveToNext());
				
				TvID.setText("Liczba treningów:  "+ Long.toString(licznik));
				TvDystans.setText("Dystans:  "+ String.format("%.2f",dystans1)+" km");
				TvGodziny.setText("Godziny:  "+ Integer.toString(godziny1));
				TvMinuty.setText("Minuty:  "+ Integer.toString(minuty1));
				TvSekundy.setText("Sekundy:  "+ Integer.toString(sekundy1));
				
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
	        && (keyCode == KeyEvent.KEYCODE_BACK)
	    && event.getRepeatCount() == 0) 
	    {
	        onBackPressed();
	    }
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
	    // Do nothing
	    return;
	}			
}


