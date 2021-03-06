package org.geekhub.cherkassy.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.activity.ItemActivity;
import org.geekhub.cherkassy.db.InfoContentProvider;
import org.geekhub.cherkassy.db.InfoTable;
import org.geekhub.cherkassy.helpers.ItemListAdapter;

public class ItemListFragment extends SherlockFragment {

    private ListView lv;
    private String category;
    private SQLiteDatabase database;
    private EditText search_text;
    private ItemListAdapter scAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_list_frag,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
        category = getActivity().getIntent().getStringExtra("category");
        getSherlockActivity().setTitle(category);
        getListFromDB("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem menuItem = menu.findItem(R.id.search_listitem);
        search_text = (EditText) menuItem.getActionView();

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    getListFromDB(editable.toString());
                }
            }
        });
    }

    
    public void getListFromDB(String text) {
        Cursor cursor;
        Location curr = getCurrentLocation();

        String[] proj = InfoTable.PROJECTION;
        proj[2] = " ((longitude-" + curr.getLongitude() + ")*(longitude-" + curr.getLongitude() + ")" +
                " +(latitude-" + curr.getLatitude() + ")*(latitude-" + curr.getLatitude() + ")) AS len ";

        	cursor = getSherlockActivity().getContentResolver().query(
        			InfoContentProvider.CONTENT_URI,
                    proj,
                    InfoTable.COLUMN_NAME + " LIKE '%" + text + "%' AND " + InfoTable.COLUMN_CATEGORY + "='" + category + "' ",
        			null,
        			"len ASC");

        getActivity().startManagingCursor(cursor);

        String[] from = new String[] {InfoTable.COLUMN_NAME };
        int[] to = new int[] { R.id.name_item };

        scAdapter = new ItemListAdapter(getActivity(),R.layout.item_adapter,cursor,from,to,getCurrentLocation());
        lv = (ListView) getView().findViewById(R.id.item_list);
        lv.setAdapter(scAdapter);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                long id = cursor.getInt(cursor.getColumnIndex(InfoTable.COLUMN_ID));
                String category = cursor.getString(cursor.getColumnIndex(InfoTable.COLUMN_CATEGORY));
                Intent intent = new Intent(getActivity(), ItemActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("category", category);
                intent.putExtra("pos", i);
                startActivity(intent);
            }
        });
    }

     private Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String providerName = locationManager.getBestProvider(criteria, true);

         Location loc = locationManager.getLastKnownLocation(providerName);
        return loc;
    }

}
