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




import com.example.testurlactivity.MyFavorFragment.ApacheHttpThread;
import com.example.testurlactivity.homeFragment.urlThreadGetImg;
import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchFragment extends Fragment {
	
	int misSearchUser = 0;
	int misSearchQuestion = 1;
	
	String userNameSearch;
	String questionContentSearch;
	
	ArrayList<User> mUserList;
	
	ArrayList<User> mUpdateUserList;
	
	ArrayList<Question> mQuestionList;
	ArrayList<Question> mUpdateQuestionList;
	
	ListView mListView;
	
	
	FoucusQuestionListAdapter mQuestionAdapter;
	
	UserListAdapter mUserAdapter;
	

	int userID;
	String userName;
	
	User user;
	
	 static InputStream is = null;
	 static String json2 = "";
	
	
	Activity thisActivity;
	private View mRootView;
	 
	SharedPreferences sharedPreferences;
	
	 private Bitmap bitmap;
		
	 
	 ArrayList<Bitmap> bitMapList;
	 
	 View contentLy;
	 View userLy ;
	 View cancelLy;
	 TextView userLText ;
	 TextView contentText ;
		
View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {

	   
	             if (v.getId() == R.id.content_ly) {
	              	 userLy.setBackgroundColor(Color.parseColor("#0077d9"));
	            	 contentLy.setBackgroundColor(Color.parseColor("#ffffff"));
	            	 userLText.setTextColor(Color.parseColor("#ffffff"));
	            	 contentText.setTextColor(Color.parseColor("#0077d9"));
	            	 misSearchUser = 0;
	                }
	             else if (v.getId() == R.id.user_ly) 
	            	 
	             {
	            	 misSearchUser = 1;
	            	 userLy.setBackgroundColor(Color.parseColor("#ffffff"));
	            	 contentLy.setBackgroundColor(Color.parseColor("#0077d9"));
	            	 userLText.setTextColor(Color.parseColor("#0077d9"));
	            	 contentText.setTextColor(Color.parseColor("#ffffff"));

	             }
	             if (v.getId() == R.id.cancel_ly) {
		
						Fragment homeContent = null;
						homeContent = new homeFragment();
		
						TestURLActivity fca = (TestURLActivity) getActivity();
						fca.switchContent(homeContent);
		
				/*		thisActivity.finish();
						// 设置切换动画，从右边进入，左边退出
						thisActivity.overridePendingTransition(R.anim.in_from_left,  R.anim.out_to_right);*/
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
			            
			            	mListView.setVisibility(View.VISIBLE);
			            	mUserAdapter = new UserListAdapter(thisActivity, R.layout.question_focus_userlist_item, mUserList);
			            //	mUserAdapter.updateListView(mUpdateUserList ,null); 	
			             }
						 else
						 {
							 	mQuestionAdapter.updateListView(mUpdateQuestionList);
							 	mListView.setVisibility(View.VISIBLE);
							 	mListView.setAdapter(mQuestionAdapter);

							 	mListView.setOnItemClickListener(new OnItemClickListener(){

									 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
									 		// Toast.makeText(thisActivity , "Id:"+mQuestionAdapter.getItem(arg2).getQuestionId()+",Detail:"+mQuestionAdapter.getItem(arg2).getQuestionDetail()+",Content:"+mQuestionAdapter.getItem(arg2).getContent(), Toast.LENGTH_LONG).show();
	
									 		Intent intent = new Intent(thisActivity,
									 				questionDetailActivity.class);
											/*intent.putExtra("question_content",mAdapter.getItem(arg2).getContent());
											intent.putExtra("question_detail",mAdapter.getItem(arg2).getQuestionDetail());
											intent.putExtra("question_id",String.valueOf(mAdapter.getItem(arg2).getQuestionId()));
											intent.putExtra("answer_count",String.valueOf(mAdapter.getItem(arg2).getAnswerCount()));
												startActivity(intent);
											*/
									 		// sould be modify ,becuase the getitem is not all the same as to the  questionDetailActivity list item ,by anxiang.xiao 20150805
									 		Bundle mBundle = new Bundle();    
									  		mBundle.putSerializable("key", mQuestionAdapter.getItem(arg2));    
									 		intent.putExtras(mBundle);    
									 	  
									 		startActivity(intent);  

									 	}
									 });
								
				          
		
						 }
									 
		         }
		         if (msg.what == 0x16) {
		        	 mListView.setVisibility(View.GONE);
						
		        	// Toast.makeText(thisActivity, "no msg received" , Toast.LENGTH_LONG).show();
						
		         }
		         if (msg.what == 0x20) {
		        							
		        	 Toast.makeText(thisActivity, bundle.getString("text3") , Toast.LENGTH_LONG).show();
						
		         }
		         if (msg.what == 0x18)
		         {
		        	 		        	 
					mListView.setAdapter(mUserAdapter);
					mListView.setOnItemClickListener(new OnItemClickListener() {
	
						public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
	
							Intent intent = new Intent(thisActivity, userDetailActivity.class);
	
							Bundle mBundle = new Bundle();
							mBundle.putSerializable("key",	mUserAdapter.getItem(arg2));
							intent.putExtras(mBundle);
	
							startActivity(intent);
	
							thisActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
						}
					});
		                
	                 	
		         }
		         if (msg.what == 0x21)
		         {
		        	 Toast.makeText(thisActivity, bundle.getString("text3") , Toast.LENGTH_LONG).show();
		         }
		         
		         
		         
		      
		        
		     }

		 };
		 


	public SearchFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		
		
		thisActivity =	getActivity();
		
		View view = inflater.inflate(R.layout.search_user_content_list, container, false);
	    
	    mRootView = view;
	    
	    SharedPreferences sharedPreferences = thisActivity.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        userID = sharedPreferences.getInt("user_id", 1);
        
        userName = sharedPreferences.getString("name", "");
  	
        // mFavorTagList = new ArrayList<FavorTag>();
        
        EditText  editTV = (EditText) view.findViewById(R.id.search_edit);
		
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
					if (misSearchUser == 1)
					{
						userNameSearch = s.toString();
						//Toast.makeText(thisActivity, userNameSearch, Toast.LENGTH_LONG).show();
					
						new ApacheHttpThreadUser(userNameSearch).start();	
						
					}
					else
					{
						questionContentSearch = s.toString();
						new ApacheHttpThreadQuestion(questionContentSearch).start();	
					}
				}
	
			}
		});
		
		mUserList = new ArrayList<User>();
		mQuestionList = new ArrayList<Question>();
		
		mListView = (ListView)mRootView.findViewById(R.id.content_user_list);

		 // userlist adapter 
	
		mQuestionAdapter = new FoucusQuestionListAdapter(thisActivity, R.layout.user_focus_questions_list_item, mQuestionList);
		
		// mListView.setAdapter(mUserAdapter);
		
		contentLy = (View)mRootView.findViewById(R.id.content_ly);
		userLy = (View)mRootView.findViewById(R.id.user_ly);
		cancelLy = (View)mRootView.findViewById(R.id.cancel_ly);
		userLText = (TextView)mRootView.findViewById(R.id.user_text);
		contentText = (TextView)mRootView.findViewById(R.id.content_text);
		 userLy.setBackgroundColor(Color.parseColor("#0077d9"));
    	 contentLy.setBackgroundColor(Color.parseColor("#ffffff"));
    	 userLText.setTextColor(Color.parseColor("#ffffff"));
    	 contentText.setTextColor(Color.parseColor("#0077d9"));

		mListView.setVisibility(View.GONE);
		
		contentLy.setOnClickListener(onClickListener);
		userLy.setOnClickListener(onClickListener);
		cancelLy.setOnClickListener(onClickListener);
		
		 return mRootView;
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
	
	

	public class ApacheHttpThreadQuestion extends Thread {
		String strs = "";
		int len;
		  JSONObject json;
		  JSONObject rootJson;
		  Iterator it ;
		  String searchName;
		public ApacheHttpThreadQuestion(String searchName) {
			// TODO Auto-generated constructor stub
			this.searchName = searchName;
		}

		@Override
		public void run() {
			try {


				HttpClient client = new DefaultHttpClient();
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_search_questions/?question_name_search="+searchName);
			
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
		          
		            rootJson = new JSONObject(sb.toString());
		            if (rootJson.has("value"))
		            {
						JSONArray jsonArray = rootJson.getJSONArray("value");
						  mUpdateQuestionList = new ArrayList<Question>();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

							// Question question = new Question();
							Question question = new Question(jsonObject);
							/*if (jsonObject.has("question_info")) {
								JSONObject actionQuestionInfoJsonObject = new JSONObject(
										jsonObject.getString("question_info"));

								// newest answer user temply ,the question
								// member need to be modify
								question.setAnswerCount(Integer
										.valueOf(actionQuestionInfoJsonObject
												.getString("answer_count")));

								// user categorytitle for last action str temply
								// ,should be modify
								question.setFocusCount(Integer
										.valueOf(actionQuestionInfoJsonObject
												.getString("focus_count")));
								question.setQuestionContent(actionQuestionInfoJsonObject
										.getString("question_content"));

							}*/

							mUpdateQuestionList.add(question);
						}

						if (mUpdateQuestionList.size() > 0) {
							Message msg = new Message();
							msg.what = 0x17;

							Bundle bundle = new Bundle();
							bundle.clear();
							// bundle.putString("recv_server", new
							// String(buffer));
							bundle.putString("text3", jsonArray.toString());

							msg.setData(bundle);

							myHandler.sendMessage(msg);
						}

					} else {
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
				Bundle bundle = new Bundle();
				bundle.clear();
				bundle.putString("text4", "bit map create finsh");

				msg.setData(bundle);
				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
		
	

}
