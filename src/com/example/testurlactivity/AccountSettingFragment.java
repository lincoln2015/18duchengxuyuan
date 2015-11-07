package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.example.testurlactivity.UserDetailEditActivity.urlThreadGetImg;
import com.example.testurlactivity.homeFragment.ApacheHttpThread;
import com.example.testurlactivity.homeFragment.httpUrlConnectionThread;
import com.example.testurlactivity.homeFragment.urlConnectThread;
import com.example.testurlactivity.homeFragment.urlThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;





public class AccountSettingFragment extends Fragment implements OnClickListener {
	
	int userID;
	String userName;
	
	User user;
	
	static InputStream is = null;
	static String json2 = "";
	
	Activity thisActivity;
	private View mRootView;
	
	SharedPreferences sharedPreferences;
	
	ImageView userImg;
	
	String userAvatarFile ="";
	
	 private Bitmap bitmap = null;
	 
	 TextView userNameView;
	 
	
	int USER_EDIT_DETAIL_START_REQUEST = 1006;
	int USER_EDIT_DETAIL_START_END = 1007;
	
	 private View backView;
	 
	 
	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    
	         if (msg.what == 0x17) {
				Bundle bundle = msg.getData();

				// showTextView.setText(bundle.getString("text2"))
		
				userImg.setImageBitmap(bitmap);
				
				userImg.setVisibility(View.VISIBLE);

		
	         }
	         if (msg.what == 0x16) {
					Bundle bundle = msg.getData();
					
					userName = user.getUserName();
					userNameView.setText(userName);

					userAvatarFile = user.getAvatarFile();
					userAvatarFile = userAvatarFile.replace("localhost", URLHelper.IP_URL);
					
					if (userAvatarFile != "" && userAvatarFile != "null")
						new urlThreadGetImg().start();
					else
						userImg.setVisibility(View.VISIBLE);
					
					
					//Toast.makeText(getApplicationContext(), userAvatarFile, Toast.LENGTH_SHORT).show();
	         }
	         
	    if (msg.what == 0x19) {
					Bundle bundle = msg.getData();
					
					//showTextView.setText(bundle.getString("text3"));

					// Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
	          
	  }
	  
	         
	      
	        
	     }

	 };

	 


	public AccountSettingFragment() {
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		
		
		// TODO Auto-generated method stub
		thisActivity =	getActivity();
		
		View view = inflater.inflate(R.layout.account_setting, container, false);
	    
	    mRootView = view;
	    
	    SharedPreferences sharedPreferences = thisActivity.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        userID = sharedPreferences.getInt("user_id", 1);
        
        // userName = sharedPreferences.getString("name", "");
  
		
        // mFavorTagList = new ArrayList<FavorTag>();
        
        
    	View exitBtn = (View) view.findViewById(R.id.exit_login);
    	
    	View notificationStingLyView = (View) view.findViewById(R.id.notification_seting_ly);
    	
    	View emailSetttingLyView = (View) view.findViewById(R.id.email_settting_ly);
    	
    	View accountProfileLyView = (View) view.findViewById(R.id.account_profile_ly);
    	
    	backView = (View) view.findViewById(R.id.back_ly);
	
    	
    	View weiboLyView = (View) view.findViewById(R.id.weibo_ly);
    	View qqLyView = (View) view.findViewById(R.id.qq_ly);
    	View emailLyView = (View) view.findViewById(R.id.email_ly);
    	
    	
    	
    	userNameView = (TextView) view.findViewById(R.id.user_name);
    	
    	userImg = (ImageView) view.findViewById(R.id.user_img);
    	
    	userImg.setVisibility(View.GONE);
  
    	new ApacheHttpThread().start();
    	

    	exitBtn.setOnClickListener(this);

    	notificationStingLyView.setOnClickListener(this);
    	emailSetttingLyView.setOnClickListener(this);
    	accountProfileLyView.setOnClickListener(this);
    	
    	weiboLyView.setOnClickListener(this);
    	qqLyView.setOnClickListener(this);
    	emailLyView.setOnClickListener(this);
    
    	
    	backView.setOnClickListener(this);
    	
		return mRootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		  if (v.getId() == R.id.exit_login) {
			 // new ApacheHttpThread().start();	
			  
			URLHelper.ISLOGIN = false;

/*			Intent intent = new Intent(thisActivity, UserLoginActivity.class);
			startActivity(intent);*/

			Fragment homeContent = null;
			homeContent = new homeFragment();

			TestURLActivity fca = (TestURLActivity) getActivity();
			fca.switchContent(homeContent);
			  
		  }
		  if (v.getId() == R.id.notification_seting_ly) {
				 // new ApacheHttpThread().start();	
			  Intent intent = new Intent(thisActivity, NotificationSetttingActivity.class);
			    
			  startActivity(intent);
				
			  thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				  
			  }
		  if (v.getId() == R.id.email_settting_ly) {
				 // new ApacheHttpThread().start();	
			  Intent intent = new Intent(thisActivity, AccountEmailSettingActivity.class);
			    
			  startActivity(intent);
			  
			  thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			  
				  
			  }
		  if (v.getId() == R.id.account_profile_ly) {
				 // new ApacheHttpThread().start();	
				  
			  Intent intent = new Intent(thisActivity, UserDetailEditActivity.class);
			 
			  startActivityForResult(intent, USER_EDIT_DETAIL_START_REQUEST);
		 
			  thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				  
			  }
		  if (v.getId() == R.id.weibo_ly) {
				 // new ApacheHttpThread().start();	
				  
			  	Toast.makeText(thisActivity, R.string.version_not_support, Toast.LENGTH_SHORT).show();
				  
			  }
		  if (v.getId() == R.id.qq_ly) {
				 // new ApacheHttpThread().start();	
				  
			  Toast.makeText(thisActivity, R.string.version_not_support, Toast.LENGTH_SHORT).show();
				  
			  }
		  if (v.getId() == R.id.email_ly) {
				 // new ApacheHttpThread().start();	
				  
			  	Toast.makeText(thisActivity, R.string.version_not_support, Toast.LENGTH_SHORT).show();
				  
			  }
		  if (v.getId() == R.id.back_ly) {
				
				TestURLActivity fca = (TestURLActivity) getActivity();
				fca.toggle();
			  }
		  
		  
		
	}
	

	 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		

		if(requestCode == USER_EDIT_DETAIL_START_REQUEST && resultCode == USER_EDIT_DETAIL_START_END)
        {
			Fragment accountSettingFragment = null;
			accountSettingFragment = new AccountSettingFragment();

			TestURLActivity fca = (TestURLActivity) getActivity();
			fca.switchContent(accountSettingFragment);
        }

		
	}

	
	public class ApacheHttpThread extends Thread {

		String strs = "";
		  JSONObject json;

		public ApacheHttpThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {
			 
				HttpClient client = new DefaultHttpClient();
				
				// may be save in localcondition would be better ,need to be consider deeply
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_current_user_info/?user_id="+userID);
				
				// 获取相应消息
				HttpResponse httpResponse = client.execute(httpGet);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				 
				is = entity.getContent();     
				
				
				/////////////////////*************////////////////////
				
				try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(
		                    is, "UTF-8"));
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            is.close();
		            json2 = sb.toString();
		            
		        
		          // JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		         json = new JSONObject(new JSONObject(sb.toString()).getString("value")); 
		         user  = new User(json);
		
				Message msg = new Message();
				msg.what = 0x16;

				Bundle bundle = new Bundle();
				bundle.clear();

				// bundle.putString("recv_server", new String(buffer));
				 bundle.putString("text1", json2);
				// bundle.putString("text1", content);

				msg.setData(bundle);

				myHandler.sendMessage(msg);


				// 给点url来进行解析
				// webView.loadUrl(url);
			} catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	            Log.d("json", json2.toString());
	        }

		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		}
	
	}
	
	
	
	   public class urlThreadGetImg extends Thread {
		   
			

				public urlThreadGetImg() {
					// TODO Auto-generated constructor stub
				
				}

				@Override
				public void run() {
				// TODO Auto-generated method stub
				// super.run();
				int i = 0;
				try {
					// URL url = new
					// URL("http://192.168.1.100:8080/myweb/image.jpg");

					// URL url = new
					// URL("http://ecshopxax.sinaapp.com/favicon.ico");
					
					

					URL url = new URL(userAvatarFile);
					InputStream is = url.openStream();
					bitmap = BitmapFactory.decodeStream(is);

					Message msg = new Message();
					msg.what = 0x17;

					myHandler.sendMessage(msg);

				} catch (Exception e) {

					e.printStackTrace();
				}

			}

			}
			
	
	

}
