package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;








import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread2;

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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryQuestionsActivity extends Activity implements OnTouchListener {
	
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
	
	int categoryId;
	int userID;
	
	String orderFlag="";
	
	
	 static InputStream is = null;
	 static String json2 = "";
	 private ListView mMontactListView;


	 private ArrayAdapter<Question> mAdapter;
	 
	 private ArrayList<Question> mQuestionList;
	 
	 
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	   
	   
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
	 
	
	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	        
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();
					
					//showTextView.setText(bundle.getString("text3"));

						//Log.d("apche", bundle.getString("text3"))
				//	Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
				/*	webView.loadDataWithBaseURL(null, bundle.getString("text3"), "text/html",
							"utf-8", null);
					*/
					 mMontactListView = (ListView)findViewById(R.id.questionlistview);
			
					 mAdapter = new QuestionListAdapter(CategoryQuestionsActivity.this, R.layout.question_list_item, mQuestionList);


					 mMontactListView.setAdapter(mAdapter);

				
	         	}
	  
	         
	      
	        
	     }

	 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.all_category_question);
		
		
		Intent intent = getIntent();
		categoryId = Integer.valueOf(intent.getStringExtra("category_id"));
		orderFlag = intent.getStringExtra("order_flag");

		
		LinearLayout ll = (LinearLayout) findViewById(R.id.category_question_ly);
		ll.setOnTouchListener(this);

		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
        
		new ApacheHttpThread().start();
				
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
	
	
	public class ApacheHttpThread extends Thread {
		String strs = "";

		public ApacheHttpThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {

				// 通过webview来解析网页
				
				HttpClient client = new DefaultHttpClient();
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_category_questions/?category_id="+categoryId+"&order_flag="+orderFlag);
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
		            BufferedReader reader = new BufferedReader(new InputStreamReader(
		                    is, "UTF-8"));
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            is.close();
		            json2 = sb.toString();
		            
		         
		           JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		           
		           mQuestionList = new ArrayList<Question>();
		              
	                for(int i=0;i<json.length();i++) 
	                { 
	                    JSONObject jsonObject = (JSONObject)json.opt(i); 
	                    
	                    JSONObject qestionInfoJsonObject =  new JSONObject(jsonObject.getString("question_info"));
	                    Question question  = new Question(qestionInfoJsonObject);
	                    
	                    if (jsonObject.getString("question_newest_answer_info") != "false")
	                    {
	                    	JSONObject questionNewestAnswerInfoJsonObject =  new JSONObject(jsonObject.getString("question_newest_answer_info"));
	                    	  question.setNewestAnswer(questionNewestAnswerInfoJsonObject.getString("answer_content"));
	                    	  question.setNewestAnswerID(questionNewestAnswerInfoJsonObject.getInt("answer_id"));
	                    }
	                    else
	                    {
	                    	 question.setNewestAnswer("");
	                    }
	                    
	                    if (jsonObject.getString("question_publish_user_info") != "false")
	                    {
	                    	JSONObject qestionPublishUserInfoJsonObject =  new JSONObject(jsonObject.getString("question_publish_user_info"));
	                    	question.setUserName(qestionPublishUserInfoJsonObject.getString("user_name"));
	                    	
	                    	// add laterly for get publish user
	                    	question.setPublishUser(new User(qestionPublishUserInfoJsonObject) );
	                    }
	                    else
	                    {
	                    	 question.setUserName("");
	                    }
	                    
	                    if (jsonObject.getString("category_info") != "false")
	                    {
	                    	JSONObject categoryInfoJsonObject =  new JSONObject(jsonObject.getString("category_info"));
	                    	 question.setCategoryTitle(categoryInfoJsonObject.getString("title"));
	                     	 question.setCategoryId(categoryInfoJsonObject.getInt("id"));
	                    	 
	                    }
	                    else
	                    {
	                    	 question.setCategoryTitle("");
	                    }
	                    
	                
	                    
	                    mQuestionList.add(question);
	                } 
	                
		          
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		            Log.d("json", json2.toString());
		        }
				
				
				/////////////////////*************////////////////////

				
				Message msg = new Message();
				msg.what = 0x17;

				Bundle bundle = new Bundle();
				bundle.clear();

				// bundle.putString("recv_server", new String(buffer));
				bundle.putString("text3", json2.toString());
			

				msg.setData(bundle);

				myHandler.sendMessage(msg);
				
				
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
