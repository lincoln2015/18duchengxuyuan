package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.testurlactivity.UserLoginActivity.ApacheHttpThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class QuestionFocusUserActivity extends Activity {
	
	TextView answerCountTextView;
	
	 static InputStream is = null;
	 static String json2 = "";
	 
	private String questionID; 
	
	 private ArrayList<User> mUserList;
	 
	 private ListView mUserListView;
	 
	 private View mRootView;
	 private ArrayAdapter<User> mAdapter;
	 
		int userID;
		int currentUserId;
		
 		 
  		int USER_DETAIL_START_REQUEST = 1000;
		int USER_DETAIL_END_REQUEST = 1001;
	 	  
	 
	
	public QuestionFocusUserActivity() {
		// TODO Auto-generated constructor stub
	}
	
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    
	         if (msg.what == 0x16) {
	     				Bundle bundle = msg.getData();

	     			//	showTextView.setText(bundle.getString("text2"));
	                 
	         }
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();
					
					//showTextView.setText(bundle.getString("text3"));

						//Log.d("apche", bundle.getString("text3"))
					//	Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
				/*	webView.loadDataWithBaseURL(null, bundle.getString("text3"), "text/html",
							"utf-8", null);
					*/
					 mUserListView = (ListView)findViewById(R.id.user_list);
						
					// int question list
					  
					 // bind 
					mAdapter = new UserListAdapter(getApplicationContext(), R.layout.question_focus_userlist_item, mUserList);


					 //set adapter	
					 mUserListView.setAdapter(mAdapter);
					 
					 

					 mUserListView.setOnItemClickListener(new OnItemClickListener(){

						 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
						 		//Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
						 	
						 		
						 		if (currentUserId ==  mAdapter.getItem(arg2).getUserId() )
						 		{
						 			Toast.makeText(getApplicationContext(), "yourself", Toast.LENGTH_LONG).show();
						 		}
						 		else
						 		{
						 			Intent intent = new Intent(getApplicationContext(),
							 				userDetailActivity.class);
									/*intent.putExtra("question_content",mAdapter.getItem(arg2).getContent());
									intent.putExtra("question_detail",mAdapter.getItem(arg2).getQuestionDetail());
									intent.putExtra("question_id",String.valueOf(mAdapter.getItem(arg2).getQuestionId()));
									intent.putExtra("answer_count",String.valueOf(mAdapter.getItem(arg2).getAnswerCount()));
							 		startActivity(intent);*/
									
							 		Bundle mBundle = new Bundle();    
							  		 mBundle.putSerializable("key", mAdapter.getItem(arg2));    
							  		 intent.putExtras(mBundle);    
						
							  		startActivityForResult(intent,USER_DETAIL_START_REQUEST);
							 	
						 		}
						 		
						 		
								
						  		
						  	
						 		
						 		/// Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getFansCount()), Toast.LENGTH_LONG).show();
						  		
						 		
							
						 	
						 	}
						 });
					
	          
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
		
		
		setContentView(R.layout.question_focus_userlist);
		
		
	    SharedPreferences sharedPreferences = getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    currentUserId = sharedPreferences.getInt("user_id", 1);
		
/*		answerCountTextView = (TextView) findViewById(R.id.answer_count);

		answerCountTextView.setOnClickListener(onClickListener);
		*/
		
	    LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);

		
		Intent intent = getIntent();
		questionID = intent.getStringExtra("question_id");

	    
	    new ApacheHttpThread().start();	

	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		

	 	  
		
		if(requestCode == USER_DETAIL_START_REQUEST && resultCode == USER_DETAIL_END_REQUEST)
        {
			new ApacheHttpThread().start();
        }
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
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_focus_users/?question_id="+questionID);
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
		            
		            mUserList = new ArrayList<User>();
		          // JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		            json = new JSONObject(new JSONObject(sb.toString()).getString("value")); 
		           len = json.length();
		           
		           
		            it = json.keys();
		         

		           
		           while(it.hasNext()){//遍历JSONObject
		        	   JSONObject userJsonObject =  new JSONObject(json.getString(it.next().toString()));
		        	   User user  = new User(userJsonObject);
			        
		        	   mUserList.add(user);
		        	   
		        	 	
		        	
		           }
		              
		           Message msg = new Message();
					msg.what = 0x17;
	
					Bundle bundle = new Bundle();
					bundle.clear();
	
					// bundle.putString("recv_server", new String(buffer));
					// bundle.putString("text3",String.valueOf(len));
					bundle.putString("text3",it.toString());
					
					msg.setData(bundle);
	
					myHandler.sendMessage(msg); 
			      
	             /*   for(int i=0;i<json.length();i++) 
	                { 
	                   //  JSONObject jsonObject = (JSONObject)json.opt(i); 
	                	json.get
	                    
	                    JSONObject userJsonObject =  new JSONObject(json.getString(String.valueOf(i + 1)));
	                    User user  = new User(userJsonObject);
	       
	                    
	                    mUserList.add(user);
	                } */
	                
		          
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


	
}
