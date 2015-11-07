package com.example.testurlactivity;

import android.app.Activity;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread;
import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionCommentActivity extends Activity {
	
	TextView userNameTextView;
	
	 private ListView mCommentListView;

	 private View mRootView;
	 private ArrayAdapter<QuestionComment> mAdapter;
	 
	 private ArrayList<QuestionComment> mCommentList;
	 
	 
	private String questionID; 

	static InputStream is = null;
	 static String json2 = "";
	 
	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	       
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();
					
					//showTextView.setText(bundle.getString("text3"));

						//Log.d("apche", bundle.getString("text3"))
					// Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
				/*	webView.loadDataWithBaseURL(null, bundle.getString("text3"), "text/html",
							"utf-8", null);
					*/
					mCommentListView = (ListView)findViewById(R.id.comment_list);
						
					// int question list
					  
					 // bind 
					mAdapter = new QuestionCommentListAdapter(getApplicationContext(), R.layout.question_comment_item, mCommentList);


					 //set adapter	
					mCommentListView.setAdapter(mAdapter);
					 
									 
	         }
	         if (msg.what == 0x18) {
					Bundle bundle = msg.getData();
					
					// showTextView.setText(bundle.getString("text4"));

						//Log.d("apche", bundle.getString("text3"))
					Toast.makeText(getApplicationContext(), bundle.getString("text4") , Toast.LENGTH_LONG).show();
				/*	webView.loadDataWithBaseURL(null, bundle.getString("text3"), "text/html",
							"utf-8", null);
					*/
			
	         }
	  
	  
	         
	      
	        
	     }

	 };
	 
	 
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	        	
	        	
	        	
	   
	             if (v.getId() == R.id.write_comment) {
	            	 
	            	/* Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();*/
	            	 Intent intent = new Intent(getApplication(),
	            			 AddCommentActivity.class);
	  	
	            	  Bundle bundle = new Bundle();
	            
	            	  bundle.putString("question_id", questionID);
	       	
	            	  intent.putExtras(bundle);

	 				  startActivity(intent);
	                }
	             
	        
	           
	             if (v.getId() == R.id.back_ly) {
	            	
	            	
		           }
	          
	        }

	    };


	
	public QuestionCommentActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.question_comment_show);
		
		
		Intent intent = getIntent();
		questionID = intent.getStringExtra("question_id");
	
		// userNameTextView = (TextView) findViewById(R.id.user_name);
		
		
		// questionDetailTextView.setText(questionDetail);
		
		
		 // new a net thread to get the comment list of the question bind to
		mCommentList = new ArrayList<QuestionComment>();
		 
		new ApacheHttpThread().start();	
	
		View writeCommentBtn = findViewById(R.id.write_comment);
		writeCommentBtn.setOnClickListener(onClickListener);
		
		View backBtn = findViewById(R.id.back_ly);
		backBtn.setOnClickListener(onClickListener);
		
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

				HttpClient client = new DefaultHttpClient();
				// 如果是Get提交则创建HttpGet对象，否则创建HttpPost对象
				// HttpGet httpGet = new
				// HttpGet("http://192.168.1.100:8080/myweb/hello.jsp?username=abc&pwd=321");
				// post提交的方式
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/account/ajax/login_process/");
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl + "/test/ajax/get_comment_list/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// 如果是Post提交可以将参数封装到集合中传递
				List dataList = new ArrayList();
				
			
				dataList.add(new BasicNameValuePair("question_id", questionID));
				
				// UrlEncodedFormEntity用于将集合转换为Entity对象
				httpPost.setEntity(new UrlEncodedFormEntity(dataList, HTTP.UTF_8));
				// 获取相应消息
				HttpResponse httpResponse = client.execute(httpPost);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				// 把消息对象直接转换为字符串
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
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
		            
		          
		              
	                for(int i=0;i<json.length();i++) 
	                { 
	                    JSONObject jsonObject = (JSONObject)json.opt(i); 
	                    QuestionComment questionComment = new QuestionComment(Integer.valueOf(jsonObject.getString("id")));
	                    // strs += jsonObject.getString("question_content") +"@@@"; 
	         	                    
	                    questionComment.setCommentContent(jsonObject.getString("message"));
	                   // questionComment.setCommentContent(jsonObject.getString("message"));
	                   // questionAnswer.setQuestionDetail(jsonObject.getString("question_detail"));
	                  
	                    
	                   mCommentList.add(questionComment);
	                } 
	                
	                Message msg = new Message();
					msg.what = 0x17;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					bundle.putString("text3", json2.toString());
					
			

					msg.setData(bundle);

					myHandler.sendMessage(msg);
					
	                
		          
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
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

}
