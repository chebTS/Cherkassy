package org.geekhub.cherkassy.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.db.DatabaseHelper;
import org.geekhub.cherkassy.db.InfoTable;

public class ItemFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_frag,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        long item_id =  getActivity().getIntent().getLongExtra("ID", 0);

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(InfoTable.TABLE_ITEMS, null, InfoTable.COLUMN_ID + "='" + item_id + "'", null,null,null,null);
        cursor.moveToFirst();
        String category =  cursor.getString(cursor.getColumnIndex(InfoTable.COLUMN_NAME));

        TextView tw = (TextView) getView().findViewById(R.id.item_title);
        tw.setText(category);
        
    }
}
