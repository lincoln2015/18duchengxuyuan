package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.testurlactivity.UserDetailEditActivity.urlThreadGetImg;
import com.example.testurlactivity.UserLoginActivity.ApacheHttpThread;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetailFragment extends Fragment {

	TextView userNameTextView;
	TextView friendCountTextView;
	TextView fansCountTextView;
	TextView topicsCountTextView;

	TextView agreeCountTextView;
	TextView thanksCountTextView;

	TextView questionedCountTextView;
	TextView answeredCountTextView;

	View questionedLayoutBtn;
	View questionAnsweredLayoutBtn;
	View detailLayoutBtn;
	View userActionsLayoutBtn;

	int userID;

	String userName;
	String topicsCount;
	String fansCount;
	String friendsCount;

	String agreeCount;
	String thanksCount;
	String answeredCount;
	String questionedCount;

	User user;

	static InputStream is = null;
	static String json2 = "";

	Activity thisActivity;
	private View mRootView;

	ImageView userImg;

	String userAvatarFile = "";

	private Bitmap bitmap = null;

	SharedPreferences sharedPreferences;

	private View backView;
	private View editView;

	int USER_EDIT_DETAIL_START_REQUEST = 1006;
	int USER_EDIT_DETAIL_START_END = 1007;
	
	
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    
	         if (msg.what == 0x15) {
	     				Bundle bundle = msg.getData();

	     				// showTextView.setText(bundle.getString("text2"));
	    
	     				// Toast.makeText(getActivity(), bundle.getString("text2") , Toast.LENGTH_LONG).show();
	     			
	     				
	     				userName = user.getUserName();
	     				userID = user.getUserId();
	     				topicsCount =String.valueOf(user.getTopicCount());
	     				fansCount = String.valueOf(user.getFansCount());
	     				friendsCount =String.valueOf( user.getFriendCount());
	     				agreeCount =String.valueOf( user.getAgreeCount());
	     				thanksCount =String.valueOf( user.getThanksCount());
	     				answeredCount = String.valueOf(user.getAnswerCount());
	     				questionedCount = String.valueOf(user.getQuestionCount());
	     				
	     				userNameTextView.setText(userName);
	     				topicsCountTextView.setText(topicsCount);
	     				fansCountTextView.setText(fansCount);
	     				friendCountTextView.setText(friendsCount);
	     				
	     				
	     				agreeCountTextView.setText("获赞数  " + agreeCount);
	     				thanksCountTextView.setText("感谢数  " + thanksCount);
	     				
	     				answeredCountTextView.setText(answeredCount);
	     				questionedCountTextView.setText(questionedCount);
	     				
	     				
	     				userAvatarFile = user.getAvatarFile();
						userAvatarFile = userAvatarFile.replace("localhost", URLHelper.IP_URL);
						
						if (userAvatarFile != "")
							new urlThreadGetImg().start();
	                 
	         }
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();

					// showTextView.setText(bundle.getString("text2"))
			
					userImg.setImageBitmap(bitmap);
					
					userImg.setVisibility(View.VISIBLE);

			
		         }
	
	        
	     }

	 };

	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	   
	   
	             if (v.getId() == R.id.topic_ly) {
	            	 
	            	/* Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();*/
	            	 Intent intent = new Intent(thisActivity, UserFocusTopicActivity.class);
	            		                    	  
	            	  /* 通过Bundle对象存储需要传递的数据 */
	            	  Bundle bundle = new Bundle();
	            	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
	            	  bundle.putString("user_id", String.valueOf(userID));
	            	
	            	  /*把bundle对象assign给Intent*/
	            	  intent.putExtras(bundle);

	 				  startActivity(intent);
	 				  
	 				 thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	                }
	             if (v.getId() == R.id.focus_ly) {
		            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
		            	 Intent intent = new Intent(thisActivity, UserFriendUserActivity.class);
		       	
		            	  Bundle bundle = new Bundle();
		            	  
		            	  bundle.putString("user_id", String.valueOf(userID));
	       	
		            	  intent.putExtras(bundle);

		 				  startActivity(intent);
		 				  
		 				 thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			           }
		             
		             if (v.getId() == R.id.be_focuse_ly) {
			            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
			            	 Intent intent = new Intent(thisActivity, UserFansActivity.class);
			       	
			            	  Bundle bundle = new Bundle();
			            	  
			            	  bundle.putString("user_id", String.valueOf(userID));
		       	
			            	  intent.putExtras(bundle);

			 				  startActivity(intent);
			 				  
			 				 thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				           }
 
	            if (v.getId() == R.id.questioned_layout_btn) {
		            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
		            	 Intent intent = new Intent(thisActivity, QuestionQuestionedActivity.class);
		       	
		            	  Bundle bundle = new Bundle();
		            	  
		            	  bundle.putString("user_id", String.valueOf(userID));
	       	
		            	  intent.putExtras(bundle);

		 				  startActivity(intent);
		 				  thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			       }
	             
	            if (v.getId() == R.id.questions_answered_layout_btn) {
	            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
	            	 Intent intent = new Intent(thisActivity, QuestionAnsweredActivity.class);
	       	
	            	  Bundle bundle = new Bundle();
	            	  
	            	  bundle.putString("user_id", String.valueOf(userID));
       	
	            	  intent.putExtras(bundle);

	 				  startActivity(intent);
	 				  thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		           }
	            
	            if (v.getId() == R.id.detail_layout_btn) {
	            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
	            	 Intent intent = new Intent(thisActivity, UserDetailForJudgeActivity.class);
	       	
					Bundle bundle = new Bundle();
					bundle.putSerializable("key", user);
					intent.putExtras(bundle);

					startActivity(intent);
					thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);	
		           }
	            
	            if (v.getId() == R.id.user_actions_layout_btn) {
				// Toast.makeText(getApplicationContext(), "click add question",
				// Toast.LENGTH_SHORT).show();
		        	
					Intent intent = new Intent(thisActivity, UserActionsActivity.class);
	
					Bundle bundle = new Bundle();
	
					// Toast.makeText(thisActivity, "click action btn userID" +userID + "@@current_id:"+userID, Toast.LENGTH_SHORT).show();
					bundle.putString("user_id", String.valueOf(userID));
				
					intent.putExtras(bundle);
	
					startActivity(intent);
					
					thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	            	
		           }
	            if (v.getId() == R.id.back_ly) {
	 				
	 				TestURLActivity fca = (TestURLActivity) getActivity();
	 				fca.toggle();
	 			  }
	            if (v.getId() == R.id.edit_ly) {
	 				
		  			 Intent intent = new Intent(thisActivity, UserDetailEditActivity.class);
	 				 startActivityForResult(intent, USER_EDIT_DETAIL_START_REQUEST);
	 				 thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	 			  }
	             
	        }

	    };
	    
	    
	    @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			

			if(requestCode == USER_EDIT_DETAIL_START_REQUEST && resultCode == USER_EDIT_DETAIL_START_END)
	        {
				Fragment userDetailFragment = null;
				userDetailFragment = new UserDetailFragment();

				TestURLActivity fca = (TestURLActivity) getActivity();
				fca.switchContent(userDetailFragment);
	        }

			
		}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		
		super.onCreate(savedInstanceState);
		
		thisActivity =	getActivity();
		
		View view = inflater.inflate(R.layout.user_detail, container, false);
	    
	    mRootView = view;
	    
	    SharedPreferences sharedPreferences = thisActivity.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        userID = sharedPreferences.getInt("user_id", 1);
     
		questionedLayoutBtn = (View) mRootView.findViewById(R.id.questioned_layout_btn);
		questionAnsweredLayoutBtn = (View) mRootView.findViewById(R.id.questions_answered_layout_btn);
		
		detailLayoutBtn =  (View) mRootView.findViewById(R.id.detail_layout_btn);
		userActionsLayoutBtn =  (View) mRootView.findViewById(R.id.user_actions_layout_btn);
		
		View topicsLy = (View) mRootView.findViewById(R.id.topic_ly);
		View friendLy = (View)  mRootView.findViewById(R.id.focus_ly);
		View fansLy = (View)  mRootView.findViewById(R.id.be_focuse_ly);
		
		
		userNameTextView = (TextView) mRootView.findViewById(R.id.user_name);
		topicsCountTextView = (TextView) mRootView.findViewById(R.id.topic_count);
		friendCountTextView = (TextView)  mRootView.findViewById(R.id.focus_count);
		fansCountTextView = (TextView)  mRootView.findViewById(R.id.focused_count);
		
		agreeCountTextView = (TextView)  mRootView.findViewById(R.id.agree_count);
		thanksCountTextView = (TextView)  mRootView.findViewById(R.id.thanks_count);
		
		answeredCountTextView = (TextView)  mRootView.findViewById(R.id.answered_count);
		questionedCountTextView = (TextView)  mRootView.findViewById(R.id.questioned_count);
		
		userImg = (ImageView) view.findViewById(R.id.user_img);
		userImg.setVisibility(View.GONE);

		topicsLy.setOnClickListener(onClickListener);
		friendLy.setOnClickListener(onClickListener);
		fansLy.setOnClickListener(onClickListener);
		
		questionedLayoutBtn.setOnClickListener(onClickListener);
		questionAnsweredLayoutBtn.setOnClickListener(onClickListener);
		detailLayoutBtn.setOnClickListener(onClickListener);
		userActionsLayoutBtn.setOnClickListener(onClickListener);
		
        backView = (View) mRootView.findViewById(R.id.back_ly);
     	backView.setOnClickListener(onClickListener);
     	editView = (View) mRootView.findViewById(R.id.edit_ly);
     	editView.setOnClickListener(onClickListener);
     	
		
		 return mRootView;
	}
	
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onActivityCreated(savedInstanceState);
		
		 new ApacheHttpThread().start();	
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
				msg.what = 0x15;

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
			
			
			/////////////////////*************////////////////////

			
		
			
			
			// 给点url来进行解析
			// webView.loadUrl(url);
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
