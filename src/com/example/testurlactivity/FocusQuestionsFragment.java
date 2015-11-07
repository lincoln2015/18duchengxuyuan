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

import com.example.testurlactivity.Notification;
import com.example.testurlactivity.NotificationListAdapter;
import com.example.testurlactivity.Question;
import com.example.testurlactivity.R;
import com.example.testurlactivity.URLHelper;
import com.example.testurlactivity.User;
import com.example.testurlactivity.NotificationFragment.ApacheHttpThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FocusQuestionsFragment extends Fragment {
	
		private String questionID; 
		
		 private ArrayList<Question> mQuestionList;
		 
		 private ListView mQuestionView;
		 

		 private ArrayAdapter<Question> mAdapter;
	
		
		int userID;
		
		User user;
		
		 static InputStream is = null;
		 static String json2 = "";
		
		
		Activity thisActivity;
		 private View mRootView;
		 
		 
		SharedPreferences sharedPreferences;
		
		int QUESTION_DETAIL_START_REQUEST = 1000;
		int QUESTION_DETAIL_END_REQUEST = 1001;
		
		 private View backView;
		 
		 View.OnClickListener onClickListener = new View.OnClickListener() {
			    
		        public void onClick(View v) {

		             if (v.getId() == R.id.back_ly) {
		 				
		 				TestURLActivity fca = (TestURLActivity) getActivity();
		 				fca.toggle();
		 			  }
		             
		          
		        }

		    };
		    
		    

	public FocusQuestionsFragment() {
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
					
					
					//	Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
			
					mQuestionView = (ListView)mRootView.findViewById(R.id.focus_question_list);
		
					 // bind 
					mAdapter = new FoucusQuestionListAdapter(thisActivity, R.layout.user_focus_questions_list_item, mQuestionList);


					 //set adapter	
					mQuestionView.setAdapter(mAdapter);
					 
					 

					mQuestionView.setOnItemClickListener(new OnItemClickListener(){

						 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
						 		//Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
						 	
						 		
						 		Intent intent = new Intent(getActivity(),questionDetailActivity.class);
								/*intent.putExtra("question_content",mAdapter.getItem(arg2).getContent());
								intent.putExtra("question_detail",mAdapter.getItem(arg2).getQuestionDetail());
								intent.putExtra("question_id",String.valueOf(mAdapter.getItem(arg2).getQuestionId()));
								intent.putExtra("answer_count",String.valueOf(mAdapter.getItem(arg2).getAnswerCount()));
									startActivity(intent);
								*/
					
						 		Bundle mBundle = new Bundle();    
						  		mBundle.putSerializable("key", mAdapter.getItem(arg2));    
						  		// Toast.makeText(getActivity(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
						 		intent.putExtras(mBundle);    
						 		
						 		startActivityForResult(intent,QUESTION_DETAIL_START_REQUEST);

						 	
						 	}
						 });
					
	          
	  }
	  
	         
	      
	        
	     }

	 };
	 
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		
		if(requestCode == QUESTION_DETAIL_START_REQUEST && resultCode == QUESTION_DETAIL_END_REQUEST)
        {
			 new ApacheHttpThread().start();	
        }
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onActivityCreated(savedInstanceState);
			
		 new ApacheHttpThread().start();	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		
		thisActivity =	getActivity();
		
		View view = inflater.inflate(R.layout.user_focus_questions_list, container, false);
	    
	    mRootView = view;
	    
	    SharedPreferences sharedPreferences = thisActivity.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        userID = sharedPreferences.getInt("user_id", 1);
  
	
        backView = (View) view.findViewById(R.id.back_ly);
		backView.setOnClickListener(onClickListener);
        
        return mRootView;
		
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
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_user_focus_question/?user_id="+userID);
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
		            
		        
		          // JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		            JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		            
			          
		            mQuestionList = new ArrayList<Question>();
		            
	                for(int i=0;i<json.length();i++) 
	                { 
	                    JSONObject jsonObject = (JSONObject)json.opt(i); 
	                    
	                    JSONObject subJson = new JSONObject(jsonObject.getString("question_info")); 
	                    
	                    
	                    Question question = new Question(Integer.valueOf(subJson.getString("question_id")));
	                    
	                    // user answer_content temply , need to change 
	                   // question.setNewestAnswer(jsonObject.getString("answer_content"));
	                    if (subJson.has("question_content"))
	                    	question.setQuestionContent(subJson.getString("question_content"));
	                    if (subJson.has("answer_count"))
	                    	question.setAnswerCount(Integer.valueOf(subJson.getString("answer_count")));
	                    if (subJson.has("focus_count"))
	                    	question.setFocusCount(Integer.valueOf(subJson.getString("focus_count")));
	                    if (subJson.has("getCommentCount"))
	                    	question.setCommentCount(Integer.valueOf(subJson.getString("comment_count")));
	                    
	                    if (subJson.has("question_detail"))
	                    	question.setQuestionDetail(subJson.getString("question_detail"));
	                    
	                    
	                    
	                    mQuestionList.add(question);
	                } 
		              
		           Message msg = new Message();
					msg.what = 0x17;
	
					Bundle bundle = new Bundle();
					bundle.clear();
	
					// bundle.putString("recv_server", new String(buffer));
					// bundle.putString("text3",String.valueOf(len));
					bundle.putString("text3",json2);
					
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

	
	

}
