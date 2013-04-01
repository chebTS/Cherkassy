package org.geekhub.cherkassy.activity;

import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.fragments.MapFragment;

import android.location.LocationManager;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;



public class MapActivity extends SherlockFragmentActivity {
	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //My app doesn't make if i not set theme for this Activity
        setTheme(R.style.Sherlock___Theme_DarkActionBar);
		setContentView(R.layout.map_act);

		if (savedInstanceState == null) {
            getSupportFragmentManager()
            		.beginTransaction()
            		.add(R.id.map_frag, new MapFragment())
            		.commit();
        }
		
	 
	}

//	@Override
//	public void onLocationChanged(Location location) {
//		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//		fragmentMap.updateLocation(latLng);
//	    //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
//	    //map.animateCamera(cameraUpdate);
//	    locationManager.removeUpdates(this);
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//	}
	

	
}
