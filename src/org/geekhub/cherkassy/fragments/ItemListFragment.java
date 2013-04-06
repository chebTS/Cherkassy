package org.geekhub.cherkassy.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.activity.ItemActivity;
import org.geekhub.cherkassy.db.DatabaseHelper;
import org.geekhub.cherkassy.db.InfoTable;
import org.geekhub.cherkassy.helpers.ItemListAdapter;

public class ItemListFragment extends SherlockFragment {

    ListView lv;
    String category;
    private SQLiteDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_list_frag,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        category = getActivity().getIntent().getStringExtra("category");
        getListFromDB();
    }

    public void getListFromDB() {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(InfoTable.TABLE_ITEMS, null, InfoTable.COLUMN_CATEGORY + "='" + category + "'", null,null,null,null);

        getActivity().startManagingCursor(cursor);

        String[] from = new String[] {InfoTable.COLUMN_NAME };
        int[] to = new int[] { R.id.name_item };

        ItemListAdapter scAdapter = new ItemListAdapter(getActivity(),R.layout.item_adapter,cursor,from,to,getCurrentLocation());
        lv = (ListView) getView().findViewById(R.id.item_list);
        lv.setAdapter(scAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor)adapterView.getItemAtPosition(i);
                int id =  cursor.getInt(cursor.getColumnIndex(InfoTable.COLUMN_ID));
                String category =  cursor.getString(cursor.getColumnIndex(InfoTable.COLUMN_CATEGORY));
                Intent intent = new Intent(getActivity(), ItemActivity.class);
                intent.putExtra("ID" , id);
                intent.putExtra("category",category);
                intent.putExtra("pos",i);
                startActivity(intent);
            }
        });
    }

     private Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String providerName = locationManager.getBestProvider(criteria, true);

         locationManager.requestLocationUpdates(providerName, 100, 1, new LocationListener() {
             @Override
             public void onLocationChanged(Location location) {}

             @Override
             public void onStatusChanged(String s, int i, Bundle bundle) {}

             @Override
             public void onProviderEnabled(String s) {}

             @Override
             public void onProviderDisabled(String s) {}

         });
         Location loc = locationManager.getLastKnownLocation(providerName);
        return loc;
    }

}
