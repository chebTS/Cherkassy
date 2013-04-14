package org.geekhub.cherkassy.fragments;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.geekhub.cherkassy.R;
import org.geekhub.cherkassy.activity.MapActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ReportFragment extends SherlockFragment implements OnClickListener{
	private static final int SELECT_CAMERA = 101;
	private static final int SELECT_GALERY = 102;
	private static final int SELECT_MAP = 103;
	ImageView imgPhoto;
	TextView txtLatLng;
	Button btnSend;
	EditText edtDescription;
	SherlockFragmentActivity actReport;
	Uri imageUri;
	Double lat, lng;
	AlertDialog.Builder ad;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		btnSend.setOnClickListener(this);
		imgPhoto.setOnClickListener(this);
		actReport = getSherlockActivity();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.report_frag,container,false);
		imgPhoto = (ImageView)v.findViewById(R.id.imgPhoto);
		btnSend  = (Button)v.findViewById(R.id.btnSend);
		txtLatLng = (TextView)v.findViewById(R.id.txtLatLng);
		edtDescription = (EditText)v.findViewById(R.id.edtDescription);
        return v;
    }
	
	//http://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a/2033124#2033124
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSend:	
			if ((lat==null)||(lng==null)){
				Toast.makeText(getSherlockActivity(), "Please set issue location", Toast.LENGTH_LONG).show();
			}else{
				String aEmailList[] = { "chebTS@gmail.com" }; 
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); //This is the email intent
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList); // adds the address to the intent
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Cherkassy issue");//the subject				 
				emailIntent.setType("plain/text");				 
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "On location " + txtLatLng.getText().toString()+
						"\nThere is an issue \n"
						+ edtDescription.getText().toString()
						); 
				//TODO add picture
				startActivity(emailIntent);
				//TODO reset text, imageview and coordinates;
			}
			break;
		case R.id.imgPhoto:
			ad = new AlertDialog.Builder(getSherlockActivity());
			ad.setTitle("Choose source");  // заголовок
			ad.setMessage("Camera or gallery"); // сообщение
			ad.setCancelable(true);
			ad.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					openCamera();
				}
			});
			ad.setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					openGallery();
				}
			});
			ad.show();
			break;
		default:
			break;
		}
	}	
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.gallery:
				openGallery();
//				Log.i("Opt","Gallery");
//				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//				photoPickerIntent.setType("image/*");
//				startActivityForResult(photoPickerIntent, SELECT_GALERY);  
				break;
			case R.id.camera:
				openCamera();
//				Log.i("Opt","Camera");
//				Log.d("ANDRO_CAMERA", "Starting camera on the phone...");
//		        String fileName = "testphoto.jpg";
//		        ContentValues values = new ContentValues();
//		        values.put(MediaStore.Images.Media.TITLE, fileName);
//		        values.put(MediaStore.Images.Media.DESCRIPTION,
//		                "Image capture by camera");
//		        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//		        imageUri = actReport.getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//		        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//		        startActivityForResult(intent, SELECT_CAMERA);
				break;
			case R.id.map:
				startActivityForResult(new Intent(getSherlockActivity(), MapActivity.class), SELECT_MAP);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void openGallery(){
		Log.i("Opt","Gallery");
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_GALERY); 
	}
	
	private void openCamera(){
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
	    case SELECT_MAP:
	    	Log.i("Select map result","__");
	    	
	    	if (data!=null){
	    		if(data.hasExtra("lat")&&(data.hasExtra("lng"))){
	    			lat = data.getExtras().getDouble("lat");
	    			lng = data.getExtras().getDouble("lng");
	    			txtLatLng.setText(lat.toString() + " : " +lng.toString());
	    		}
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