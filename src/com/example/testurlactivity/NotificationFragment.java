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

import com.example.testurlactivity.UserDetailFragment.ApacheHttpThread;

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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class NotificationFragment extends Fragment {
	

	 
	private String questionID; 
	
	 private ArrayList<Notification> mNotificationList;
	 
	 private ListView mNotificationView;
	 

	 private ArrayAdapter<Notification> mAdapter;
	 
	
	
	
	int userID;
	
	User user;
	
	 static InputStream is = null;
	 static String json2 = "";
	
	
	Activity thisActivity;
	private View mRootView;

	SharedPreferences sharedPreferences;
	
	private View backView;
	private View clearView;
	 
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	   
	             if (v.getId() == R.id.back_ly) {
		 				
		 				TestURLActivity fca = (TestURLActivity) getActivity();
		 				fca.toggle();
		 			  }
	             if (v.getId() == R.id.clear_ly) {
		 				
	            	 Toast.makeText(thisActivity,  getString(R.string.version_not_support) +"清空消息", Toast.LENGTH_SHORT).show();
		 		 }
		             

	        }

	    };
	

	public NotificationFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    
	         if (msg.what == 0x16) {
	     				Bundle bundle = msg.getData();

	     			//	showTextView.setText(bundle.getString("text2"));
	     				Toast.makeText(thisActivity, bundle.getString("text3") , Toast.LENGTH_LONG).show();
	                 
	         }
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();
					
					
					// Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
			
					
					mNotificationView = (ListView)mRootView.findViewById(R.id.notification_list);
		
					 // bind 
					mAdapter = new NotificationListAdapter(thisActivity, R.layout.notification_list_item, mNotificationList);


					 //set adapter	
					mNotificationView.setAdapter(mAdapter);
			
	          
	  }
	  
	         
	      
	        
	     }

	 };


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		thisActivity =	getActivity();
		
		View view = inflater.inflate(R.layout.notification_list, container, false);
	    
	    mRootView = view;
	    
	    SharedPreferences sharedPreferences = thisActivity.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        userID = sharedPreferences.getInt("user_id", 1);
  
        mNotificationList = new ArrayList<Notification>();
        
        backView = (View) view.findViewById(R.id.back_ly);
     	backView.setOnClickListener(onClickListener);
    	clearView = (View) view.findViewById(R.id.clear_ly);
     	clearView.setOnClickListener(onClickListener);
		
		 return mRootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onActivityCreated(savedInstanceState);
		
		 new ApacheHttpThread().start();	
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
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/list_notification/?user_id="+userID);
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
		            
			          
		              
	                for(int i=0;i<json.length();i++) 
	                { 
	                    JSONObject jsonObject = (JSONObject)json.opt(i); 
	                    
	                    Notification notification = new Notification(jsonObject);
	                    
	                    // user answer_content temply , need to change 
	                   // question.setNewestAnswer(jsonObject.getString("answer_content"));
	                    
	                  /*  if (jsonObject.has("p_user_info"))
	        				        			{
	        				
	        				
	        				Message msg = new Message();
	  						msg.what = 0x16;
	  		
	  						Bundle bundle = new Bundle();
	  						bundle.clear();
	  		
	  						// bundle.putString("recv_server", new String(buffer));
	  						// bundle.putString("text3",String.valueOf(len));
	  						bundle.putString("text3",jsonObject.getString("p_user_info"));
	  						
	  						Log.d("##tag", jsonObject.getString("p_user_info"));
	  						
	  						msg.setData(bundle);
	  		
	  						myHandler.sendMessage(msg); 
	        				
	        				
	        			}*/

	       
	                    mNotificationList.add(notification);
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
