package org.geekhub.cherkassy.Fragments;

import org.geekhub.cherkassy.R;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ReportFragment extends SherlockFragment implements OnClickListener{
	private static final int DIALOG_PHOTO  = 100;
	private static final int SELECT_CAMERA = 101;
	private static final int SELECT_GALERY = 102;
	ImageView imgPhoto;
	Button btnSend;
	SherlockFragmentActivity actReport;
	Uri imageUri;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		imgPhoto.setOnClickListener(this);
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
		case R.id.imgPhoto:
			break;
		default:
			break;
		}
	}
	
	
	private final class CameraOnClickListener implements DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
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
	}
	
	private final class GaleryOnClickListener implements DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, SELECT_GALERY);   
		}
	}
	
	private final class CancelOnClickListener implements DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}
}