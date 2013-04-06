package org.geekhub.cherkassy.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.widget.IcsColorDrawable;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.db.DatabaseHelper;
import org.geekhub.cherkassy.db.InfoTable;
import org.geekhub.cherkassy.fragments.ItemFragment;

public class ItemActivity extends SherlockFragmentActivity {

    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;

    public static Cursor cursor;
    public static int id,pos;
    public static String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_act);
        getSupportActionBar().setBackgroundDrawable(new IcsColorDrawable(Color.parseColor("#5F9EA0")));
        id = getIntent().getIntExtra("ID",0);
        category = getIntent().getStringExtra("category");

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        cursor = database.query(InfoTable.TABLE_ITEMS, null, InfoTable.COLUMN_CATEGORY + "='" + category + "'", null,null,null,null);

        if (savedInstanceState == null) {
            mViewPager = (ViewPager) findViewById(R.id.viewpager);
            mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mMyFragmentPagerAdapter);
        }
    }
                                                                                                  //arraylist fragment
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            cursor.moveToNext();
            long id_item = cursor.getLong(cursor.getColumnIndex(InfoTable.COLUMN_ID));
            return ItemFragment.newInstance(id_item);
        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }
    }
}
