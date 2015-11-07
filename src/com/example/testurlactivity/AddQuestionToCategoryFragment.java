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


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddQuestionToCategoryFragment extends Fragment {
	
	Activity thisActivity;
	private View mRootView;
	EditText editTV;
	int choseCategoryId ;

	int misSearchUser = 1;
	int misSearchQuestion = 0;
	String titleSearch;
	String questionContentSearch;
	
	ArrayList<QuestionCategory> mQuestionCategoryList = null;
	ArrayList<QuestionCategory> mUpdateQuestionCategoryList;
	ListView mListView;
	QuestionCategoryListAdapter mQuestionCategoryAdapter;
	
	QuestionCategoryListAdapter mOldQuestionCategoryAdapter;
	
	SharedPreferences sharedPreferences;
	int currentUserId;
	int inviteUserId;
	int userID;
	String currentUserName;

	static InputStream is = null;
	static String json2 = "";

	String chosedCategoryTitle;
	int chosedCategoryId = -1;
	
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	       public void onClick(View v) {
	   
	       if (v.getId() == R.id.cancel) {
	           
	    		/*// new a thread to update the qusetion item foucu_count
				finish();
				// 设置切换动画，从右边进入，左边退出
				overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
	         	           */
	       }
	      

	       } 

	   };
	
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    		Bundle bundle = msg.getData();
	    		
		if (msg.what == 0x22) {

			Toast.makeText(thisActivity, bundle.getString("flag") , Toast.LENGTH_LONG).show();

		}
		if (msg.what == 0x18) {

		    Toast.makeText(thisActivity, bundle.getString("text3") , Toast.LENGTH_LONG).show();
		    
			 
		    /*
		    for (int i = 0; i < mUpdateQuestionCategoryList.size(); i++)
		    {
		    	 Toast.makeText(thisActivity, "@@"+ mUpdateQuestionCategoryList.get(i).getCategoryTitel(),	Toast.LENGTH_SHORT).show();
		    }*/
		    mQuestionCategoryAdapter.updateListView(mUpdateQuestionCategoryList);
		}
		
		
		if (msg.what == 0x17) {

			// Toast.makeText(getApplicationContext(),
			// bundle.getString("text3") , Toast.LENGTH_LONG).show();

			mQuestionCategoryAdapter.updateListView(mQuestionCategoryList);

		}
		if (msg.what == 0x16) {
			mListView.setVisibility(View.GONE);

			// Toast.makeText(thisActivity, "no msg received" ,
			// Toast.LENGTH_LONG).show();

		}
 
	        
	     }

	 };
	

	
	public AddQuestionToCategoryFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public int  getChosedCategoryId()
	{
		
		for (int i = 0, j = mListView.getCount(); i < j; i++) {  
    	    View child = mListView.getChildAt(i);  
    	    if (child != null)
    	    {
	    	    RadioButton rdoBtn = (RadioButton) child.findViewById(R.id.radio_categroy_id);  
	    	    if (rdoBtn.isChecked())  
	    	    {
	    	    	chosedCategoryTitle = mQuestionCategoryList.get(i).getCategoryTitel();  
	    	    	chosedCategoryId = mQuestionCategoryList.get(i).getCategoryId(); 
	    	    }
    	    }
    	}  
    	
    	// Toast.makeText(thisActivity, "chosed_id:"+ chosedCategoryId, Toast.LENGTH_SHORT).show();
		return chosedCategoryId;
    	//Toast.makeText(thisActivity, "chosed_id:"+ chosedCategoryId, Toast.LENGTH_SHORT).show();
       	           
		//return 9;
    	
	}
	

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        View  contentView=  inflater.inflate(R.layout.fragment_add_question_to_category,null);
        mRootView = contentView;
		thisActivity =	getActivity();
		
		new ApacheHttpThread().start();
		

		if (mQuestionCategoryList == null)
			mQuestionCategoryList = new ArrayList<QuestionCategory>();
		  
		mListView = (ListView) mRootView.findViewById(R.id.category_list);
		if (mOldQuestionCategoryAdapter != null)
			mQuestionCategoryAdapter = mOldQuestionCategoryAdapter;
		else
		{
			mQuestionCategoryAdapter = new QuestionCategoryListAdapter(thisActivity, R.layout.question_category_item,	mQuestionCategoryList);
			mOldQuestionCategoryAdapter = mQuestionCategoryAdapter;
		}
		mListView.setAdapter(mQuestionCategoryAdapter);
		

		/*editTV = (EditText)  mRootView.findViewById(R.id.search_edit);
		editTV.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				// Toast.makeText(getApplicationContext(), s.toString(),
				// Toast.LENGTH_SHORT).show();

				// update list data by the text;
				if (s.length() == 0) {
					// mListView.setVisibility(View.GONE);

					// mQuestionCategoryAdapter.updateListView(mQuestionCategoryList);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

				if (s.length() == 0) {
					// mListView.setVisibility(View.GONE);

					// mQuestionCategoryAdapter.updateListView(mQuestionCategoryList);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				//
				// step 1 :make sure user or question to search
				// step 2 :connect server to get date
				// step 3 :update list date
				// step 4 :update ui vibility or not

				// get data from server and update the updatelist to update the
				// listview item
				//
				if (s.length() == 0) {
					// mListView.setVisibility(View.GONE);
					// mQuestionCategoryAdapter.updateListView(mQuestionCategoryList);
				} else {
					
					 titleSearch = s.toString();
					 Toast.makeText(thisActivity, titleSearch, Toast.LENGTH_LONG).show(); 
					 new ApacheHttpThreadCategory(titleSearch).start();
					

				}

			}
		});
*/
        return contentView;
    }
    
    
    public class ApacheHttpThread extends Thread {
		String strs = "";

		public ApacheHttpThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {

				// 通过webview来解析网页
				
				HttpClient client = new DefaultHttpClient();
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/list_category/");
				// HttpGet httpGet = new HttpGet("http://192.168.0.104/wecenter2/?/explore/ajax/list/");
				// HttpGet httpGet = new HttpGet("http://192.168.0.104/wecenter2/?flagmobile=1");
				// 获取相应消息
				HttpResponse httpResponse = client.execute(httpGet);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				 
				is = entity.getContent();     
				
				try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            is.close();
		            json2 = sb.toString();
		            
		         
		           JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		           
		           mQuestionCategoryList = new ArrayList<QuestionCategory>();
		              
	                for(int i=0;i<json.length();i++) 
	                { 
	                    JSONObject jsonObject = (JSONObject)json.opt(i); 
	                    
	                    QuestionCategory qestionCategory =  new QuestionCategory(jsonObject);

	                    mQuestionCategoryList.add(qestionCategory);
	                } 
	                
		          
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		            Log.d("json", json2.toString());
		        }
	
				Message msg = new Message();
				msg.what = 0x17;

				Bundle bundle = new Bundle();
				bundle.clear();

				// bundle.putString("recv_server", new String(buffer));
				bundle.putString("text3", json2.toString());
			

				msg.setData(bundle);

				myHandler.sendMessage(msg);
				
				
				// 给点url来进行解析
				// webView.loadUrl(url);
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
	
	
	
	
	public class ApacheHttpThreadCategory extends Thread {
		String strs = "";
		int len;
		  JSONObject json;
		  JSONObject rootJson;
		  Iterator it ;
		  String searchTitle;
		public ApacheHttpThreadCategory(String searchTitle1) {
			// TODO Auto-generated constructor stub
			this.searchTitle = searchTitle1;
		}

		@Override
		public void run() {
			try {


				HttpClient client = new DefaultHttpClient();
				
				HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_search_category/?title_search="+searchTitle);
			
				HttpResponse httpResponse = client.execute(httpGet);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				 
				is = entity.getContent();     
				
	
				
				try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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

						JSONArray json = rootJson.getJSONArray("value"); 
				       // JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
				           
						mUpdateQuestionCategoryList = new ArrayList<QuestionCategory>();
				              
					    for(int i=0;i<json.length();i++) 
		                { 
		                    JSONObject jsonObject = (JSONObject)json.opt(i); 
		                    
		                    QuestionCategory qestionCategory =  new QuestionCategory(jsonObject);

		                    mUpdateQuestionCategoryList.add(qestionCategory);
		                } 

						if (mUpdateQuestionCategoryList.size() > 0) {
							Message msg = new Message();
							msg.what = 0x18;
							Bundle bundle = new Bundle();
							bundle.clear();
							bundle.putString("text3", json2);
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
	

}
