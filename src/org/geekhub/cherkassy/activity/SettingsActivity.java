package org.geekhub.cherkassy.activity;

import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.fragments.SettingsFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {  // hides keyboad on screen touch
	    View v = getCurrentFocus();
	    boolean ret = super.dispatchTouchEvent(event);
	    if (v instanceof EditText) {
	        View w = getCurrentFocus();
	        int scrcoords[] = new int[2];
	        w.getLocationOnScreen(scrcoords);
	        float x = event.getRawX() + w.getLeft() - scrcoords[0];
	        float y = event.getRawY() + w.getTop() - scrcoords[1];
	        if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) { 
	            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
	        }
	    }
	return ret;
	}

	
}
