package org.geekhub.cherkassy.helpers;

import java.util.List;

import org.geekhub.cherkassy.fragments.HomePagerFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomeAdapter extends FragmentPagerAdapter {
	List<Integer> drawables;
	
	
	public HomeAdapter(FragmentManager fm, List<Integer> drawables) {
		super(fm);
		this.drawables = drawables;
	}

	@Override
	public Fragment getItem(int position) {
		return HomePagerFragment.newInstance(position, drawables.get(position));
	}

	@Override
	public int getCount() {
		return drawables.size();
	}

	
	


}
