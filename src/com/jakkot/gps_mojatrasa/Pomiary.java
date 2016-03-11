package com.jakkot.gps_mojatrasa;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pomiary extends Activity implements LocationListener {

	Button BtnStart, BtnStop, BtnPauza;
	TextView TvPredkosc, TvCzas, TvDystans, TvLatitude, TvLongitude, TvStatus, TvSrednia;
	
	int godziny = 0, minuty = 0, sekundy = 0, MinOdleglosc = 10, MinCzas = 1000, UstJednostka, UstGPS;
	double predkosc = 0, dystans = 0, srednia = 0;
	boolean sygnal = false, wybor = false, start = true;
	
	LocationManager locationManager = null;
	Location srcLocation = null;
	Timer timer = null;
	
	private MyDatabase theMyDatabase;
	private SQLiteDatabase theSQLiteDatabase;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomiary);
        
        TvPredkosc = (TextView) findViewById(R.id.predkosc);
        TvCzas = (TextView) findViewById(R.id.czas);
        TvDystans = (TextView) findViewById(R.id.dystans);
        TvLatitude = (TextView) findViewById(R.id.latitude);
        TvLongitude = (TextView) findViewById(R.id.longitude);
        TvStatus = (TextView) findViewById(R.id.status);
        TvSrednia = (TextView) findViewById(R.id.srednia);
        
        BtnStart = (Button) findViewById(R.id.start);
        BtnStop = (Button) findViewById(R.id.stop);
        BtnPauza = (Button) findViewById(R.id.pauza);  
        
        theMyDatabase = new MyDatabase(this);
		theSQLiteDatabase = theMyDatabase.getWritableDatabase();
        
		String[] astrProjection = { MyDatabaseInfo.COLUMN_NAME_ID2, MyDatabaseInfo.COLUMN_NAME_GPS, MyDatabaseInfo.COLUMN_NAME_JEDNOSTKA };
		Cursor aCursor = theSQLiteDatabase.query(MyDatabaseInfo.TABLE_NAME2, astrProjection, null, null, null, null, null);
		displayValues(aCursor);
		
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MinCzas, MinOdleglosc, this);
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
       case R.id.ActionBar:
        	if(sygnal == true || start == false){
        		Toast.makeText(getBaseContext(), "Jeœli przejdziesz dalej twój trening\nzostanie usuniêty.", Toast.LENGTH_LONG).show();
        	}
        	return true;
        case R.id.historia:
        	Intent intent1 = new Intent(this, Historia.class);
            startActivity(intent1);
            locationManager.removeUpdates(this);
            finish();
            return true;
        case R.id.ustawienia:
            Intent intent2 = new Intent(this, Ustawienia.class);
            startActivity(intent2);
            locationManager.removeUpdates(this);
            finish();
            return true;
        case R.id.informacje:
            Intent intent3 = new Intent(this, Informacje.class);
            startActivity(intent3);
            locationManager.removeUpdates(this);
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
    
    
    
    @Override
    public void onLocationChanged(Location location) {
    	
    	if (wybor == false){
    		TvStatus.setText("GPS OK"); //gdy trening nie jest w³¹czony dzia³a tylko to, zeby miec
    		srcLocation = location;     //ca³y czas aktualne po³ozenie
    		BtnStart.setEnabled(true);
    	}
    	
    	TvLatitude.setText(Double.toString(location.getLatitude()));  //szerokoœæ geograficzna
		TvLongitude.setText(Double.toString(location.getLongitude()));  //d³ugoœæ geograficzna
    	
    	if(sygnal == true){
    		if(UstJednostka == 0){
    			predkosc = location.getSpeed() * 3.6;
    			TvPredkosc.setText("Prêdkoœæ: "+ String.format("%.1f", predkosc)+" km/h");
    		
    			dystans += location.distanceTo(srcLocation) / 1000;
    			TvDystans.setText("Dystans:  "+ String.format("%.2f",dystans)+" km");
    			srcLocation = location;
    		}
    		if (UstJednostka == 1){
    			predkosc = location.getSpeed() * 3.6*0.621371192;
    			TvPredkosc.setText("Prêdkoœæ: "+ String.format("%.1f", predkosc)+" mph");
    		
    			dystans += location.distanceTo(srcLocation) / 1000*0.621371192;
    			TvDystans.setText("Dystans:  "+ String.format("%.2f",dystans)+" mi");
    			srcLocation = location;
    		}
    	}	
    }
 
    @Override
    public void onProviderDisabled(String provider) {  
        /******** Called when User off Gps *********/   
        Toast.makeText(getBaseContext(), "GPS: OFF", Toast.LENGTH_LONG).show();
    }
 
    @Override
    public void onProviderEnabled(String provider) { 
        /******** Called when User on Gps  *********/
        Toast.makeText(getBaseContext(), "GPS: ON ", Toast.LENGTH_LONG).show();
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub      
    }  
    
    public void start(View view){
    	
    	if(start == true){
    		godziny = 0; minuty = 0; sekundy = 0; predkosc = 0; dystans = 0;
    		if(UstJednostka == 0){
    			TvPredkosc.setText("Prêdkoœæ: 0.0 km/h");
    			TvDystans.setText("Dystans: 0.00 km");
    		}
    		if(UstJednostka == 1){
    			TvPredkosc.setText("Prêdkoœæ: 0.0 mph");
    			TvDystans.setText("Dystans: 0.00 mi");
    		}
    	}
    	
    	startStopera();
    	wybor = true;
    	sygnal = true;
    	BtnStart.setEnabled(false);
        BtnStop.setEnabled(true);
        BtnPauza.setEnabled(true);
        
        TvSrednia.setText("");
    }
    
    public void pauza(View view){
	  	
    	timer.cancel();
    	sygnal = false;
    	wybor = false;
    	start = false;
        BtnStart.setEnabled(true);
        BtnPauza.setEnabled(false);
        Toast.makeText(getBaseContext(), "PAUZA", Toast.LENGTH_LONG).show();
        if(dystans != 0){
            srednia = dystans/(godziny*3600+minuty*60+sekundy)*3600;
            TvSrednia.setText("Œrednia prêdkoœæ:  "+ String.format("%.1f", srednia)+" km/h");
        }
    }
    
    public void stop(View view){
    	  	
    	timer.cancel();
    	sygnal = false;
    	wybor = false;
    	start = true;
        BtnStart.setEnabled(true);
        BtnStop.setEnabled(false);
        BtnPauza.setEnabled(false);
        Toast.makeText(getBaseContext(), "STOP", Toast.LENGTH_LONG).show();
        if(dystans != 0){
            srednia = dystans/(godziny*3600+minuty*60+sekundy)*3600;
            TvSrednia.setText("Œrednia prêdkoœæ:  "+ String.format("%.1f", srednia)+" km/h");
        }
        
        //baza danych
        ContentValues aMapValues = new ContentValues();
		aMapValues.put(MyDatabaseInfo.COLUMN_NAME_DYSTANS, dystans);
		aMapValues.put(MyDatabaseInfo.COLUMN_NAME_GODZINY, godziny);
		aMapValues.put(MyDatabaseInfo.COLUMN_NAME_MINUTY, minuty);
		aMapValues.put(MyDatabaseInfo.COLUMN_NAME_SEKUNDY, sekundy);
		theSQLiteDatabase.insert(MyDatabaseInfo.TABLE_NAME, null, aMapValues);
        
        
    }
    
    
private void startStopera() {
    	
		timer = new Timer();
		int period = 1000;
		TimerTask task = new TimerTask() {

			@Override
			public void run() {

				aktualizujCzas();		
			}
		};

		timer.scheduleAtFixedRate(task, 0, period);

	}

	private void aktualizujCzas() {
		runOnUiThread(new Runnable() {

			public void run() {
					
				sekundy++;
				if(sekundy==59){
					sekundy = 0;
					minuty++;
				}
				
				if(minuty==59){
					minuty = 0;
					godziny++;
				}
				if(godziny==99){
					timer.cancel();
					sekundy = 0; minuty = 0; godziny = 0;
					Toast.makeText(getBaseContext(), "Przekroczono maksymalny czas pomiaru", Toast.LENGTH_LONG).show();
				}
		        
				if(godziny==0 &&minuty<10 && sekundy<10)
					TvCzas.setText("Czas: 0"+godziny+":0"+minuty+":0"+sekundy);
				else if (godziny==0 &&minuty<10 && sekundy >=10)	
					TvCzas.setText("Czas: 0"+godziny+":0"+minuty+":"+sekundy);
				else if(godziny==0 &&minuty>=10 && sekundy<10)
					TvCzas.setText("Czas: 0"+godziny+":"+minuty+":0"+sekundy);
				else if(godziny==0 &&minuty>=10 && sekundy>=10)
					TvCzas.setText("Czas: 0"+godziny+":"+minuty+":"+sekundy);
				else if(godziny<10 && godziny!=0 && minuty<10 && sekundy<10)
					TvCzas.setText("Czas: 0"+godziny+":0"+minuty+":0"+sekundy);
				else if(godziny>=10 && minuty<10 && sekundy<10)
					TvCzas.setText("Czas: "+godziny+":0"+minuty+":0"+sekundy);
			}
		});
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
				int anColumnIndex_gps = aCursor.getColumnIndexOrThrow(MyDatabaseInfo.COLUMN_NAME_GPS);
				int anColumnIndex_jednostka = aCursor.getColumnIndex(MyDatabaseInfo.COLUMN_NAME_JEDNOSTKA);
				do{	
					UstGPS = aCursor.getInt(anColumnIndex_gps);
					UstJednostka = aCursor.getInt(anColumnIndex_jednostka);
				} while(aCursor.moveToNext());
				switch(UstGPS){
					case 0:
						MinOdleglosc = 10;
						MinCzas = 1000;
						break;
					case 1:
						MinOdleglosc = 5;
						MinCzas = 500;
						break;
					case 2:
						MinOdleglosc = 50;
						MinCzas = 5000;
						break;
				}	
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
