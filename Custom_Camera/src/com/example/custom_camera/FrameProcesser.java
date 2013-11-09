package com.example.custom_camera;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaFormat;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class FrameProcesser implements Camera.PictureCallback{
	TextView t;
	public FrameProcesser(TextView t) {
		// TODO Auto-generated constructor stub
		this.t = t;
	}
	@Override
	public void onPictureTaken(byte[] image, Camera cam) {
      	ByteArrayInputStream img = new ByteArrayInputStream(image);
    	Bitmap bitmap = BitmapFactory.decodeStream(img);
    	long sum = 0;
    	for(int i=0;i<bitmap.getHeight();i++){
    		for(int j=0;j<bitmap.getWidth();j++){
    			sum = sum + (bitmap.getPixel(j, i) & 0x00FFFFFF);
    		}
    	}
    	t.setText(sum + ":");	
		/*try {
			FileOutputStream fos = new FileOutputStream("/storage/emulated/0/DCIM/Camera/test.jpg");
			fos.write(image);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	//	ByteArrayInputStream imgData = new ByteArrayInputStream(image);
	//	Bitmap bmp = BitmapFactory.decodeStream(imgData);
	//	Log.i("BMP", "BMP");
	}

}
