package com.example.testurlactivity;

import java.io.IOException;
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
import org.apache.http.util.EntityUtils;

import com.example.testurlactivity.QuestionCommentActivity.ApacheHttpThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCommentActivity extends Activity {
	
	
	EditText commentContentEditText;

	String commentContentStr = "";
	String questionID;
	String userID;
	

	public AddCommentActivity() {
		// TODO Auto-generated constructor stub
	}
	
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	        
	          
	         if (msg.what == 0x15) {
	     				Bundle bundle = msg.getData();
	     				Toast.makeText(getApplicationContext(), bundle.getString("text1"), Toast.LENGTH_SHORT).show();
	     				
	     		//		 showTextView.setText(bundle.getString("text1"));
	                 
	         }
	  
	        
	     }

	 };
	
	
	 View.OnClickListener onClickListener = new View.OnClickListener() {
	    
        public void onClick(View v) {
        	
        	
        	
        	   if (v.getId() == R.id.cancel) {
              
        		 	 finish();
               	           
                   }
   
             if (v.getId() == R.id.publish_comment) {
            	 
            	/* Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();*/
            	 commentContentStr = commentContentEditText.getText().toString();
            	 
            	 // new net deal thread to connect to server for push the date
            	// Toast.makeText(getApplicationContext(), questionContentStr, Toast.LENGTH_SHORT).show();
            	 new ApacheHttpThread().start();	
            	  
             	 //finish();       
                }
             
         
          
        }

    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.add_comment);
		
		
		Intent intent = getIntent();
		questionID = intent.getStringExtra("question_id");
		
		
		 SharedPreferences sharedPreferences = getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
          userID = String.valueOf(sharedPreferences.getInt("user_id", 1));
   
	
	
		
		commentContentEditText = (EditText) findViewById(R.id.comment_edittext);

		View cancleText = findViewById(R.id.cancel);
		cancleText.setOnClickListener(onClickListener);
		
		View publishCommentText = findViewById(R.id.publish_comment);
		publishCommentText.setOnClickListener(onClickListener);
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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl + "/test/ajax/add_comment/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// 如果是Post提交可以将参数封装到集合中传递
				List dataList = new ArrayList();
				
			
				dataList.add(new BasicNameValuePair("comment_content", commentContentStr));
				dataList.add(new BasicNameValuePair("question_id", questionID));
				dataList.add(new BasicNameValuePair("user_id", userID));
			
				strs ="content :" +commentContentStr + "@@ questionID:" +questionID + "@@ userID" +userID;
				// UrlEncodedFormEntity用于将集合转换为Entity对象
				httpPost.setEntity(new UrlEncodedFormEntity(dataList, HTTP.UTF_8));
				// 获取相应消息
				HttpResponse httpResponse = client.execute(httpPost);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				// 把消息对象直接转换为字符串
				String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				
				sleep(1000);
				
				Message msg = new Message();
				msg.what = 0x15;

				Bundle bundle = new Bundle();
				bundle.clear();

				// bundle.putString("recv_server", new String(buffer));
				bundle.putString("text1", content);

				msg.setData(bundle);

				myHandler.sendMessage(msg);


				// 给点url来进行解析
				// webView.loadUrl(url);
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	

}
