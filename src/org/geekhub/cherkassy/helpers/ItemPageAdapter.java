package org.geekhub.cherkassy.helpers;

import android.content.Context;
import android.graphics.Bitmap;
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

import java.io.File;
import java.util.List;

public class ItemPageAdapter extends PagerAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public ItemPageAdapter(Context context,List<String> list, LayoutInflater inflater) {
        super();
        this.list = list;
        this.context = context;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == ((View) o);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);


        displayImg(view,list.get(position),imageView);
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
                    .memoryCacheExtraOptions(600, 200) // width, height
                    .discCacheExtraOptions(600, 200, Bitmap.CompressFormat.PNG, 75) // width, height, compress format, quality
                    .imageDownloader(new BaseImageDownloader(context))
                    .denyCacheImageMultipleSizesInMemory()
                    .threadPoolSize(4)
                    .threadPriority(6)
                    .offOutOfMemoryHandling()
                    .memoryCache(new UsingFreqLimitedMemoryCache(10 * 1024 * 1024)) // 10 Mb
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
                   // .bitmapConfig(Bitmap.Config.RGB_565)
                    .cacheOnDisc()
                    .build();

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
            imageLoader.displayImage(imgLink,iv,options);
    }

    }
}
