package org.geekhub.cherkassy.fragments;

import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.objects.Const;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;

public class SettingsFragment extends SherlockFragment {
	private EditText edtNick;
	private SharedPreferences nickPref;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_frag, container, false);
        edtNick = (EditText)v.findViewById(R.id.edtNick);
        nickPref = getSherlockActivity().getSharedPreferences(Const.NICK_TAG, 0);
        edtNick.setText(nickPref.getString(Const.NICK_TAG, "Unknown"));        
		return v;
    }

	@Override
	public void onStop() {
		super.onStop();
		nickPref = getSherlockActivity().getSharedPreferences(Const.NICK_TAG, 0);
		nickPref.edit().putString(Const.NICK_TAG, edtNick.getText().toString()).commit();
	}
}
