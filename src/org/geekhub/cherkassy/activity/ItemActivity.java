package org.geekhub.cherkassy.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.fragments.ItemFragment;

public class ItemActivity extends SherlockFragmentActivity {

    private ViewPager mViewPager;

    public static Cursor cursor;
    public static int id,pos;
    public static String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_act);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.item_frag, new ItemFragment())
                    .commit();
        }
    }

}
