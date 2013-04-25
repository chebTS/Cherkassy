package org.geekhub.cherkassy.helpers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.db.InfoTable;

import java.io.File;

public class ItemListAdapter extends SimpleCursorAdapter {

    private Cursor c;
    private Context context;
    private Location currLocation;

    public ItemListAdapter(Context context, int layout, Cursor c, String[] from, int[] to,Location loc) {
        super(context, layout, c, from, to);
        this.c = c;
        currLocation = loc;
        this.context = context;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_adapter, null);
        }

        this.c.moveToPosition(pos);
        String name = this.c.getString(this.c.getColumnIndex(InfoTable.COLUMN_NAME));
        double lati = this.c.getDouble(this.c.getColumnIndex(InfoTable.COLUMN_LATITUDE));
        double longi = this.c.getDouble(this.c.getColumnIndex(InfoTable.COLUMN_LONGITUDE));
        String imgLink = this.c.getString(this.c.getColumnIndex(InfoTable.COLUMN_LOGOURL));

        ImageView iv = (ImageView) v.findViewById(R.id.logo_item);
        if (imgLink != null) {

            File cacheDir = StorageUtils.getCacheDirectory(context);

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .memoryCacheExtraOptions(120, 80) // width, height
                    .discCacheExtraOptions(120, 80, Bitmap.CompressFormat.PNG, 75) // width, height, compress format, quality
                    .threadPoolSize(4)
                    .threadPriority(6)
                    .imageDownloader(new BaseImageDownloader(context))
                    .denyCacheImageMultipleSizesInMemory()
                    .offOutOfMemoryHandling()
                    .memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024)) // 2 Mb
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
            imageLoader.displayImage(imgLink, iv,options);

        }

        TextView tvname = (TextView) v.findViewById(R.id.name_item);
        tvname.setText(name);

        Location locationB = new Location("tem loc");
        locationB.setLatitude(lati);
        locationB.setLongitude(longi);
        double distance = currLocation.distanceTo(locationB);

        TextView tvdistance = (TextView) v.findViewById(R.id.distance_item);
        tvdistance.setText(getDistance(distance));

        return(v);
    }

    private String getDistance(Double distance) {
        String str;

        if (distance < 1000) {
           str = Double.toString(Math.round(distance)) + " meters";
        } else {
           Double kmdistance = Double.valueOf(Math.round(distance/100))/10;
           str = "~" +  Double.toString(kmdistance) + " km";
        }
        return str;
    }




}