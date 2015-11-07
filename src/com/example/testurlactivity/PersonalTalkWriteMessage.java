package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.example.testurlactivity.WritePersonalTalk.ApacheHttpThreadUser;
import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PersonalTalkWriteMessage extends Activity implements OnTouchListener{
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
	
	
	
	ArrayList<InboxMessage> mInboxMessageList;

	ListView mListView;

	InboxMessageListAdapter mInboxMessageAdapter;

	int userID;
	int currentUid;
	int userTalkToUid;
	String message;
	String userName;
	
	String userNameTalkTo;

	User user;

	static InputStream is = null;
	static String json2 = "";

	Activity thisActivity;
	private View mRootView;

	EditText editTV;

	SharedPreferences sharedPreferences;

	private Bitmap bitmap;

	ArrayList<Bitmap> bitMapList;
	
	TextView pepleTalkToView;
	 
	
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	   
	   
	             if (v.getId() == R.id.send) {
	            	 
	            	// Toast.makeText(getApplicationContext(), "click send message", Toast.LENGTH_SHORT).show();
	            	 
	            	 message = editTV.getText().toString();
	            	
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
	             
	             if (v.getId() == R.id.delete_ly) {

	            	 Toast.makeText(getApplicationContext(),  getString(R.string.version_not_support) +"删除对话", Toast.LENGTH_SHORT).show();
		 		}
	             
	        }

	    };
		
	  
		
	public Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			if (msg.what == 0x17) {

				 // Toast.makeText(getApplicationContext(), bundle.getString("text3") ,Toast.LENGTH_LONG).show();
				
				new urlThreadGetImg().start();
				

				mListView = (ListView)findViewById(R.id.message_list);

				 // userlist adapter 
		
				mInboxMessageAdapter = new InboxMessageListAdapter(getApplicationContext(), R.layout.inbox_message_item, mInboxMessageList);
			
			
			}
			if (msg.what == 0x18)
			{
				// mInboxMessageAdapter.updateListView(mInboxMessageList);

				mListView.setAdapter(mInboxMessageAdapter);

				mListView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// Toast.makeText(getApplicationContext(),
						// String.valueOf(mAdapter.getItem(arg2).getQuestionId()),
						// Toast.LENGTH_LONG).show();

						/*
						 * 
						 * Intent intent = new Intent(getApplicationContext(),
						 * PersonalTalkWriteMessage.class);
						 * 
						 * 
						 * intent.putExtra("user_id",String.valueOf(mUserAdapter.
						 * getItem(arg2).getUserId()));
						 * 
						 * startActivity(intent);
						 */

					}
				});
			}
			

		}

	};

	public PersonalTalkWriteMessage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.write_message);
		
	    SharedPreferences sharedPreferences = getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    currentUid = sharedPreferences.getInt("user_id", 1);
	    
	    Intent intent = getIntent();
	    userTalkToUid = Integer.valueOf(intent.getStringExtra("user_id"));
	    userNameTalkTo = intent.getStringExtra("user_name");
	    
		

		View send = findViewById(R.id.send);
		send.setOnClickListener(onClickListener);
		
		pepleTalkToView = (TextView) findViewById(R.id.peple_talk_to);
		pepleTalkToView.setText(userNameTalkTo);
		

        
        editTV = (EditText) findViewById(R.id.write_message_edit);
        
        
        bitMapList = new ArrayList<Bitmap> ();
        
        
    	LinearLayout ll = (LinearLayout) findViewById(R.id.personal_talk_writemessage_ly);
		ll.setOnTouchListener(this);

		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		
		LinearLayout deleteLy = (LinearLayout) findViewById(R.id.delete_ly);
		deleteLy.setOnClickListener(onClickListener);
		
        
        new ApacheHttpThreadMessage().start();	

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
	
	
	public class ApacheHttpThread extends Thread {
		String strs = "";
		int intviteUid;

		public ApacheHttpThread() {
	
		}

		@Override
		public void run() {
			try {

				HttpClient client = new DefaultHttpClient();
				
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/send_inboxmessage/");
			
				List dataList = new ArrayList();
			
				dataList.add(new BasicNameValuePair("current_uid",  String.valueOf(currentUid)));
				dataList.add(new BasicNameValuePair("user_talk_to_uid", String.valueOf(userTalkToUid)));
				dataList.add(new BasicNameValuePair("message", message));
				

				// UrlEncodedFormEntity用于将集合转换为Entity对象
				httpPost.setEntity(new UrlEncodedFormEntity(dataList, HTTP.UTF_8));
				// 获取相应消息
				HttpResponse httpResponse = client.execute(httpPost);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				
				is = entity.getContent();     
				
				
			    new ApacheHttpThreadMessage().start();	
			
				/*
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
		            
		           
		            JSONObject json = new JSONObject(json2);
		    
		             if (json.getInt("ask_self_reply") == 1)
		            {
		            	// Toast.makeText(getApplicationContext(), "ask self answer", Toast.LENGTH_SHORT).show();
		            	
		               Message msg = new Message();
						msg.what = ASK_SELF_ID;

						Bundle bundle = new Bundle();
						bundle.clear();

					
						 bundle.putString("ASK_SELF_ID","ask self answer");
										
						msg.setData(bundle);

						myHandler.sendMessage(msg);
			          
		            }
		           
		        
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		            Log.d("json", json2.toString());
		        }
				
				*/ 
	

				// 给点url来进行解析
				// webView.loadUrl(url);
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
	
	

	public class ApacheHttpThreadMessage extends Thread {
		String strs = "";
		int len;
		  JSONObject json;
		  JSONObject rootJson;
		  Iterator it ;
		  String searchQuestionName;
		public ApacheHttpThreadMessage() {
			// TODO Auto-generated constructor stub
			// this.searchQuestionName = searchName;
		}

		@Override
		public void run() {
			try {


				HttpClient client = new DefaultHttpClient();
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_message_list/?sender_uid="+currentUid+"&recipient_uid="+userTalkToUid);
			
				HttpResponse httpResponse = client.execute(httpGet);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				 
				is = entity.getContent();     
				
	
				
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
		            
		        String tempStr ="";
		          // JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		         // JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		        	mInboxMessageList = new ArrayList<InboxMessage>();
					rootJson = new JSONObject(sb.toString());
					if (rootJson.has("value")) {
						json = new JSONObject(rootJson.getString("value"));
						len = json.length();

						it = json.keys();

					
						while (it.hasNext()) {// 遍历JSONObject
							JSONObject messageJsonObject = new JSONObject(
									json.getString(it.next().toString()));
							InboxMessage inboxMessage = new InboxMessage(messageJsonObject);
							
							if (messageJsonObject.getInt("uid") == currentUid)
								inboxMessage.setIsSelfMessage(1);
							else
								inboxMessage.setIsSelfMessage(0);

							mInboxMessageList.add(inboxMessage);
						

						}
						
	
						if (mInboxMessageList.size() > 0) {
							Message msg = new Message();
							msg.what = 0x17;

							Bundle bundle = new Bundle();
							bundle.clear();

							// bundle.putString("recv_server", new
							// String(buffer));
							bundle.putString("text3", json2);

							msg.setData(bundle);

							myHandler.sendMessage(msg);

						}
					}
					else 
					{
						Message msg = new Message();
						msg.what = 0x16;

						myHandler.sendMessage(msg);

					}
		                
		
		          
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
				
				

				while (i < mInboxMessageList.size())
				{
					if (mInboxMessageList.get(i).getAvatarFile() !="")
					{
						URL url = new URL(mInboxMessageList.get(i).getAvatarFile().replace("localhost", URLHelper.IP_URL));
						InputStream is = url.openStream();
						bitmap = BitmapFactory.decodeStream(is);
						
						bitMapList.add(bitmap);
					}
					else
						bitMapList.add(null);
					
					i++;
				}
			
				mInboxMessageAdapter.updateListView(mInboxMessageList ,bitMapList);
				
				Message msg = new Message();
				msg.what = 0x18;

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
