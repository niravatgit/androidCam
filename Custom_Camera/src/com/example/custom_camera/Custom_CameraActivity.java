package com.example.custom_camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class Custom_CameraActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private Bitmap bitmap;
    TextView data;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom__camera);
        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mCameraPreview);

        
        Button captureButton = (Button) findViewById(R.id.button_capture);
        data = (TextView)findViewById(R.id.textView1);
        data.setText("ASDFF");
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	//mCamera.set
            	Camera.Parameters param = mCamera.getParameters();
            	List<Size> sizes = param.getSupportedPictureSizes();
            	
            	
            	int width=2000, height=2000;
            	for(int i=0;i<sizes.size();i++){
            		Size s = sizes.get(i);
            		if( s.width < width ){
            			width = s.width;
            			height = s.height;
            		}
            	}
            	param.setPictureSize(width, height);
            	param.setPreviewSize(width, height);
            	param.setPreviewFrameRate(20);
            	param.setRotation(90);
            	
            	mCamera.setParameters(param);
                mCamera.startPreview();
                FrameProcesser fp = new FrameProcesser(data);
            	while( true){
                mCamera.takePicture(null, null, fp);
                mCamera.startPreview();
                try {
        			Thread.sleep(70);
        		} catch (InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                data = (TextView)findViewById(R.id.textView1);
                	data.setText("asdasd");
            	}
            	/*Bitmap bitmap;
            	//View v1 = v.getRootView();
            	View v1 = mCameraPreview;
            	
            	
            	v1.setDrawingCacheEnabled(true);
            	bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            	ImageView img = new ImageView(getApplicationContext());
            	img.setImageBitmap(bitmap);
            	//setContentView(img);
            	v1.setDrawingCacheEnabled(false);
            	
            	FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.removeAllViews(); // addView(mCameraPreview);
                
                
                preview.addView(img);
            	
            	int a = 0;*/
                
            }
        });
    }

    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     * 
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    PictureCallback mPicture = new PictureCallback() {
    	TextView t;
    	void PictureCallback(TextView t){
    		this.t = t;
    	}
    	public void setTextView(TextView t){
    		this.t = t;
    	}
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
        	ByteArrayInputStream img = new ByteArrayInputStream(data);
        	bitmap = BitmapFactory.decodeStream(img);
        	long sum = 0;
        	for(int i=0;i<bitmap.getHeight();i++){
        		for(int j=0;j<bitmap.getWidth();j++){
        			sum = sum + (bitmap.getPixel(j, i) & 0x00FFFFFF);
        		}
        	}
        	t.setText(sum + ":");
        	//File pictureFile = getOutputMediaFile();
//            if (pictureFile == null) {
//                return;
//            }
//            try {
//                FileOutputStream fos = new FileOutputStream(pictureFile);
//                fos.write(data);
//                fos.close();
//            } catch (FileNotFoundException e) {
//
//            } catch (IOException e) {
//            }
        	//TextView t = new TextView(getApplicationContext());
        	//ImageView img2 = new ImageView(getApplicationContext());
        	//img2.setImageBitmap(bitmap);
        	//setContentView(img2);
        	//mCamera.takePicture(null, null, mPicture);
        }
    };

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }
}