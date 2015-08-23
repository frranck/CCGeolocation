Geolocation for cocos2-dx for android and iOS.

## ios : 

add the corelocation framework, in linked framework.

add a key NSLocationWhenInUseUsageDescription of type String in plist.

## android :

add this in your manifest:

	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

add in your main java activity class:

import org.mumblecore.location.CClocation;

in protected void onCreate(Bundle savedInstanceState) add this call:

CClocation.setContext(getApplicationContext(), this);

in CClocation.java:

replace all MAIN_CLASS occurencies  with the name of your class that extends Cocos2dxActivity
