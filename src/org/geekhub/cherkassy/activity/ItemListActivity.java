package org.geekhub.cherkassy.activity;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.fragments.ItemListFragment;

public class ItemListActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_act);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.item_list_frag, new ItemListFragment())
                    .commit();
        }
    }

}