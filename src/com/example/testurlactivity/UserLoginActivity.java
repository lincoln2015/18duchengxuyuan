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
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserLoginActivity extends Activity {
	
    User user =null;
	SharedPreferences sharedPreferences;
	
	EditText nameEditText;
	EditText passwdEditText;
	
	String name ;
	String passwd;
	
	int USER_LOGIN_START_REQUEST = 1004;
	int USER_LOGIN_END_REQUEST = 1005;
	

	public UserLoginActivity() {
		// TODO Auto-generated constructor stub
	}
	
	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	        
	          
	         if (msg.what == 0x15) {
	     				Bundle bundle = msg.getData();
	     				String result =  bundle.getString("text1");
	     				
	     				
	     				  // save login user info ,befor this ,the thread should reponse the result with the login sucess or failed
	    				if (result.equals("sucess"))
	    				{
	    					 /*	String name = nameEditText.getText().toString();
	    		                String passwd = passwdEditText.getText().toString();*/
	    						Editor editor = sharedPreferences.edit(); //获取编辑器
	    		                editor.putString("name", user.getUserName());
	    		                editor.putInt("answer_count", user.getAnswerCount());
	    		                editor.putInt("question_count", user.getQuestionCount());
	    		                editor.putInt("user_id", user.getUserId());
	    		                editor.commit();//提交修改
	    		                result = "login sucess";
	    		                
	    		              
	    		                URLHelper.ISLOGIN = true;
	    		                
	    		                Intent intent = new Intent();
	    		                UserLoginActivity.this.setResult(USER_LOGIN_END_REQUEST,intent);
		 		            	 /*结束本Activity*/
	    		                UserLoginActivity.this.finish();
	    		                
	    		                // finish();
	    				}
	     				else 
	     				{
	     						result = "login failed";
	     				}
	            	   
	     					     				
	     				// Toast.makeText(getApplicationContext(), result + "@@:"+ URLHelper.ISLOGIN, Toast.LENGTH_SHORT).show();
	     				
	     				// Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
	     				//	showTextView.setText(bundle.getString("text1"));
	                 
	         }
	  
	        
	     }

	 };
	
View.OnClickListener onClickListener = new View.OnClickListener() {
	    
        public void onClick(View v) {
        	
       
   
        	if (v.getId() == R.id.login) {
              
        		 	// finish();
        		// new a thread to send to server to check user lgoin 
        		name = nameEditText.getText().toString();
        	    passwd = passwdEditText.getText().toString();
        	    
        	    //check name & passwd
        	    if (name.equals(""))
        	    	Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
        	    else if (passwd.equals(""))
        	    	Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
        	    else
        	    	new ApacheHttpThread().start();	
        	    
        	                 	           
             }
        	if (v.getId() == R.id.register) {
                
        		 Intent intent = new Intent(getApplication(), RigisterActivity.class);
            		                    	  
            	  /* 通过Bundle对象存储需要传递的数据 */
            	 // Bundle bundle = new Bundle();
            	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
            	 // bundle.putString("user_id", String.valueOf(userID));
            	
            	  /*把bundle对象assign给Intent*/
            	 // intent.putExtras(bundle);

 				  startActivity(intent);
 				  
 				 overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
           	           
        	}
        	
        } 

    };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.user_login);
	
		nameEditText = (EditText) findViewById(R.id.name);

		passwdEditText = (EditText) findViewById(R.id.passwd);

		View loginBtn = findViewById(R.id.login);
		loginBtn.setOnClickListener(onClickListener);
		
		View registerBtn = findViewById(R.id.register);
		registerBtn.setOnClickListener(onClickListener);

	    sharedPreferences = getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        String nameValue = sharedPreferences.getString("name", "");
        String passwd = sharedPreferences.getString("passwd", "");
        nameEditText.setText(nameValue);
        passwdEditText.setText(passwd);

       // URLHelper.ISLOGIN = 1;
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
		 super.finish();
		
		// android.os.Process.killProcess(android.os.Process.myPid());
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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/login_process/");
				// 如果是Post提交可以将参数封装到集合中传递
				List dataList = new ArrayList();
				
				dataList.add(new BasicNameValuePair("addflag", "1"));
				dataList.add(new BasicNameValuePair("name", name));
				dataList.add(new BasicNameValuePair("passwd", passwd));
				// UrlEncodedFormEntity用于将集合转换为Entity对象
				httpPost.setEntity(new UrlEncodedFormEntity(dataList, HTTP.UTF_8));
				// 获取相应消息
				HttpResponse httpResponse = client.execute(httpPost);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				// 把消息对象直接转换为字符串
				String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				
			    JSONObject dateJsonObject =  new JSONObject(content);
			    if (dateJsonObject.getString("login_result").equals("sucess"))
			    {	
			    	 JSONObject userInfoJsonObject =  new JSONObject(dateJsonObject.getString("user_info"));
			    	 user  = new User(userInfoJsonObject);
			    }
	
				Message msg = new Message();
				msg.what = 0x15;

				Bundle bundle = new Bundle();
				bundle.clear();

				// bundle.putString("recv_server", new String(buffer));
				 bundle.putString("text1", dateJsonObject.getString("login_result"));
				// bundle.putString("text1", content);

				msg.setData(bundle);

				myHandler.sendMessage(msg);


				// 给点url来进行解析
				// webView.loadUrl(url);
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
}
