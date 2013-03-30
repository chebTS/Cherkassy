package org.geekhub.cherkassy.activity;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.fragments.MapFragment;

public class MapActivity extends SherlockFragmentActivity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //My app doesn't make if i not set theme for this Activity
        setTheme(R.style.Sherlock___Theme_DarkActionBar);
		setContentView(R.layout.map_act);

		if (savedInstanceState == null) {
            getSupportFragmentManager()
            		.beginTransaction()
            		.add(R.id.map_frag, new MapFragment())
            		.commit();
        }
	}

	
}
