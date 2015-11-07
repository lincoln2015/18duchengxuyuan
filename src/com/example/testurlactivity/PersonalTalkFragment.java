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

import com.example.testurlactivity.NotificationFragment.ApacheHttpThread;
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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class PersonalTalkFragment extends Fragment {
	
	 
	private String questionID;

	private ArrayList<InboxDialog> mInboxDialogList;

	private ListView mInboxDialogView;

	private InboxDialogListAdapter mAdapter;

	int userID;

	User user;

	static InputStream is = null;
	static String json2 = "";

	Activity thisActivity;
	private View mRootView;

	SharedPreferences sharedPreferences;

	private Bitmap bitmap;

	ArrayList<Bitmap> bitMapList;
	
	private View backView;
	private View clearView;

	View.OnClickListener onClickListener = new View.OnClickListener() {
				    
			        public void onClick(View v) {
			   
			   
			             if (v.getId() == R.id.write_talk_ly) {
			            	 
			            	/* Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();*/
			            	 Intent intent = new Intent(thisActivity,
			            			 WritePersonalTalk.class);
			            		                    	  
			            	  /* 通过Bundle对象存储需要传递的数据 */
			            	  Bundle bundle = new Bundle();
			            	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
			            	  bundle.putString("question_id", questionID);
			            	
			            	  /*把bundle对象assign给Intent*/
			            	  intent.putExtras(bundle);

			 				  startActivity(intent);
			 				  
			 				 thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			                }
			             if (v.getId() == R.id.back_ly) {
				 				
				 				TestURLActivity fca = (TestURLActivity) getActivity();
				 				fca.toggle();
				 		 }
			             if (v.getId() == R.id.clear_ly) {
				 				
			            	 Toast.makeText(thisActivity,  getString(R.string.version_not_support) +"清空私信", Toast.LENGTH_SHORT).show();
				 		 }
			             
     
			        }

			    };

			
	public Handler myHandler = new Handler() {
			     @Override
			     public void handleMessage(Message msg) {
			    
			         if (msg.what == 0x16) {
			     				Bundle bundle = msg.getData();

			     			//	showTextView.setText(bundle.getString("text2"));
			                 
			         }
			         if (msg.what == 0x17) {
							Bundle bundle = msg.getData();
							
							new urlThreadGetImg().start();
							
							//	Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
					
							mInboxDialogView = (ListView)mRootView.findViewById(R.id.personal_talk_list);
				
							 // bind 
							mAdapter = new InboxDialogListAdapter(thisActivity, R.layout.personal_talk_list_item, mInboxDialogList);

			          
			  }
			         if (msg.what == 0x18) {
			        	 //set adapter	
							mInboxDialogView.setAdapter(mAdapter);
							 
							mInboxDialogView.setOnItemClickListener(new OnItemClickListener(){

								 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
								 		//Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
									 	
								 		Intent intent = new Intent(thisActivity, PersonalTalkWriteMessage.class);
							
								 		intent.putExtra("user_id",String.valueOf(mAdapter.getItem(arg2).getUid()));
								 		
										intent.putExtra("user_name",String.valueOf(mAdapter.getItem(arg2).getUserNameTalkTo()));
  
								 		startActivity(intent); 
										
								 		thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
								 	
								 	}
								 });
							
			  }
			         
			        
			  
			         
			      
			        
			     }

			 };

	public PersonalTalkFragment() {
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		thisActivity =	getActivity();
		
		View view = inflater.inflate(R.layout.personal_talk_list, container, false);
	    
	    mRootView = view;
	    
	    SharedPreferences sharedPreferences = thisActivity.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        userID = sharedPreferences.getInt("user_id", 1);
  
		
        mInboxDialogList = new ArrayList<InboxDialog>();
        
        
        View writeTalkLy = view.findViewById(R.id.write_talk_ly);
        writeTalkLy.setOnClickListener(onClickListener);
        
        backView = (View) view.findViewById(R.id.back_ly);
     	backView.setOnClickListener(onClickListener);
     	clearView = (View) view.findViewById(R.id.clear_ly);
     	clearView.setOnClickListener(onClickListener);
     	
        
        bitMapList = new ArrayList<Bitmap> ();
		
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
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_inbox_dialog_list/?user_id="+userID);
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
	                    
	                    InboxDialog inboxDialog = new InboxDialog(jsonObject);
	                    
	                    // user answer_content temply , need to change 
	                   // question.setNewestAnswer(jsonObject.getString("answer_content"));
	                        	
	                    
	                    mInboxDialogList.add(inboxDialog);
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
				

				while (i < mInboxDialogList.size())
				{
					if (mInboxDialogList.get(i).getUserTalkTo().getAvatarFile() !="" && mInboxDialogList.get(i).getUserTalkTo().getAvatarFile() !="null")
					{
						URL url = new URL(mInboxDialogList.get(i).getUserTalkTo().getAvatarFile().replace("localhost", URLHelper.IP_URL));
						InputStream is = url.openStream();
						bitmap = BitmapFactory.decodeStream(is);
						
						bitMapList.add(bitmap);
					}
					else
						bitMapList.add(null);
					
					i++;
				}
			
				mAdapter.updateListView(bitMapList);
				
				Message msg = new Message();
				msg.what = 0x18;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
		
	

}
