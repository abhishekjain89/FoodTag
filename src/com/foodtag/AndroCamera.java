package com.foodtag;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AndroCamera extends Activity {
	private static final int IMAGE_CAPTURE = 0;
	private static final int CAMERA_REQUEST = 1888; 
	private Button startBtn;
	private Uri imageUri;
	private ImageView imageView;

	/** Called when the activity is first created. 
	 *  sets the content and gets the references to 
	 *  the basic widgets on the screen like
	 *  {@code Button} or {@link ImageView}
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_picture);
		imageView = (ImageView)findViewById(R.id.img);
		
		imageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startCamera();
			}
		});
	}

	public void startCamera() {
		
		Log.d("ANDRO_CAMERA", "Starting camera on the phone...");
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		startActivityForResult(cameraIntent, CAMERA_REQUEST); 
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST) {
			Bitmap photo = (Bitmap) data.getExtras().get("data"); 
			imageView.setImageBitmap(photo);
		}
	}
}