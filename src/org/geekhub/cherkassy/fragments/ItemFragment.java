package org.geekhub.cherkassy.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.db.DatabaseHelper;
import org.geekhub.cherkassy.db.InfoTable;

public class ItemFragment extends SherlockFragment {
	private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.item_frag, container, false);

        long item_id =  getActivity().getIntent().getLongExtra("ID", 0);
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(InfoTable.TABLE_ITEMS, null, InfoTable.COLUMN_ID + "='" + item_id + "'", null,null,null,null);
        cursor.moveToFirst();
        String category =  cursor.getString(cursor.getColumnIndex(InfoTable.COLUMN_NAME));
        TextView tw = (TextView) inflate.findViewById(R.id.item_title);
        tw.setText(category);
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == 0){
            mMapView = (MapView) inflate.findViewById(R.id.map);
            mMapView.onCreate(mBundle);
            mMap = mMapView.getMap();
            mMap.setMyLocationEnabled(true);
            MarkerOptions mo = new MarkerOptions()
                    .position(new LatLng(
                            cursor.getDouble(cursor.getColumnIndex(InfoTable.COLUMN_LATITUDE)),
                            cursor.getDouble(cursor.getColumnIndex(InfoTable.COLUMN_LOGOURL))))
                    .title(cursor.getString(cursor.getColumnIndex(InfoTable.COLUMN_NAME)))
                    .snippet(cursor.getString(cursor.getColumnIndex(InfoTable.COLUMN_ADDRESS)));
            mMap.addMarker(mo);
//	        Polyline line =mMap.addPolyline(new PolylineOptions().add(
//	        			new LatLng(56.05, 42.042),
//	        			new LatLng(50.00, 45.02))
//	        			.geodesic(true));
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
        //mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //mMapView.onPause();
    }

    @Override
    public void onDestroy() {
       // mMapView.onDestroy();
        super.onDestroy();
    }

    
}
