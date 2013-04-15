package org.geekhub.cherkassy.helpers;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import org.geekhub.cherkassy.R;

public class ItemPageAdapter extends PagerAdapter{

    @Override
    public int getCount() {
        return 5;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == ((View) o);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ImageView mImg = new ImageView(container.getContext());


        mImg.setImageDrawable(container.getResources().getDrawable(R.drawable.mc));
        ((ViewPager) container).addView(mImg, 0);
        return mImg;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

}
