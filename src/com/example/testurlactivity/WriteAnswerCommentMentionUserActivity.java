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
import org.json.JSONObject;

import com.example.testurlactivity.InviteUserActivity.ApacheHttpThreadInvite;
import com.example.testurlactivity.InviteUserActivity.ApacheHttpThreadUser;
import com.example.testurlactivity.InviteUserActivity.urlThreadGetImg;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WriteAnswerCommentMentionUserActivity extends Activity {
	
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
	private static int USER_START_DETAIL_REQUEST = 10000;
	private static int USER_END_DETAIL_REQUEST = 10001;
	
	
	
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

	ArrayList<Bitmap> bitMapList;
	private Bitmap bitmap;

View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {

	            
	             if (v.getId() == R.id.cancel_ly) {
	            	 
	            	 finish();
	 				// �����л����������ұ߽��룬����˳�
	            	 overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
	               }
	             
	        }

	    };
		
		
	public Handler myHandler = new Handler() {
		     @Override
		     public void handleMessage(Message msg) {
		    		Bundle bundle = msg.getData();
		         if (msg.what == 0x17) {
					
			
					 //  Toast.makeText(thisActivity, bundle.getString("text3") , Toast.LENGTH_LONG).show();
					
			
						 if (misSearchUser == 1)
			             {
								new urlThreadGetImg().start();
								
			                	mUserAdapter.updateListView(mUpdateUserList,null);
								
								mListView.setVisibility(View.VISIBLE);
			                	
			                 	mListView.setAdapter(mUserAdapter);

			                 	mListView.setOnItemClickListener(new OnItemClickListener(){

									 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
									 	
											String userID= String.valueOf(mUserAdapter.getItem(arg2).getUserId());
											String userName = mUserAdapter.getItem(arg2).getUserName();
											Intent intent = new Intent();
				
											/* ͨ��Bundle����洢��Ҫ���ݵ����� */
											Bundle bundle = new Bundle();
											/* �ַ����ַ������������ֽ����顢�������ȵȣ������Դ� */
											bundle.putString("user_id",userID);
											bundle.putString("user_name",userName);
											/* ��bundle����assign��Intent */
											intent.putExtras(bundle);
				
											// Toast.makeText(getApplicationContext(),"finish detail", Toast.LENGTH_SHORT).show();
											WriteAnswerCommentMentionUserActivity.this.setResult(USER_END_DETAIL_REQUEST, intent);
											/* ������Activity */
											WriteAnswerCommentMentionUserActivity.this.finish();        	
									 	
									 	}
									 });
			                	
			             }
						
									 
		         }
		         if (msg.what == 0x16) {
		        	 mListView.setVisibility(View.GONE);
						
		        	// Toast.makeText(thisActivity, "no msg received" , Toast.LENGTH_LONG).show();
						
		         }
		      
		  
		         if (msg.what == 0x18) {
		        	
		        	    Toast.makeText(getApplicationContext(), bundle.getString("text4") , Toast.LENGTH_LONG).show();
					
		        	 
						
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
		


	public WriteAnswerCommentMentionUserActivity() {
		// TODO Auto-generated constructor stub
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
        
        
        View cancelLy = (View)findViewById(R.id.cancel_ly);
        cancelLy.setOnClickListener(onClickListener);
  	
    	mUserList = new ArrayList<User>();
	
		mListView = (ListView)findViewById(R.id.user_list);
		mUserAdapter = new UserListAdapter(this, R.layout.question_focus_userlist_item, mUserList);
		mListView.setAdapter(mUserAdapter);

		mListView.setVisibility(View.GONE);
        
        EditText  editTV = (EditText) findViewById(R.id.search_edit);
		
		editTV.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
				// ��ȡ��Ϣ����
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
						while (it.hasNext()) {// ����JSONObject
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
					if (mUpdateUserList.get(i).getAvatarFile() !="" && mUpdateUserList.get(i).getAvatarFile() != "null")
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
