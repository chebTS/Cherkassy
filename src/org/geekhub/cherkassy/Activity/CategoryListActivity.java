package org.geekhub.cherkassy.Activity;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.geekhub.cherkassy.Fragments.CategoryListFragment;
import org.geekhub.cherkassy.R;

public class CategoryListActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_list_act);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.category_list_frag, new CategoryListFragment()).commit();
        }
    }



}
