package org.geekhub.cherkassy.activity;

import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.fragments.ReportFragment;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ReportActivity extends SherlockFragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_act);
		
		if (savedInstanceState == null) {
            getSupportFragmentManager()
            		.beginTransaction()
            		.add(R.id.report_frag, new ReportFragment())
            		.commit();
        }
	}
	
	
}