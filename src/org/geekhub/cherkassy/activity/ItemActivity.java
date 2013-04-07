package org.geekhub.cherkassy.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.widget.IcsColorDrawable;
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
        getSupportActionBar().setBackgroundDrawable(new IcsColorDrawable(Color.parseColor("#5F9EA0")));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.item_frag, new ItemFragment())
                    .commit();
        }
    }

}
