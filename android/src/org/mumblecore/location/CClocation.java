
package org.mumblecore.location;

import org.cocos2dx.lib.Cocos2dxActivity;


import org.mumblecore.<MAIN_CLASS>.<MAIN_CLASS>;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;  

import android.app.Service;
import android.os.IBinder;



public class CClocation implements LocationListener {

	    @Override
	    public void onLocationChanged(Location loc) {
			Log.d("activity", "RLOC: onLocationChanged");
            location=loc;
			invokeNativeCode();

	    }

	    @Override
	    public void onProviderDisabled(String provider) {
			Log.d("activity", "RLOC: onProviderDisabled");    	
	    }

	    @Override
	    public void onProviderEnabled(String provider) {
			 LocationManager locManager = (LocationManager)myAndroidContext.getSystemService(Context.LOCATION_SERVICE);         

			Log.d("activity", "RLOC: onProviderEnabled");
		    Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		    double latitude=0;
		    double longitude=0;
		    latitude = location.getLatitude();
		    longitude = location.getLongitude();
			Log.d("activity", "RLOC: onProviderEnabled "+latitude+" "+longitude);
	    }

	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.d("activity", "RLOC: onStatusChanged");
	    	
	    }
	    
	static LocationListener locationListener=null;   
	private static final String TAG = "CClocation";
	static Context myAndroidContext;
	static CClocation instance;
	static <MAIN_CLASS> _mainClassInstance;
	
	static public void setContext(Context c, <MAIN_CLASS> b) {
		Log.d("activity", "RLOC: setContext");
		myAndroidContext=c;
		_mainClassInstance=b;
	}
	
	public void run(Context my) {
		Log.d("activity", "RLOC: run");
		myAndroidContext=my;
	}
	
	
	public static void helloWorld()
{
Log.v("InternetConnection", "HELLO WORLD");
}
	

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	// Declaring a Location Manager
	protected LocationManager locationManager;
	
	public Location getLocation2() {
	    try {
	        locationManager = (LocationManager) myAndroidContext
	                .getSystemService(Context.LOCATION_SERVICE);

	        // getting GPS status
	        isGPSEnabled = locationManager
	                .isProviderEnabled(LocationManager.GPS_PROVIDER);

	        // getting network status
	        isNetworkEnabled = locationManager
	                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

	        if (!isGPSEnabled && !isNetworkEnabled) {
	            // no network provider is enabled
	        } else {
	            this.canGetLocation = true;
	            if (isNetworkEnabled) {
	                locationManager.requestLocationUpdates(
	                        LocationManager.NETWORK_PROVIDER,
	                        MIN_TIME_BW_UPDATES,
	                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                Log.d("activity", "LOC Network Enabled");
	                if (locationManager != null) {
	                    location = locationManager
	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                    if (location != null) {
	    	                Log.d("activity", "LOC by Network");
	                        latitude = location.getLatitude();
	                        longitude = location.getLongitude();
	                    }
	                }
	            }
	            // if GPS Enabled get lat/long using GPS Services
	            if (isGPSEnabled) {
	                if (location == null) {
	                    locationManager.requestLocationUpdates(
	                            LocationManager.GPS_PROVIDER,
	                            MIN_TIME_BW_UPDATES,
	                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                    Log.d("activity", "RLOC: GPS Enabled");
	                    if (locationManager != null) {
	                        location = locationManager
	                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                        if (location != null) {
	    	                    Log.d("activity", "RLOC: loc by GPS");

	                            latitude = location.getLatitude();
	                            longitude = location.getLongitude();
	                        }
	                    }
	                }
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		Log.d("activity", "RLOC: Location xx "+latitude+" "+longitude);

	    return location;
	}

	
	static CClocation truc;
	
	
	private static void getLocation()
	{
		Log.d("activity", "getLocation");
		truc = new CClocation();
		
		_mainClassInstance.runOnUiThread(new Runnable() {
		   public void run() {
				truc.getLocation2();
		   }
		 });
		
	
	}
	
	 private native void nativeupdatePosition(double a, double b);

	  public void invokeNativeCode() {
		        latitude = location.getLatitude();
                longitude = location.getLongitude();
                nativeupdatePosition(latitude,longitude);
		    }
		
}

