package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread;
import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class userDetailActivity extends Activity  implements OnTouchListener {
	
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

	Button focusBtn;

	int userID;
	int currentUserId;

	String userName;
	String topicsCount;
	String fansCount;
	String friendsCount;

	String agreeCount;
	String thanksCount;
	String answeredCount;
	String questionedCount;

	static InputStream is = null;
	static String json2 = "";

	int USER_DETAIL_START_REQUEST = 1000;
	int USER_DETAIL_END_REQUEST = 1001;

	User user;
	ImageView userImg;
	String userAvatarFile = "";
	private Bitmap bitmap = null;

	View focusLy;
	ImageView focusImg;
	TextView focusTextView;

	int USER_EDIT_DETAIL_START_REQUEST = 1006;
	int USER_EDIT_DETAIL_START_END = 1007;
	
	
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    
	         if (msg.what == 0x16) {
	     				Bundle bundle = msg.getData();

	     				// showTextView.setText(bundle.getString("text2"));
	     				// Toast.makeText(getApplicationContext(), bundle.getString("text2") , Toast.LENGTH_LONG).show();
	                 
	         }
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();

					// showTextView.setText(bundle.getString("text2"))
			
					userImg.setImageBitmap(bitmap);
					
					userImg.setVisibility(View.VISIBLE);

			
		         }
	
	         if (msg.what == 0x18) {
  				Bundle bundle = msg.getData();

  				if (bundle.getString("check_result").equals("false"))
  				{
					Drawable  d = getResources().getDrawable(R.drawable.focus_grey_icon_grey_background);//找到你要加入的图片
					//	shareImg.setBackground(d);
					focusImg.setImageDrawable(d);
					focusTextView.setText("关注");		 
					// focusBtn.setText("关注");
  				}
  				else
  				{
  					Drawable  d = getResources().getDrawable(R.drawable.focus_grey_icon_blu_background);//找到你要加入的图片
					//	shareImg.setBackground(d);
					focusImg.setImageDrawable(d);
					focusTextView.setText("取消关注");
					
					// focusBtn.setText("取消关注");
	  				// Toast.makeText(getApplicationContext(), "check return :"+msg2 , Toast.LENGTH_LONG).show();
  				}
  				
  				new urlThreadGetImg().start();
              
	         }
	         if (msg.what == 0x19) {
	  				Bundle bundle = msg.getData();

	  				if (bundle.getString("action_result").equals("add"))
	  				{
	  				/*	fansCount = String.valueOf((Integer.valueOf(fansCount) + 1));
	  					fansCountTextView.setText(fansCount);
	  					focusBtn.setText("取消关注");*/
	  					
	  					fansCount = String.valueOf((Integer.valueOf(fansCount) + 1));
	  					fansCountTextView.setText(fansCount);
	  					Drawable  d = getResources().getDrawable(R.drawable.focus_grey_icon_blu_background);//找到你要加入的图片
						//	shareImg.setBackground(d);
						focusImg.setImageDrawable(d);
						focusTextView.setText("取消关注");
	  				}
	  				else
	  				{
	  				/*	fansCount = String.valueOf((Integer.valueOf(fansCount) - 1));
	  					fansCountTextView.setText(fansCount);
	  					focusBtn.setText("关注");*/
	  					
	  					fansCount = String.valueOf((Integer.valueOf(fansCount) - 1));
	  					fansCountTextView.setText(fansCount);
	  					Drawable  d = getResources().getDrawable(R.drawable.focus_grey_icon_grey_background);//找到你要加入的图片
						//	shareImg.setBackground(d);
						focusImg.setImageDrawable(d);
						focusTextView.setText("关注");		
	  				}
	  				// Toast.makeText(getApplicationContext(), "check return :"+msg2 , Toast.LENGTH_LONG).show();
	              
		         }
	         
	        
	     }

	 };

	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	   
	   
	        	  if (v.getId() == R.id.topic_ly) {
		            	 
		            	/* Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();*/
		            	 Intent intent = new Intent(getApplication(), UserFocusTopicActivity.class);
		            		                    	  
		            	  /* 通过Bundle对象存储需要传递的数据 */
		            	  Bundle bundle = new Bundle();
		            	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
		            	  bundle.putString("user_id", String.valueOf(userID));
		            	
		            	  /*把bundle对象assign给Intent*/
		            	  intent.putExtras(bundle);

		 				  startActivity(intent);
		 				  
		 				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		                }
		             if (v.getId() == R.id.focus_ly) {
			            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
			            	 Intent intent = new Intent(getApplication(), UserFriendUserActivity.class);
			       	
			            	  Bundle bundle = new Bundle();
			            	  
			            	  bundle.putString("user_id", String.valueOf(userID));
		       	
			            	  intent.putExtras(bundle);

			 				  startActivity(intent);
			 				  
			 				 overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				           }
			             
			             if (v.getId() == R.id.be_focuse_ly) {
				            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
				            	 Intent intent = new Intent(getApplication(), UserFansActivity.class);
				       	
				            	  Bundle bundle = new Bundle();
				            	  
				            	  bundle.putString("user_id", String.valueOf(userID));
			       	
				            	  intent.putExtras(bundle);

				 				  startActivity(intent);
				 				  
				 				 overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
					           }
		             
	             
	            
	             if (v.getId() == R.id.focus_count) {
	            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
	            	 Intent intent = new Intent(getApplication(), UserFriendUserActivity.class);
	       	
	            	  Bundle bundle = new Bundle();
	            	  
	            	  bundle.putString("user_id", String.valueOf(userID));
       	
	            	  intent.putExtras(bundle);

	 				  startActivity(intent);
	            	
		           }
	             
	             if (v.getId() == R.id.focused_count) {
		            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
		            	 Intent intent = new Intent(getApplication(), UserFansActivity.class);
		       	
		            	  Bundle bundle = new Bundle();
		            	  
		            	  bundle.putString("user_id", String.valueOf(userID));
	       	
		            	  intent.putExtras(bundle);

		 				  startActivity(intent);
		            	
			           }
	            if (v.getId() == R.id.questioned_layout_btn) {
		            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
		            	 Intent intent = new Intent(getApplication(), QuestionQuestionedActivity.class);
		       	
		            	  Bundle bundle = new Bundle();
		            	  
		            	  bundle.putString("user_id", String.valueOf(userID));
	       	
		            	  intent.putExtras(bundle);

		 				  startActivity(intent);
		            	
			       }
	             
	            if (v.getId() == R.id.questions_answered_layout_btn) {
	            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
	            	 Intent intent = new Intent(getApplication(), QuestionAnsweredActivity.class);
	       	
	            	  Bundle bundle = new Bundle();
	            	  
	            	  bundle.putString("user_id", String.valueOf(userID));
       	
	            	  intent.putExtras(bundle);

	 				  startActivity(intent);
	            	
		           }
	            
	            if (v.getId() == R.id.detail_layout_btn) {
	            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
	            	 Intent intent = new Intent(getApplication(), UserDetailForJudgeActivity.class);
	       	
				Bundle bundle = new Bundle();
				bundle.putSerializable("key", user);
				intent.putExtras(bundle);


				startActivity(intent);
	            	
		           }
	            
	            if (v.getId() == R.id.user_actions_layout_btn) {
				// Toast.makeText(getApplicationContext(), "click add question",
				// Toast.LENGTH_SHORT).show();
	        		
	        	SharedPreferences sharedPreferences = getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	              String currentID = String.valueOf(sharedPreferences.getInt("user_id", 1));
				Intent intent = new Intent(getApplication(),
						UserActionsActivity.class);

				Bundle bundle = new Bundle();

			// Toast.makeText(getApplicationContext(), "click action btn userID" +userID + "@@current_id:"+currentID, Toast.LENGTH_SHORT).show();
				bundle.putString("user_id", String.valueOf(userID));
				bundle.putString("user_name", userName);
				

				intent.putExtras(bundle);

				startActivity(intent);
	            	
		           }
             
	            if (v.getId() == R.id.foucus_ly) {
	
	            	new ApacheHttpThread().start();
     	
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
	        	 if (v.getId() == R.id.edit_ly) {

		  			 Intent intent = new Intent(getApplicationContext(), UserDetailEditActivity.class);
	 				 startActivityForResult(intent, USER_EDIT_DETAIL_START_REQUEST);
	 				 overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	 			  }

	            
	             
	        }

	    };


	public userDetailActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.user_detail_2);
		

	    SharedPreferences sharedPreferences = this.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    currentUserId = sharedPreferences.getInt("user_id", 1);
		
		Intent intent = getIntent();
		
		user =  (User) intent.getSerializableExtra("key"); 
		
		if (user != null)
		{
			userName = user.getUserName();
			userID = user.getUserId();
			topicsCount =String.valueOf(user.getTopicCount());
			fansCount = String.valueOf(user.getFansCount());
			friendsCount =String.valueOf( user.getFriendCount());
			agreeCount =String.valueOf( user.getAgreeCount());
			thanksCount =String.valueOf( user.getThanksCount());
			answeredCount = String.valueOf(user.getAnswerCount());
			questionedCount = String.valueOf(user.getQuestionCount());
			
			userAvatarFile = user.getAvatarFile().replace("localhost", URLHelper.IP_URL);
		}

		questionedLayoutBtn = (View) findViewById(R.id.questioned_layout_btn);
		questionAnsweredLayoutBtn = (View) findViewById(R.id.questions_answered_layout_btn);
		
		detailLayoutBtn =  (View) findViewById(R.id.detail_layout_btn);
		userActionsLayoutBtn =  (View) findViewById(R.id.user_actions_layout_btn);
		
		View topicsLy = (View) findViewById(R.id.topic_ly);
		View friendLy = (View)  findViewById(R.id.focus_ly);
		View fansLy = (View) findViewById(R.id.be_focuse_ly);

		userNameTextView = (TextView) findViewById(R.id.user_name);
		topicsCountTextView = (TextView) findViewById(R.id.topic_count);
		friendCountTextView = (TextView) findViewById(R.id.focus_count);
		fansCountTextView = (TextView) findViewById(R.id.focused_count);
		
		agreeCountTextView = (TextView) findViewById(R.id.agree_count);
		thanksCountTextView = (TextView) findViewById(R.id.thanks_count);
		
		answeredCountTextView = (TextView) findViewById(R.id.answered_count);
		questionedCountTextView = (TextView) findViewById(R.id.questioned_count);
		
		userImg = (ImageView) findViewById(R.id.user_img);
		userImg.setVisibility(View.GONE);
		
		
		userNameTextView.setText(userName);
		topicsCountTextView.setText(topicsCount);
		fansCountTextView.setText(fansCount);
		friendCountTextView.setText(friendsCount);
		
		agreeCountTextView.setText("获赞数  " + agreeCount);
		thanksCountTextView.setText("感谢数  " + thanksCount);
		
		answeredCountTextView.setText(answeredCount);
		questionedCountTextView.setText(questionedCount);

		questionedLayoutBtn.setOnClickListener(onClickListener);
		questionAnsweredLayoutBtn.setOnClickListener(onClickListener);
		detailLayoutBtn.setOnClickListener(onClickListener);
		userActionsLayoutBtn.setOnClickListener(onClickListener);

		topicsLy.setOnClickListener(onClickListener);
		friendLy.setOnClickListener(onClickListener);
		fansLy.setOnClickListener(onClickListener);
		
		focusLy = (View)findViewById(R.id.foucus_ly);
		focusImg = (ImageView)findViewById(R.id.focus_img);
		focusTextView  = (TextView) findViewById(R.id.focus_text);	
		if (userID == currentUserId)
		{
			focusLy.setVisibility(View.GONE);
		}
		else
		{
			focusLy.setOnClickListener(onClickListener);
		}

		LinearLayout ll = (LinearLayout) findViewById(R.id.user_detail_ly);
		ll.setOnTouchListener(this);

		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		View editView = (View) findViewById(R.id.edit_ly);
     	editView.setOnClickListener(onClickListener);
     	editView.setClickable(false);
     	TextView editText = (TextView) findViewById(R.id.edit);
     	editText.setVisibility(View.GONE);
     	
	
		new ApacheHttpThreadFollowCheck().start();
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub

		Intent intent = new Intent();
	

		// Toast.makeText(getApplicationContext(),"finish detail", Toast.LENGTH_SHORT).show();
		userDetailActivity.this.setResult(USER_DETAIL_END_REQUEST, intent);
		
		super.finish();
	}




	public class ApacheHttpThread extends Thread {
		String strs = "";
		int len;
		  JSONObject json;
		  Iterator it ;
		public ApacheHttpThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {


				HttpClient client = new DefaultHttpClient();
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/change_focus/?will_foucus_uid="+userID+"&current_uid="+currentUserId);
				// HttpGet httpGet = new HttpGet("http://192.168.0.104/wecenter2/?/explore/ajax/list/");
				// HttpGet httpGet = new HttpGet("http://192.168.0.104/wecenter2/?flagmobile=1");
				// 获取相应消息
				HttpResponse httpResponse = client.execute(httpGet);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				 
				is = entity.getContent();     
				// 把消息对象直接转换为字符串
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				
				
				/////////////////////*************////////////////////
				
				try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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
					bundle.putString("action_result", json.getString("value"));
									
					msg.setData(bundle);

					myHandler.sendMessage(msg);
			      
	          
	                
		          
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
	
	
	public class ApacheHttpThreadFollowCheck extends Thread {
		String strs = "";
		int len;
		  JSONObject json;
		  Iterator it ;
		public ApacheHttpThreadFollowCheck() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {


				HttpClient client = new DefaultHttpClient();
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/user_follow_check/?will_foucus_uid="+userID+"&current_uid="+currentUserId);
				// HttpGet httpGet = new HttpGet("http://192.168.0.104/wecenter2/?/explore/ajax/list/");
				// HttpGet httpGet = new HttpGet("http://192.168.0.104/wecenter2/?flagmobile=1");
				// 获取相应消息
				HttpResponse httpResponse = client.execute(httpGet);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				 
				is = entity.getContent();     
				// 把消息对象直接转换为字符串
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				
				
				/////////////////////*************////////////////////
				
				try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            is.close();
		            json2 = sb.toString();
		            
		        
		            JSONObject json = new JSONObject(json2); 
		            
		          
		          
		            Message msg = new Message();
					msg.what = 0x18;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					bundle.putString("check_result",   json.getString("value"));
									
					msg.setData(bundle);

					myHandler.sendMessage(msg);
			      
	          
	                
		          
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
