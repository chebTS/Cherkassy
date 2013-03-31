package org.geekhub.cherkassy.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.db.DatabaseHelper;
import org.geekhub.cherkassy.db.InfoTable;

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
        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(getActivity(),R.layout.itemlist_adapter,cursor,new String[]{InfoTable.COLUMN_NAME},new int[]{R.id.item_text});
        lv = (ListView) getView().findViewById(R.id.item_list);
        lv.setAdapter(scAdapter);
    }
}
