package org.geekhub.cherkassy.fragments;

import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.db.ImageTable;
import org.geekhub.cherkassy.db.InfoContentProvider;
import org.geekhub.cherkassy.db.InfoTable;
import org.geekhub.cherkassy.helpers.ItemPageAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ItemFragment extends SherlockFragment {
	private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.item_frag, container, false);
        long item_id =  getActivity().getIntent().getLongExtra("ID", 0);        
        Cursor cursor1 = getSherlockActivity().getContentResolver().query(
                InfoContentProvider.CONTENT_URI,
                null,
                InfoTable.COLUMN_ID + "=" + item_id ,
                null,
                null);
        cursor1.moveToFirst();
        
        String category =  cursor1.getString(cursor1.getColumnIndex(InfoTable.COLUMN_NAME));
        String address =  cursor1.getString(cursor1.getColumnIndex(InfoTable.COLUMN_ADDRESS));
        TextView tw = (TextView) inflate.findViewById(R.id.item_title);
        tw.setText(category);

        ImagePager(item_id,inflater,inflate);

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == 0){
            mMapView = (MapView) inflate.findViewById(R.id.map);
            mMapView.onCreate(mBundle);
            mMap = mMapView.getMap();
            mMap.setMyLocationEnabled(true);
            double lat , lon;
            lat  = cursor1.getDouble(cursor1.getColumnIndex(InfoTable.COLUMN_LATITUDE));
            lon  = cursor1.getDouble(cursor1.getColumnIndex(InfoTable.COLUMN_LONGITUDE));
            Log.i("Points",Double.toString(lat));
            Log.i("Points",Double.toString(lon));
            LatLng latLng = new LatLng(lat, lon);
            MarkerOptions mo = new MarkerOptions()
                    .position(latLng)
                    .title(category)
                    .snippet(address);
            mMap.addMarker(mo);    
            try {
                MapsInitializer.initialize(getSherlockActivity());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(cameraUpdate);
                mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {					
					@Override
					public void onInfoWindowClick(Marker marker) {
						String s = "geo:"+marker.getPosition().latitude+","+marker.getPosition().longitude+
								"?q="+marker.getPosition().latitude+","+marker.getPosition().longitude+"("+marker.getTitle()+")";
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
						startActivity(intent);
					}
				});
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
        cursor1.close();
        return inflate;
    }

    private void ImagePager(long item_id, LayoutInflater inflater,View v) {

        Cursor cursor = getSherlockActivity().getContentResolver().query(
                InfoContentProvider.IMG_URI,
                null,
                ImageTable.COLUMN_ITEMID + "=" + item_id ,
                null,
                null);

        cursor.moveToFirst();
        ItemPageAdapter adapter = new ItemPageAdapter(getActivity(),cursor,inflater);
        ViewPager myPager = (ViewPager) v.findViewById(R.id.itemviewpager);
        myPager.setAdapter(adapter);
    }

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
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
}
