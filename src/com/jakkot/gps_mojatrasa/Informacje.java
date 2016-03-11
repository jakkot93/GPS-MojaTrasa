package com.jakkot.gps_mojatrasa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Informacje extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informacje);
		
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
        case R.id.ustawienia:
            Intent intent2 = new Intent(this, Ustawienia.class);
            startActivity(intent2);
            finish();
            return true;
        case R.id.pomiary:
            Intent intent3 = new Intent(this, Pomiary.class);
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

