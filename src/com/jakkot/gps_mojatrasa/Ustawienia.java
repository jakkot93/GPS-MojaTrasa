package com.jakkot.gps_mojatrasa;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

public class Ustawienia extends Activity {
	
	RadioButton aRadio1, aRadio2, aRadio3;
	int gps, jednostka;

	private MyDatabase theMyDatabase;
	private SQLiteDatabase theSQLiteDatabase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ustawienia);
		
		aRadio1 = (RadioButton) findViewById(R.id.radio1);
		aRadio2 = (RadioButton) findViewById(R.id.radio2);
		aRadio3 = (RadioButton) findViewById(R.id.radio3);
		
		theMyDatabase = new MyDatabase(this);
		theSQLiteDatabase = theMyDatabase.getWritableDatabase();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.historia:
        	Intent intent1 = new Intent(this, Historia.class);
            startActivity(intent1);
            finish();
            return true;
        case R.id.pomiary:
        	Intent intent2 = new Intent(this, Pomiary.class);
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

    
    public void onRadioButtonClicked1(View view) {
		
		boolean abIsChecked = ((RadioButton) view).isChecked();
		
		switch(view.getId()) 
		{
		case R.id.radio1:
			if(abIsChecked)
				gps = 0;
			break;
		case R.id.radio2:
			if(abIsChecked)
				gps = 1;
			break;
		case R.id.radio3:
			if(abIsChecked)
				gps = 2;
			break;
		}
	}
    
    public void onRadioButtonClicked2(View view) {
		
		boolean abIsChecked = ((RadioButton) view).isChecked();
		
		switch(view.getId()) 
		{
		case R.id.radio4:
			if(abIsChecked)
				jednostka = 0;
			break;
		case R.id.radio5:
			if(abIsChecked)
				jednostka = 1;
			break;
		}
	}
    
    public void zapis(View view){
    
    	//baza danych
    	ContentValues aMapValues = new ContentValues();
    	aMapValues.put(MyDatabaseInfo.COLUMN_NAME_GPS, gps);
    	aMapValues.put(MyDatabaseInfo.COLUMN_NAME_JEDNOSTKA, jednostka);
    	theSQLiteDatabase.insert(MyDatabaseInfo.TABLE_NAME2, null, aMapValues);
	
    	Intent intent = new Intent(this, Pomiary.class);
    	startActivity(intent);
    	finish();
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