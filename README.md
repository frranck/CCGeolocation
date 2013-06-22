
- ios : 

* add the corelocation framework, in linked framework.

- android :

* add this in your manifest:

	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

* in your main java activity class:

add:

import org.mumblecore.location.CClocation;

in 	protected void onCreate(Bundle savedInstanceState)

add this call:
		CClocation.setContext(getApplicationContext(), this);

 *in CClocation.java

replace all <MAIN_CLASS> occurencies  with the name of your class that extends Cocos2dxActivity
