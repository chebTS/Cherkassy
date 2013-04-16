package org.geekhub.cherkassy.fragments;

import android.database.Cursor;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.db.ImageTable;
import org.geekhub.cherkassy.db.InfoContentProvider;
import org.geekhub.cherkassy.db.InfoTable;
import org.geekhub.cherkassy.helpers.ItemPageAdapter;

import java.util.ArrayList;
import java.util.List;

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
        String description =  cursor1.getString(cursor1.getColumnIndex(InfoTable.COLUMN_DESCRIPTION));

        getSherlockActivity().setTitle(category);

        TextView tw = (TextView) inflate.findViewById(R.id.item_title);
        TextView textView = (TextView) inflate.findViewById(R.id.txtDescription);

        tw.setText(category);
        textView.setText(description);

        ImagePager(item_id,inflater,inflate);

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == 0){
            mMapView = (MapView) inflate.findViewById(R.id.map);
            mMapView.onCreate(mBundle);
            mMap = mMapView.getMap();
            mMap.setMyLocationEnabled(true);
            Log.i("Points",Double.toString(cursor1.getDouble(cursor1.getColumnIndex(InfoTable.COLUMN_LATITUDE))));
            Log.i("Points",Double.toString(cursor1.getDouble(cursor1.getColumnIndex(InfoTable.COLUMN_LONGITUDE))));
            
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

    private void ImagePager(long item_id, LayoutInflater inflater,View v) {

        Cursor cursor = getSherlockActivity().getContentResolver().query(
                InfoContentProvider.IMG_URI,
                null,
                ImageTable.COLUMN_ITEMID + "=" + item_id ,
                null,
                null);

        cursor.moveToFirst();
        List<String> urlList = new ArrayList<String>();

        for (int i=1;i<=cursor.getCount();i++) {
            String imgLink = cursor.getString(cursor.getColumnIndex(ImageTable.COLUMN_URL));
            urlList.add(imgLink);
            cursor.moveToNext();
            Log.e("1234",imgLink);
        }

        ItemPageAdapter adapter = new ItemPageAdapter(getActivity(),urlList,inflater);
        ViewPager myPager = (ViewPager) v.findViewById(R.id.itemviewpager);
        myPager.setAdapter(adapter);
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
