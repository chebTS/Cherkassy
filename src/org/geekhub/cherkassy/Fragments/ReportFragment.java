package org.geekhub.cherkassy.Fragments;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.geekhub.cherkassy.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ReportFragment extends SherlockFragment implements OnClickListener{
	private static final int SELECT_CAMERA = 101;
	private static final int SELECT_GALERY = 102;
	ImageView imgPhoto;
	Button btnSend;
	SherlockFragmentActivity actReport;
	Uri imageUri;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		btnSend.setOnClickListener(this);
		actReport = getSherlockActivity();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.report_frag,container,false);
		imgPhoto = (ImageView)v.findViewById(R.id.imgPhoto);
		btnSend  = (Button)v.findViewById(R.id.btnSend);
        return v;
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSend:
			
			break;
		default:
			break;
		}
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.gallery:
				Log.i("Opt","Gallery");
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_GALERY);  
				break;
			case R.id.camera:
				Log.i("Opt","Camera");
				Log.d("ANDRO_CAMERA", "Starting camera on the phone...");
		        String fileName = "testphoto.jpg";
		        ContentValues values = new ContentValues();
		        values.put(MediaStore.Images.Media.TITLE, fileName);
		        values.put(MediaStore.Images.Media.DESCRIPTION,
		                "Image capture by camera");
		        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
		        imageUri = actReport.getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		        startActivityForResult(intent, SELECT_CAMERA);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.report_menu,menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) { 
	    case SELECT_GALERY:
	        if(resultCode == Activity.RESULT_OK){  	        	
	        	setImage(data.getData());	        	
	        }
	        break;
	    case SELECT_CAMERA:
	    	if(resultCode == Activity.RESULT_OK){ 	    		
	    		setImage(imageUri);	    		
	    	}
	    	break;
	    }
	}

	
	private Boolean setImage(Uri uri){
		try{
			if (((BitmapDrawable)imgPhoto.getDrawable())!= null)
					(((BitmapDrawable)imgPhoto.getDrawable()).getBitmap()).recycle();
    		InputStream imageStream = getSherlockActivity().getContentResolver().openInputStream(uri);
            Bitmap bmp = BitmapFactory.decodeStream(imageStream);
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            float k;
            if (width> height){
            	if (width> 2048){
            		k = width / height;
            		width = 2048;
            		height = (int)(2048 / k);
            		imgPhoto.setImageBitmap(Bitmap.createScaledBitmap(bmp, width, height, false));
            	}else{
            		imgPhoto.setImageBitmap(bmp);
            	}
            }else{
            	if (height > 2048){
            		k = height / width;
            		height = 2048;
            		width = (int)(2048 / k);
            		imgPhoto.setImageBitmap(Bitmap.createScaledBitmap(bmp, width, height, false));
            	}else{
            		imgPhoto.setImageBitmap(bmp);
            	}
            }
            //imgInit = true;
            return true;
		}catch (FileNotFoundException e) {
			Log.i("FileNotFound","FileNotFound");
			return false;
		}
	}

}