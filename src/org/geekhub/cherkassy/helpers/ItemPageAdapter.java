package org.geekhub.cherkassy.helpers;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.db.ImageTable;

import java.io.File;

public class ItemPageAdapter extends PagerAdapter {

    private Cursor c;
    private Context context;
    private LayoutInflater inflater;

    public ItemPageAdapter(Context context,Cursor cursor, LayoutInflater inflater) {
        super();
        c=cursor;
        this.context = context;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return c.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == ((View) o);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);

        String imgLink = c.getString(c.getColumnIndex(ImageTable.COLUMN_URL));
        displayImg(view,imgLink,imageView);
        ((ViewPager) view).addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    public void displayImg(View v,String imgLink,ImageView iv)
    {
        if (imgLink != null) {

            File cacheDir = StorageUtils.getCacheDirectory(context);

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .threadPoolSize(4)
                    .threadPriority(6)
                    .imageDownloader(new BaseImageDownloader(context))
                    .denyCacheImageMultipleSizesInMemory()
                    .offOutOfMemoryHandling()
                    .memoryCache(new UsingFreqLimitedMemoryCache(10 * 1024 * 1024)) // 2 Mb
                    .discCache(new UnlimitedDiscCache(cacheDir))
                    .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                    .enableLogging()
                    .build();

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.ck_logo)
                    .showImageForEmptyUri(R.drawable.ck_logo)
                    .showImageOnFail(R.drawable.ck_logo)
                    .cacheInMemory()
                    .cacheOnDisc()
                    .build();

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
            imageLoader.displayImage(imgLink,iv,options);
    }

    }
}
