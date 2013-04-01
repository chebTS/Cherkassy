package org.geekhub.cherkassy.activity;

import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.fragments.MapFragment;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.model.LatLng;



public class MapActivity extends SherlockFragmentActivity {
	private  LatLng issuePosition;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
        //My app doesn't make if i not set theme for this Activity
		//hm... ok.
        setTheme(R.style.Sherlock___Theme_DarkActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.map_act);
		if (savedInstanceState == null) {
            getSupportFragmentManager()
            		.beginTransaction()
            		.add(R.id.map_frag, new MapFragment())
            		.commit();
        }
		
	 
	}
	public LatLng getIssuePosition() {
		return issuePosition;
	}

	public void setIssuePosition(LatLng issuePosition) {
		this.issuePosition = issuePosition;
		Intent intent = new Intent();
	    intent.putExtra("lat", issuePosition.latitude);
	    intent.putExtra("lng", issuePosition.longitude);
	    setResult(RESULT_OK, intent);
	}
	
	//Just for fun... If You don`t like it - remove it))
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case android.R.id.home:
        	finish(); 
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
		}
	}
	
	
	
	
}
