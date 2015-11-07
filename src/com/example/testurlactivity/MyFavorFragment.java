package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.json.JSONException;
import org.json.JSONObject;


import com.example.testurlactivity.AddAnswerToFavorTagActivity.ApacheHttpThread;
import com.example.testurlactivity.AddAnswerToFavorTagActivity.ApacheHttpThread2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MyFavorFragment extends Fragment {

	private String questionID;

	private ArrayList<FavorTag> mFavorTagList;

	private ListView mFavorTagView;

	private FavorTagListAdapter mAdapter;

	int userID;
	String userName;

	User user;

	static InputStream is = null;
	static String json2 = "";

	Activity thisActivity;
	private View mRootView;

	SharedPreferences sharedPreferences;

	String tagTitel;
	String tagDiscription;

	private View backView;
	private View deleteView;

	AddFavorDialog dialog2 = null;
	Button sureLy;
	Button cancleLy;
	EditText favorNameEditTxt;
	EditText favorDisEditTxt;

	public MyFavorFragment() {
		// TODO Auto-generated constructor stub
	}
	
View.OnClickListener onClickListener = new View.OnClickListener() {
	    
        public void onClick(View v) {
   
   
             if (v.getId() == R.id.add_favor_tag) {
            	 
            	/* Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();*/
            	/* Intent intent = new Intent(thisActivity,
            			 AddFavorTagActivity.class);
     
 				  startActivity(intent);*/
            	 //	showCustomDia();
            	 
            	  dialog2.show();//显示Dialog
                }
             if (v.getId() == R.id.back_ly) {
	 				
	 				TestURLActivity fca = (TestURLActivity) getActivity();
	 				fca.toggle();
	 			  }
             if (v.getId() == R.id.delete_ly) {
	 				
            		Toast.makeText(thisActivity, R.string.version_not_support, Toast.LENGTH_SHORT).show();
	 			  }
           	
             if (v.getId() == R.id.sure) {

	 				tagTitel = favorNameEditTxt.getText().toString();
	 				tagDiscription = favorDisEditTxt.getText().toString();
	 				
	 				new ApacheHttpThread2().start();	

                 }
             if (v.getId() == R.id.cancle) {
      
             		dialog2.dismiss();

                 }
         	

        }

    };
    
    
    private void showCustomDia()
	{
		AlertDialog.Builder customDia=new AlertDialog.Builder(getActivity());
		final View viewDia=LayoutInflater.from(getActivity()).inflate(R.layout.diag_add_favor_tag, null);
		customDia.setTitle("新建收藏夹");
		customDia.setView(viewDia);
		customDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				EditText tagTitleEditxt=(EditText) viewDia.findViewById(R.id.tag_title);
				
				EditText tagDicriptionEditxt=(EditText) viewDia.findViewById(R.id.tag_dicription);
				
				tagTitel = tagTitleEditxt.getText().toString();
				tagDiscription = tagDicriptionEditxt.getText().toString();
	
				new ApacheHttpThread2().start();	 
			}
		});
		
		customDia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				/*EditText diaInput=(EditText) viewDia.findViewById(R.id.editText2);
				showClickMessage(diaInput.getText().toString());*/
			}
		});
		customDia.create().show();
	}
	
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    	 
	    	 if ((msg.what == 0x18) )
	    	 {
	    		 new ApacheHttpThread().start();	
	    		 dialog2.dismiss();
	    	 }
	    
	         if (msg.what == 0x16) {
	     				Bundle bundle = msg.getData();

	     			//	showTextView.setText(bundle.getString("text2"));
	                 
	         }
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();
					
					
					// Toast.makeText(thisActivity, bundle.getString("text3") , Toast.LENGTH_LONG).show();
			
					mFavorTagView = (ListView)mRootView.findViewById(R.id.favor_list);
		
					 // bind 
					mAdapter = new FavorTagListAdapter(thisActivity, R.layout.favor_list_item, mFavorTagList);


					 //set adapter	
					mFavorTagView.setAdapter(mAdapter);
					 
					 

					mFavorTagView.setOnItemClickListener(new OnItemClickListener(){

						 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
						 		//Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
					
						 		Intent intent = new Intent(thisActivity,FavorTagItemShowActivity.class);
						 		
						 		Bundle mBundle = new Bundle();    
						  		 mBundle.putSerializable("key", mAdapter.getItem(arg2));    
						  		 intent.putExtras(mBundle);   
								
						 		startActivity(intent);
						
						 	
						 	}
						 });
					
	          
	         }
	     
 
	     }

	 };


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
		
		// TODO Auto-generated method stub
		thisActivity =	getActivity();
		
		View view = inflater.inflate(R.layout.my_favor_list, container, false);
	    mRootView = view;
	    
	    SharedPreferences sharedPreferences = thisActivity.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        userID = sharedPreferences.getInt("user_id", 1);
        userName = sharedPreferences.getString("name", "");
  
		
        mFavorTagList = new ArrayList<FavorTag>();
     
        View addFavorBtn = view.findViewById(R.id.add_favor_tag);
        addFavorBtn.setOnClickListener(onClickListener);
        backView = (View) view.findViewById(R.id.back_ly);
     	backView.setOnClickListener(onClickListener);
     	deleteView = (View) view.findViewById(R.id.delete_ly);
     	deleteView.setOnClickListener(onClickListener);
     	
     	
		dialog2 = new AddFavorDialog(thisActivity, 310, 210, R.layout.add_favor_dialog, R.style.Theme_dialog);
		//warningMsgTextView = (TextView) dialog2.findViewById(R.id.warning_message);
		sureLy = (Button) dialog2.findViewById(R.id.sure);
		cancleLy = (Button) dialog2.findViewById(R.id.cancle);
		favorNameEditTxt = (EditText) dialog2.findViewById(R.id.favor_name);
		favorDisEditTxt = (EditText) dialog2.findViewById(R.id.favor_dis);
		sureLy.setOnClickListener(onClickListener);
		cancleLy.setOnClickListener(onClickListener);
	
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
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_favor_tag/?user_id="+userID+"&answer_id="+"-1");
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
				mFavorTagList = new ArrayList<FavorTag>();
				
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
	                   
	                    FavorTag favorTag = new FavorTag(jsonObject);
	                    
	                    // user answer_content temply , need to change 
	                   // question.setNewestAnswer(jsonObject.getString("answer_content"));
	                        	
	                    favorTag.setUserName(userName);
	                    mFavorTagList.add(favorTag);

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
	
	
	public class ApacheHttpThread2 extends Thread {
		String strs = "";

		public ApacheHttpThread2() {
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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/add_favor_tag/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// 如果是Post提交可以将参数封装到集合中传递
				List dataList = new ArrayList();
				

				dataList.add(new BasicNameValuePair("tag_title", tagTitel));
				dataList.add(new BasicNameValuePair("tag_discription", tagDiscription));
				dataList.add(new BasicNameValuePair("user_id", String.valueOf(userID)));
				
				
				dataList.add(new BasicNameValuePair("item_id", String.valueOf(0)));
				dataList.add(new BasicNameValuePair("item_type", "answer"));
				dataList.add(new BasicNameValuePair("action", "add"));
				
				
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
				
				// new ApacheHttpThread().start();	
				
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
			    

					// bundle.putString("recv_server", new String(buffer));
					if (json.getString("value").equals("add"))
					{
						Message msg = new Message();
						msg.what = 0x18;
						Bundle bundle = new Bundle();
						bundle.clear();
						msg.setData(bundle);
						myHandler.sendMessage(msg);
					}
		           
		           
		          
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

	
	
	
}
