package com.example.testurlactivity;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.example.testurlactivity.AccountEmailSettingActivity.ApacheHttpThread;
import com.example.testurlactivity.AccountEmailSettingActivity.ApacheHttpThreadUpdateEmailSetting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class testImgviewActivity extends Activity {
	 private ImageView imgeview;
	 private Bitmap bitmap;
	 Button downloadImg;
	 
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	
	        if (v.getId() == R.id.download_img) {
                
    		 	// finish();
	    		// new a thread to send to server to check user lgoin 
	        		new urlThread().start();
           	           
	        }
	        	
	   
	        } 

	    };
	    
	    
	    public Handler myHandler = new Handler() {
		     @Override
		     public void handleMessage(Message msg) {
		         if (msg.what == 0x14) {
		        	 imgeview.setImageBitmap(bitmap);          
		         }
		  
	 
		        
		     }

		 };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		

		setContentView(R.layout.test_imgview);

	    

	    imgeview = (ImageView) findViewById(R.id.img_view);

		
		
		downloadImg = (Button) findViewById(R.id.download_img);

		downloadImg.setOnClickListener(onClickListener);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	public class urlThread extends Thread {

		public urlThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// super.run();

			try {
				// URL url = new
				// URL("http://192.168.1.100:8080/myweb/image.jpg");
				
				//URL url = new URL("http://ecshopxax.sinaapp.com/favicon.ico");
				 URL url = new URL("http://192.168.42.38/wecenter/uploads/answer/20150826/0df3765e4af03f34f1802f682b9929a5.jpg");
				InputStream is = url.openStream();
				bitmap = BitmapFactory.decodeStream(is);

				Message msg = new Message();
				msg.what = 0x14;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}
	
	

}
