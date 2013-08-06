package com.citygameclient;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPS extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	    LocationManager locManager= (LocationManager) getSystemService (Context.LOCATION_SERVICE);
	    LocationListener locListener= new MyLocationListener();
	    locManager.requestLocationUpdates(locManager.GPS_PROVIDER, 0, 0, locListener);
	}
	
	}
