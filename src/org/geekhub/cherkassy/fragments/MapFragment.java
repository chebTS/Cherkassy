package org.geekhub.cherkassy.fragments;

import org.geekhub.cherkassy.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.maps.GeoPoint;

public class MapFragment extends SherlockFragment  {
	private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.map_frag, container,false);
		if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getSherlockActivity()) == 0){				
			mMapView = (MapView) v.findViewById(R.id.map);
	        mMapView.onCreate(mBundle);
	        mMap = ((MapView) v.findViewById(R.id.map)).getMap();
	        mMap.setMyLocationEnabled(true);	        
	        
	        //mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(37.412259, -79.138257) , 13.0f) );
	        //mMap.setOnInfoWindowClickListener(this);
	        //addMarkers();
	        
		}
        return v;
    }
	
	
	


	@Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
	
//	@Override
//	public void onInfoWindowClick(Marker paramMarker) {
//		
//		
//		Toast.makeText(mActivity, "Info " + paramMarker.getId() , Toast.LENGTH_LONG).show();
//	}	
}
