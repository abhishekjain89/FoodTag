package com.foodtag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class AndroCamera extends Activity {
	private static final int IMAGE_CAPTURE = 0;
	private static final int CAMERA_REQUEST = 1888; 
	private static final int GALLERY_REQUEST = 1889; 
	private Button startBtn;
	private Uri imageUri;
	private ImageView imageView;
	private Bitmap photo;
	private String message;
	private EditText editText;
	private Button shareButton;

	

	/** Called when the activity is first created. 
	 *  sets the content and gets the references to 
	 *  the basic widgets on the screen like
	 *  {@code Button} or {@link ImageView}
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_picture);
		imageView = (ImageView) findViewById(R.id.img);
		editText = (EditText) findViewById(R.id.editText1);
		shareButton = (Button) findViewById(R.id.share);

		imageView.requestFocus();
		checkShareStatus();
		imageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				imageView.setAlpha(128);
				takePictureOption();
				imageView.setAlpha(255);

			}
		});
		editText.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	
	        	
	        	checkShareStatus();
	        	
	        }

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
	       
	    }); 
	}
	
	public void checkShareStatus(){
		message = editText.getText().toString();
    	if(message.length()>0 && photo!=null){
    		shareButton.setVisibility(View.VISIBLE);
    	}
    	else{
    		shareButton.setVisibility(View.GONE);
    	}
    	
		
	}



	public void startCamera() {

		Log.d("ANDRO_CAMERA", "Starting camera on the phone...");
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(Environment.getExternalStorageDirectory(), "test.jpg");
		imageUri = Uri.fromFile(file);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

		startActivityForResult(cameraIntent, CAMERA_REQUEST); 
	}

	public void openGallery(){

		Intent cameraIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(cameraIntent, GALLERY_REQUEST); 

	}


	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		imageView.setAlpha(255);
		if(requestCode == CAMERA_REQUEST) {
			if(resultCode == RESULT_OK){

				try {
					if(photo!=null){
						photo.recycle();
						photo = null;			
					}
					photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

					imageView.setImageBitmap(photo);
					System.out.println(photo.getWidth()+ " " + photo.getHeight());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		}
		else if(requestCode==GALLERY_REQUEST){
			if(resultCode == RESULT_OK){
				try{
				imageUri = intent.getData();
				String[] filePathColumn = {MediaStore.Images.Media.DATA};

	            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            cursor.close();

	            photo = BitmapFactory.decodeFile(filePath);
	            imageView.setImageBitmap(photo);
				System.out.println(photo.getWidth()+ " " + photo.getHeight());
				} catch(Exception e){
					e.printStackTrace();
				}
	            
			}
		}
		
		checkShareStatus();
	}

	private void takePictureOption(){
		final CharSequence[] items = {"Album Gallery", "Camera",};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Take Picture");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				//Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
				if(item==1){
					startCamera();
				}else if(item==0){
					openGallery();
				}

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		
	}



}