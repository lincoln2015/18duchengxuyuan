package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.example.testurlactivity.NotificationSetttingActivity.ApacheHttpThread;
import com.example.testurlactivity.NotificationSetttingActivity.ApacheHttpThreadUpdateSetting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class AccountEmailSettingActivity extends Activity  implements OnTouchListener{
	
	// 手指向右滑动时的最小速度
	private static final int XSPEED_MIN = 200;
	// 手指向右滑动时的最小距离
	private static final int XDISTANCE_MIN = 150;
	// 记录手指按下时的横坐标。
	private float xDown;
	// 记录手指移动时的横坐标。
	private float xMove;
	// 用于计算手指滑动的速度。
	private VelocityTracker mVelocityTracker;
	
	

	int userID;

	static InputStream is = null;
	static String json2 = "";

	User user;

	CheckBox choseBoxPeopleFocus ;
	CheckBox choseBoxPeopleInvite ;
	CheckBox choseBoxNewReply;
	CheckBox choseBoxNewPersonalMessage ;
	CheckBox choseBoxQuestionEdit ;

	 
	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    
	         if (msg.what == 0x16) {
				Bundle bundle = msg.getData();

				// showTextView.setText(bundle.getString("text2"))
				
				
				if (user.getFollowMeChoise())
					choseBoxPeopleFocus.setChecked(false);
				else
					choseBoxPeopleFocus.setChecked(true);
				
				if (user.getQuestionInviteChoise())
					choseBoxPeopleInvite.setChecked(false);
				else
					choseBoxPeopleInvite.setChecked(true);
				
				if (user.getNewAnswerChoise())
					choseBoxNewReply.setChecked(false);
				else
					choseBoxNewReply.setChecked(true);
				
				if (user.getNewMessageChoise())
					choseBoxNewPersonalMessage.setChecked(false);
				else
					choseBoxNewPersonalMessage.setChecked(true);
				
				if (user.getQuestionModChoise())
					choseBoxQuestionEdit.setChecked(false);
				else
					choseBoxQuestionEdit.setChecked(true);
				
	         }
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();
					
		
	         }
	         
	    if (msg.what == 0x19) {
					Bundle bundle = msg.getData();
					
					//showTextView.setText(bundle.getString("text3"));

					Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
			
	          
	  }
	  
	         
	      
	        
	     }

	 };

	
	 View.OnClickListener onClickListener = new View.OnClickListener() {
	    
        public void onClick(View v) {
    
        if (v.getId() == R.id.answer_count) {
                
    		 	// finish();
	    		// new a thread to send to server to check user lgoin 
        		 finish();
           	           
         }

        if (v.getId() == R.id.submit_ly) {
                
        		new ApacheHttpThreadUpdateEmailSetting().start();
		 
        		finish();
           	           
         }
    	if (v.getId() == R.id.back_ly) {

			/*
			 * Toast.makeText(getApplicationContext(), "click add question",
			 * Toast.LENGTH_SHORT).show();
			 */
			// new a thread to update the qusetion item foucu_count
			finish();
			// 设置切换动画，从右边进入，左边退出
			overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
		}
        	
   
        } 

    };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
		

		setContentView(R.layout.account_email_setting);
	
		Intent intent = getIntent();
		
		
		SharedPreferences sharedPreferences = getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    userID = sharedPreferences.getInt("user_id", 1);
		
	    new ApacheHttpThread().start();
	    

		View submitLy = (View) findViewById(R.id.submit_ly);

		submitLy.setOnClickListener(onClickListener);
		
		
		choseBoxPeopleFocus = (CheckBox) findViewById(R.id.people_focus_checkbox);
		choseBoxPeopleInvite = (CheckBox) findViewById(R.id.people_invite_checkbox);
		choseBoxNewReply = (CheckBox) findViewById(R.id.new_reply_checkbox);
		choseBoxNewPersonalMessage = (CheckBox) findViewById(R.id.new_personal_message__checkbox);
		choseBoxQuestionEdit = (CheckBox) findViewById(R.id.question_edit_checkbox);
		
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.account_email_setting_ly);
		ll.setOnTouchListener(this);

		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		
		
		choseBoxPeopleFocus.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					if (choseBoxPeopleFocus.isChecked())
						user.setFollowMeChoise(false);
					else
						user.setFollowMeChoise(true);
				}
			});
	
		
	
	choseBoxPeopleInvite.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			if (choseBoxPeopleInvite.isChecked())
				user.setQuestionInviteChoise(false);
			else
				user.setQuestionInviteChoise(true);
				
		}
	});



	choseBoxNewReply.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		if (choseBoxNewReply.isChecked())
			user.setNewAnswerChoise(false);
		else
			user.setNewAnswerChoise(true);
	}
	});



	choseBoxNewPersonalMessage.setOnClickListener(new View.OnClickListener() {
		
	@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			if (choseBoxNewPersonalMessage.isChecked())
				user.setNewMessageChoise(false);
			else
				user.setNewMessageChoise(true);
		}
	});

	

	choseBoxQuestionEdit.setOnClickListener(new View.OnClickListener() {
		
	@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			if (choseBoxQuestionEdit.isChecked())
				user.setQuestionModChoise(false);
			else
				user.setQuestionModChoise(true);
			
		}
	});

}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
	
	public class ApacheHttpThreadUpdateEmailSetting extends Thread {
		String strs = "";

		public ApacheHttpThreadUpdateEmailSetting() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {

				HttpClient client = new DefaultHttpClient();
				// 如果是Get提交则创建HttpGet对象，否则创建HttpPost对象
				// HttpGet httpGet = new
				// HttpGet("http://192.168.1.100:8080/myweb/hello.jsp?username=abc&pwd=321");
				// post提交的方式
				// HttpPost httpPost = new
				// HttpPost("http://192.168.0.104/wecenter2/?/account/ajax/login_process/");
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl + "/test/ajax/privacy_email_setting/");
				HttpResponse httpResponse = null;
				// HttpPost httpPost = new
				// HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new
				// HttpPost("http://192.168.0.104/testlogin/index.php");
				// 如果是Post提交可以将参数封装到集合中传递
				
	
				String keyStr = "";
				
				if (!user.getFollowMeChoise())
					keyStr += "FOLLOW_ME,";
				if (!user.getQuestionInviteChoise())
					keyStr += "QUESTION_INVITE,";
				if (!user.getNewAnswerChoise())
					keyStr += "NEW_ANSWER,";
				if (!user.getNewMessageChoise())
					keyStr += "NEW_MESSAGE,";
				if (!user.getQuestionModChoise())
					keyStr += "QUESTION_MOD";
				
				List dataList = new ArrayList();

				dataList.add(new BasicNameValuePair("email_settings", keyStr));
				dataList.add(new BasicNameValuePair("current_uid", String.valueOf(userID)));

				// UrlEncodedFormEntity用于将集合转换为Entity对象
				httpPost.setEntity(new UrlEncodedFormEntity(dataList, HTTP.UTF_8));
				// 获取相应消息
				httpResponse = client.execute(httpPost);

				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();

				is = entity.getContent();

				new ApacheHttpThread().start();
				// ///////////////////*************////////////////////

				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					json2 = sb.toString();

					JSONObject json = new JSONObject(json2);

					Message msg = new Message();
					msg.what = 0x19;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					bundle.putString("text3", keyStr);

					msg.setData(bundle);

					myHandler.sendMessage(msg);

				} catch (Exception e) {
					Log.e("Buffer Error",
							"Error converting result " + e.toString());
					Log.d("json", json2.toString());
				}

				// 给点url来进行解析
				// webView.loadUrl(url);
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
	
	
	// 转载请说明出处：http://blog.csdn.net/ff20081528/article/details/17845753
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				createVelocityTracker(event);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					xDown = event.getRawX();
					Log.d("@@down", "xdown:"+xDown);
					break;
				case MotionEvent.ACTION_MOVE:
					xMove = event.getRawX();
					//活动的距离
					int distanceX = (int) (xMove - xDown);
					//获取顺时速度
					int xSpeed = getScrollVelocity();
					//当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity
					Log.d("@@move", "xmove:"+xMove +",speed :"+xSpeed);
					if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
						finish();
						//设置切换动画，从右边进入，左边退出
						overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.d("@@up", "xDown:"+xDown+ ",xMove ="+xMove);
					recycleVelocityTracker();
					break;
				default:
					break;
				}
				return true;
			}
			
			/**
			 * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
			 * 
			 * @param event
			 *        
			 */
			private void createVelocityTracker(MotionEvent event) {
				if (mVelocityTracker == null) {
					mVelocityTracker = VelocityTracker.obtain();
				}
				mVelocityTracker.addMovement(event);
			}
			
			/**
			 * 回收VelocityTracker对象。
			 */
			private void recycleVelocityTracker() {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			
			/**
			 * 获取手指在content界面滑动的速度。
			 * 
			 * @return 滑动速度，以每秒钟移动了多少像素值为单位。
			 */
			private int getScrollVelocity() {
				mVelocityTracker.computeCurrentVelocity(1000);
				int velocity = (int) mVelocityTracker.getXVelocity();
				return Math.abs(velocity);
			}

	
	

}
