package org.geekhub.cherkassy.Activity;

import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.Fragments.CategoryListFragment;
import org.geekhub.cherkassy.Fragments.SettingsFragment;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class SettingsActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_act);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
            		.beginTransaction()
            		.add(R.id.settings_frag, new SettingsFragment())
            		.commit();
        }
	}

	
}
