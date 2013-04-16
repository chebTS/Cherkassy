package org.geekhub.cherkassy.fragments;


import org.geekhub.cherkassy.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class HomePagerFragment extends SherlockFragment {
	static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
	static final String ARGUMENT_DRAWABLE = "arg_drawable";
	int pageNumber;
	int drawable;
	
	static HomePagerFragment newInstance(int page, int drawable) {
		HomePagerFragment pageFragment = new HomePagerFragment();
	    Bundle arguments = new Bundle();
	    arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
	    arguments.putInt(ARGUMENT_DRAWABLE, drawable);
	    pageFragment.setArguments(arguments);
	    return pageFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
		drawable = getArguments().getInt(ARGUMENT_DRAWABLE);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.item_home_pager, null);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	
}
