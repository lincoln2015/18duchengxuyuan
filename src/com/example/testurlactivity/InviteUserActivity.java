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
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.testurlactivity.SearchFragment.ApacheHttpThreadQuestion;
import com.example.testurlactivity.SearchFragment.ApacheHttpThreadUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class InviteUserActivity extends Activity {
	
	int ASK_SELF_ID = 2;
	int USER_ALREADY_INVITED_ID = 3;
	int ASK_PUBLISH_USER_ID = 4;
	int SUCESS_ID = 5;
	
	int misSearchUser = 1;
	int misSearchQuestion = 0;
	
	String userNameSearch;
	String questionContentSearch;
	
	ArrayList<User> mUserList;
	
	ArrayList<User> mUpdateUserList;
	
	ArrayList<Question> mQuestionList;
	ArrayList<Question> mUpdateQuestionList;
	
	ListView mListView;
	
	 ArrayList<Bitmap> bitMapList;
	 private Bitmap bitmap;
	
	FoucusQuestionListAdapter mQuestionAdapter;
	
	UserListAdapter mUserAdapter;
	

	int currentUserId;
	int inviteUserId;
	int userID;
	String currentUserName;
	
	User user;
	
	 static InputStream is = null;
	 static String json2 = "";
	
	
	Activity thisActivity;
	 private View mRootView;
	 
	 String questionID;
	 
		SharedPreferences sharedPreferences;
		
		 View.OnClickListener onClickListener = new View.OnClickListener() {
			    
		       public void onClick(View v) {
		   
		       if (v.getId() == R.id.cancel_ly) {
		           
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
		    		Bundle bundle = msg.getData();
		    		
			if (msg.what == 0x22) {

				Toast.makeText(getApplicationContext(), bundle.getString("flag") , Toast.LENGTH_LONG).show();

			}
			if (msg.what == 0x17) {

				// Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
				// Toast.LENGTH_LONG).show();

					if (misSearchUser == 1) {
						new urlThreadGetImg().start();
						mListView.setVisibility(View.VISIBLE);
						mUserAdapter = new UserListAdapter(getApplicationContext(), R.layout.question_focus_userlist_item, mUserList);
					}

		         }
		         if (msg.what == 0x16) {
		        	 mListView.setVisibility(View.GONE);
						
		        	// Toast.makeText(thisActivity, "no msg received" , Toast.LENGTH_LONG).show();
						
		         }
		      
		         if (msg.what == 0x18)
		         {
		        	  	mListView.setAdapter(mUserAdapter);

	                 	mListView.setOnItemClickListener(new OnItemClickListener(){

							 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
							 		
							 		new ApacheHttpThreadInvite(mUserAdapter.getItem(arg2).getUserId()).start();	
							 	}
							 });
		         }
		  
		      
		         if (msg.what == ASK_SELF_ID) {
			        	
		        	 Toast.makeText(getApplicationContext(), bundle.getString("ASK_SELF_ID") , Toast.LENGTH_LONG).show();
						
		        	// Toast.makeText(thisActivity, "no msg received" , Toast.LENGTH_LONG).show();
						
		         }
		         if (msg.what == USER_ALREADY_INVITED_ID) {
			        	
		        	 Toast.makeText(getApplicationContext(), bundle.getString("USER_ALREADY_INVITED_ID") , Toast.LENGTH_LONG).show();
						
		        	// Toast.makeText(thisActivity, "no msg received" , Toast.LENGTH_LONG).show();
						
		         }
		         if (msg.what == ASK_PUBLISH_USER_ID) {
			        	
		        	 Toast.makeText(getApplicationContext(), bundle.getString("ASK_PUBLISH_USER_ID") , Toast.LENGTH_LONG).show();
						
		        	// Toast.makeText(thisActivity, "no msg received" , Toast.LENGTH_LONG).show();
						
		         }
		         if (msg.what == SUCESS_ID) {
			        	
		        	 finish();
		        	 // Toast.makeText(getApplicationContext(), bundle.getString("ASK_PUBLISH_USER_ID") , Toast.LENGTH_LONG).show();
						
		        	// Toast.makeText(thisActivity, "no msg received" , Toast.LENGTH_LONG).show();
						
		         }
		        
		     }

		 };
		

		

	public InviteUserActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.invite_user);
		
		Intent intent = getIntent();
		
		questionID = intent.getStringExtra("question_id");
		
	
	    SharedPreferences sharedPreferences = this.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    currentUserId = sharedPreferences.getInt("user_id", 1);
        
        currentUserName = sharedPreferences.getString("name", "");
  	
    	mUserList = new ArrayList<User>();
	
		mListView = (ListView)findViewById(R.id.user_list);
		// mListView.setAdapter(mUserAdapter);

		mListView.setVisibility(View.GONE);
		
		View cancelLy = (View) findViewById(R.id.cancel_ly);
		cancelLy.setOnClickListener(onClickListener);
        
        EditText  editTV = (EditText) findViewById(R.id.search_edit);
		editTV.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				// Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_SHORT).show();
				
				// update list data by the text;
				if (s.length() == 0)
					mListView.setVisibility(View.GONE);
			
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
				if (s.length() == 0)
					mListView.setVisibility(View.GONE);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//
				//step 1 :make sure user or question to search
				//step 2 :connect server to get date 
				//step 3 :update list date 
				//step 4 :update ui vibility or not 
				
				// get data from server and update the updatelist to update the listview item
				//
				if (s.length() == 0)
				{
					mListView.setVisibility(View.GONE);
				}
				else
				{
					userNameSearch = s.toString();
						//Toast.makeText(thisActivity, userNameSearch, Toast.LENGTH_LONG).show();
					new ApacheHttpThreadUser(userNameSearch).start();	
				
				}
	
			}
		});
	}
	
	
	public class ApacheHttpThreadUser extends Thread {
		String strs = "";
		int len;
		  JSONObject json;
		  JSONObject rootJson;
		  Iterator it ;
		  String searchQuestionName;
		public ApacheHttpThreadUser(String searchName) {
			// TODO Auto-generated constructor stub
			this.searchQuestionName = searchName;
		}

		@Override
		public void run() {
			try {


				HttpClient client = new DefaultHttpClient();
				
				HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_search_users/?user_name_search="+searchQuestionName);
			
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
		            
		        
		          // JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		         // JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		            
					rootJson = new JSONObject(sb.toString());
					if (rootJson.has("value")) {
						json = new JSONObject(rootJson.getString("value"));
						len = json.length();

						it = json.keys();

						mUpdateUserList = new ArrayList<User>();
						while (it.hasNext()) {// 遍历JSONObject
							JSONObject userJsonObject = new JSONObject(
									json.getString(it.next().toString()));
							User user = new User(userJsonObject);

							mUpdateUserList.add(user);
						

						}
						
	
						if (mUpdateUserList.size() > 0) {
							Message msg = new Message();
							msg.what = 0x17;

							Bundle bundle = new Bundle();
							bundle.clear();

							// bundle.putString("recv_server", new
							// String(buffer));
							bundle.putString("text3", rootJson.toString());

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
	
	public class ApacheHttpThreadInvite extends Thread {
		String strs = "";
		int intviteUid;

		public ApacheHttpThreadInvite(int id) {
			// TODO Auto-generated constructor stub
			intviteUid = id;
		}

		@Override
		public void run() {
			try {

				HttpClient client = new DefaultHttpClient();
				
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/save_invite/");
			
				List dataList = new ArrayList();
				
			
				dataList.add(new BasicNameValuePair("question_id", questionID));
				dataList.add(new BasicNameValuePair("current_uid", String.valueOf(currentUserId)));
				dataList.add(new BasicNameValuePair("user_name", currentUserName));
				
				dataList.add(new BasicNameValuePair("invite_uid", String.valueOf(intviteUid)));
				
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
		            
		           
		            JSONObject json = new JSONObject(json2);
		    
		            if (json.getInt("ask_self_reply") == 1)
		            {
		            	// Toast.makeText(getApplicationContext(), "ask self answer", Toast.LENGTH_SHORT).show();
		            	
		                Message msg = new Message();
						msg.what = ASK_SELF_ID;

						Bundle bundle = new Bundle();
						bundle.clear();

						// bundle.putString("recv_server", new String(buffer));
						// bundle.putString("text4", "question_id:"+questionID + "@@@ user_id:"+String.valueOf(userID));
						 bundle.putString("ASK_SELF_ID","ask self answer");
										
						msg.setData(bundle);

						myHandler.sendMessage(msg);
			          
		            }
		            
		            else if (json.getInt("invite_user_already_invited") == 1)
				     {
				            	// Toast.makeText(getApplicationContext(), "ask self answer", Toast.LENGTH_SHORT).show();
				            	
				                Message msg = new Message();
								msg.what = USER_ALREADY_INVITED_ID;

								Bundle bundle = new Bundle();
								bundle.clear();

								// bundle.putString("recv_server", new String(buffer));
								// bundle.putString("text4", "question_id:"+questionID + "@@@ user_id:"+String.valueOf(userID));
								 bundle.putString("USER_ALREADY_INVITED_ID","user already_invvited");
												
								msg.setData(bundle);

								myHandler.sendMessage(msg);
					          
				      }
		             else if (json.getInt("ask_publish_user") == 1)
				     {
				            	// Toast.makeText(getApplicationContext(), "ask self answer", Toast.LENGTH_SHORT).show();
				            	
				                Message msg = new Message();
								msg.what = ASK_PUBLISH_USER_ID;

								Bundle bundle = new Bundle();
								bundle.clear();

								// bundle.putString("recv_server", new String(buffer));
								// bundle.putString("text4", "question_id:"+questionID + "@@@ user_id:"+String.valueOf(userID));
								 bundle.putString("ASK_PUBLISH_USER_ID","ask publish user");
												
								msg.setData(bundle);

								myHandler.sendMessage(msg);
					          
				      }
		             else
		             {
		            	  Message msg = new Message();
							msg.what = SUCESS_ID;

							Bundle bundle = new Bundle();
							bundle.clear();

							// bundle.putString("recv_server", new String(buffer));
							// bundle.putString("text4", "question_id:"+questionID + "@@@ user_id:"+String.valueOf(userID));
							 bundle.putString("SUCESS_ID","sucess");
											
							msg.setData(bundle);

							myHandler.sendMessage(msg);
		            	 
		             }
		           
		           /* if (json.getInt("ask_publish_user") == 1)
		            {
		            	Toast.makeText(getApplicationContext(), "ask publish answer", Toast.LENGTH_SHORT).show();
		            }
		            if (json.getInt("invite_user_already_invited") == 1)
		            {
		            	Toast.makeText(getApplicationContext(), "user already invited", Toast.LENGTH_SHORT).show();
		            }
		            */
		        
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
				while (i < mUpdateUserList.size())
				{
					if (mUpdateUserList.get(i).getAvatarFile() !="" && mUpdateUserList.get(i).getAvatarFile() !="null")
					{
						URL url = new URL(mUpdateUserList.get(i).getAvatarFile().replace("localhost", URLHelper.IP_URL));
						InputStream is = url.openStream();
						bitmap = BitmapFactory.decodeStream(is);
						
						bitMapList.add(bitmap);
		
					}
					else
						bitMapList.add(null);
					
					i++;
				}
			
				mUserAdapter.updateListView(mUpdateUserList ,bitMapList);
				
				Message msg = new Message();
				msg.what = 0x18;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
		
	

}
