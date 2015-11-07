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
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.testurlactivity.InviteUserActivity.ApacheHttpThreadUser;
import com.example.testurlactivity.InviteUserActivity.urlThreadGetImg;
import com.example.testurlactivity.QuestionFocusUserActivity.ApacheHttpThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerCommentsShowActivity extends Activity {
	

	ArrayList<AnswerComment> mAnswerCommentList;
	

	
	ListView mListView;
	
	
	AnswerCommentsListAdapter  mAnswerCommentsListAdapter;
	


	int currentUserId;
	int inviteUserId;
	int userID;
	String currentUserName;
	
	User user;
	
	 static InputStream is = null;
	 static String json2 = "";

	 String answerID;
	 
	SharedPreferences sharedPreferences;
	
	
	 ArrayList<Bitmap> bitMapList;
	 private Bitmap bitmap;
		
	public Handler myHandler = new Handler() {
		     @Override
		     public void handleMessage(Message msg) {
		    
		         if (msg.what == 0x16) {
		     				Bundle bundle = msg.getData();

		     			//	showTextView.setText(bundle.getString("text2"));
		                 
		         }
		         if (msg.what == 0x17) {
						Bundle bundle = msg.getData();
						
						mListView = (ListView)findViewById(R.id.comments_list);	
						mAnswerCommentsListAdapter = new AnswerCommentsListAdapter(getApplicationContext(), R.layout.answer_comment_item, mAnswerCommentList);
						
						new urlThreadGetImg().start();
		        	 
		        	
		        	 
		        	//  Toast.makeText(getApplicationContext(), bundle.getString("text3"), Toast.LENGTH_SHORT).show();
		          
		         }
		         if (msg.what == 0x18)
		         {
						mAnswerCommentsListAdapter.updateListView(mAnswerCommentList ,bitMapList);
						
						mListView.setAdapter(mAnswerCommentsListAdapter);

						mListView.setOnItemClickListener(new OnItemClickListener(){

							 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
							
							 	/*	Intent intent = new Intent(getApplicationContext(),
							 				userDetailActivity.class);
									
									
							 		Bundle mBundle = new Bundle();    
							  		 mBundle.putSerializable("key", mAdapter.getItem(arg2));    
							  		 intent.putExtras(mBundle);    
								 	  
							 		 startActivity(intent); */
									
							  	
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
	        if (v.getId() == R.id.write_comment_ly) {
	                
	        	Intent intent = new Intent(getApplicationContext(),	WriteAnswerCommentActivity.class);
	        	
	        	  Bundle bundle = new Bundle();
            	  
            	  bundle.putString("answer_id", String.valueOf(answerID));
   	
            	  intent.putExtras(bundle);
		 	
		
		 		 startActivity(intent); 
		 		 
		 		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	           	           
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

	public AnswerCommentsShowActivity() {
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.answer_comments_show);
		
		Intent intent = getIntent();
		
		answerID = intent.getStringExtra("answer_id");
		
	
	    SharedPreferences sharedPreferences = this.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    currentUserId = sharedPreferences.getInt("user_id", 1);
        
        currentUserName = sharedPreferences.getString("name", "");
  	
    /*	mUserList = new ArrayList<User>();
	
		mListView = (ListView)findViewById(R.id.user_list);
		mUserAdapter = new UserListAdapter(this, R.layout.question_focus_userlist_item, mUserList);
		mListView.setAdapter(mUserAdapter);

		mListView.setVisibility(View.GONE);
        
        EditText  editTV = (EditText) findViewById(R.id.search_edit);*/
        View writeComentLy = (View)findViewById(R.id.write_comment_ly);
    
        writeComentLy.setOnClickListener(onClickListener);
        

		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		
       // Toast.makeText(getApplicationContext(), "answerid:"+answerID, Toast.LENGTH_SHORT).show();
        
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
		
		new ApacheHttpThread().start();	
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
		// TODO Auto-generated method stub
		return super.onCreateThumbnail(outBitmap, canvas);
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
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_answer_comments/?answer_id="+answerID);
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
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					json2 = sb.toString();
					
				
			        mAnswerCommentList = new 	ArrayList<AnswerComment>();
					// JSONArray json = new
					// JSONObject(sb.toString()).getJSONArray("value");
					JSONArray json = new JSONObject(sb.toString()).getJSONArray("value");

					for (int i = 0; i < json.length(); i++) {
						JSONObject jsonObject = (JSONObject) json.opt(i);
						AnswerComment answerComment = new AnswerComment(jsonObject);
					
						mAnswerCommentList.add(answerComment);
					}

					Message msg = new Message();
					msg.what = 0x17;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					// bundle.putString("text3",String.valueOf(len));
					bundle.putString("text3", json2);

					msg.setData(bundle);

					myHandler.sendMessage(msg);
		          
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
				
				
			 	bitMapList = new ArrayList<Bitmap>();
				while (i < mAnswerCommentList.size())
				{
					if (mAnswerCommentList.get(i).getAvatarFile() !="" && mAnswerCommentList.get(i).getAvatarFile() !="null")
					{
						URL url = new URL(mAnswerCommentList.get(i).getAvatarFile().replace("localhost", URLHelper.IP_URL));
						InputStream is = url.openStream();
						bitmap = BitmapFactory.decodeStream(is);
						
						bitMapList.add(bitmap);
		
					}
					else
						bitMapList.add(null);
					
					i++;
				}
			
				//mAnswerCommentsListAdapter.updateListView(mAnswerCommentList ,bitMapList);
				
				Message msg = new Message();
				msg.what = 0x18;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
		
	

	

}
