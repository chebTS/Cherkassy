package org.geekhub.cherkassy.fragments;

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
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        String address =  cursor1.getString(cursor1.getColumnIndex(InfoTable.COLUMN_ADDRESS));
        String www =  cursor1.getString(cursor1.getColumnIndex(InfoTable.COLUMN_WEBSITEURL));
        String email =  cursor1.getString(cursor1.getColumnIndex(InfoTable.COLUMN_EMAIL));
        String phone =  cursor1.getString(cursor1.getColumnIndex(InfoTable.COLUMN_PHONE));

        getSherlockActivity().setTitle(category);

        TextView tw = (TextView) getView().findViewById(R.id.item_title);
        TextView textView = (TextView) getView().findViewById(R.id.txtDescription);
        TextView wwwTV = (TextView) getView().findViewById(R.id.txt_www);
        TextView emailTV = (TextView) getView().findViewById(R.id.txt_email);
        TextView phoneTV = (TextView) getView().findViewById(R.id.txt_phone);

        tw.setText(category);
        textView.setText(description);
        wwwTV.setText("www: " + www);
        emailTV.setText("Email: " + email);
        phoneTV.setText("Phone: " + phone);

        ImagePager(item_id,getLayoutInflater(savedInstanceState),getView());

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == 0){
            mMapView = (MapView) getView().findViewById(R.id.map);
            mMapView.onCreate(mBundle);
            mMap = mMapView.getMap();
            mMap.setMyLocationEnabled(true);
            double lat , lon;
            lat  = cursor1.getDouble(cursor1.getColumnIndex(InfoTable.COLUMN_LATITUDE));
            lon  = cursor1.getDouble(cursor1.getColumnIndex(InfoTable.COLUMN_LONGITUDE));
            Log.i("Points", Double.toString(lat));
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
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.item_frag, container, false);

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
        }

        ItemPageAdapter adapter = new ItemPageAdapter(getActivity(),urlList,inflater);
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
