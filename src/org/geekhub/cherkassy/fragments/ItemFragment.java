package org.geekhub.cherkassy.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.db.InfoContentProvider;
import org.geekhub.cherkassy.db.InfoTable;
import org.geekhub.cherkassy.helpers.ItemPageAdapter;

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
        TextView tw = (TextView) inflate.findViewById(R.id.item_title);
        tw.setText(category);

        ItemPageAdapter adapter = new ItemPageAdapter();
        ViewPager myPager = (ViewPager) inflate.findViewById(R.id.itemviewpager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(2);

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == 0){
            mMapView = (MapView) inflate.findViewById(R.id.map);
            mMapView.onCreate(mBundle);
            mMap = mMapView.getMap();
            mMap.setMyLocationEnabled(true);
            MarkerOptions mo = new MarkerOptions()
                    .position(new LatLng(
                            cursor1.getDouble(cursor1.getColumnIndex(InfoTable.COLUMN_LATITUDE)),
                            cursor1.getDouble(cursor1.getColumnIndex(InfoTable.COLUMN_LONGITUDE))))
                    .title(cursor1.getString(cursor1.getColumnIndex(InfoTable.COLUMN_NAME)))
                    .snippet(cursor1.getString(cursor1.getColumnIndex(InfoTable.COLUMN_ADDRESS)));
            mMap.addMarker(mo);

            try {
                MapsInitializer.initialize(getSherlockActivity());
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }

        return inflate;
    }



    @Override
    public void onStart() {
        super.onStart();

        
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
