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








import com.example.testurlactivity.MyFavorFragment.ApacheHttpThread;
import com.example.testurlactivity.MyFavorFragment.ApacheHttpThread2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class AddAnswerToFavorTagActivity extends Activity {
	
	private String questionID; 
	
	 private ArrayList<FavorTag> mFavorTagList;
	 
	 private ArrayList<FavorTag> mWillAddAnserToFavorTagList;
	 
	 private ListView mFavorTagView;
	 

	//  private ArrayAdapter<FavorTag> mAdapter;
	 
	 FavorTagForAddAnswerListAdapter mAdapter;
	 
	
	 int answerId;
	
	
	int userID;
	String userName;
	
	User user;
	

	 static InputStream is = null;
	 static String json2 = "";
	
	 
	String tagTitel;
	String tagDiscription;

	AddFavorDialog dialog2 = null;
	Button sureLy;
	Button cancleLy;
	EditText favorNameEditTxt;
	EditText favorDisEditTxt;
	
	
View.OnClickListener onClickListener = new View.OnClickListener() {
	    
        public void onClick(View v) {
   
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
   
             if (v.getId() == R.id.add_favor_tag) {
            	 // showCustomDia();
            	 
            	 dialog2.show();//显示Dialog
                }

             
             if (v.getId() == R.id.finish_ly) {
            	// showCustomDia();
            	 //step 1 get the chockbox values & tag_title & user_id & answer_id & item_type 
            	 //step 2 connect server and add tage itm to favor_tag table & faver table 
            	 // connect server and add tage itm to favor_tag table & faver table 
            	 
            	 mWillAddAnserToFavorTagList = new ArrayList<FavorTag>() ;
            
            	 for (int i = 0; i < mFavorTagList.size(); i++)
            	 {    		
            		 if (mFavorTagList.get(i).isFavorAddToChange())
            		 {
            			
            			 mWillAddAnserToFavorTagList.add(mFavorTagList.get(i));
            			 /*
            			 Message msg = new Message();
     					msg.what = 0x19;
     	
     					Bundle bundle = new Bundle();
     					bundle.clear();
     	
     					// bundle.putString("recv_server", new String(buffer));
     					// bundle.putString("text3",String.valueOf(len));
     					bundle.putString("FOCUS_ACTION_ID","@@action:" + mFavorTagList.get(i).getAction());
     					
     					msg.setData(bundle);
     	
     					myHandler.sendMessage(msg);*/
            		 }
            			            		
            	 }
            	 
            	if (mWillAddAnserToFavorTagList.size() > 0)	
            		new ApacheHttpThreadAddAnswerToFavor().start();	 
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
		AlertDialog.Builder customDia=new AlertDialog.Builder(AddAnswerToFavorTagActivity.this);
		final View viewDia=LayoutInflater.from(AddAnswerToFavorTagActivity.this).inflate(R.layout.diag_add_favor_tag, null);
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
	    
	    	 if (msg.what == 0x19) {
     				Bundle bundle = msg.getData();

     				// Toast.makeText(getApplicationContext(), bundle.getString("FOCUS_ACTION_ID") , Toast.LENGTH_LONG).show();
                 
     				finish();
    				// 设置切换动画，从右边进入，左边退出
    				overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
	    	   }
	    
	         if (msg.what == 0x16) {
	     				Bundle bundle = msg.getData();

	     			//	showTextView.setText(bundle.getString("text2"));
	                 
	         }
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();
					
					
					// Toast.makeText(thisActivity, bundle.getString("text3") , Toast.LENGTH_LONG).show();
			
					mFavorTagView = (ListView)findViewById(R.id.favor_tag_list);
		
					 // bind 
					mAdapter = new FavorTagForAddAnswerListAdapter(getApplicationContext(), R.layout.add_answer_to_favor_tag_lsit_item, mFavorTagList);


					 //set adapter	
					mFavorTagView.setAdapter(mAdapter);
					 
					 

					mFavorTagView.setOnItemClickListener(new OnItemClickListener(){

						 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
						 		//Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
					
						 	/*	Intent intent = new Intent(getApplicationContext(),FavorTagItemShowActivity.class);
						 		
						 		Bundle mBundle = new Bundle();    
						  		 mBundle.putSerializable("key", mAdapter.getItem(arg2));    
						  		 intent.putExtras(mBundle);   
								
						 		startActivity(intent);*/
						
						 	
						 	}
						 });
					
	          
	  }
	
	         
	      
	        
	     }

	 };



	public AddAnswerToFavorTagActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		answerId = Integer.valueOf(intent.getStringExtra("answer_id"));

		setContentView(R.layout.add_answer_to_favor_tag);

		SharedPreferences sharedPreferences = getSharedPreferences("ljq123",
				Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
		userID = sharedPreferences.getInt("user_id", 1);

		userName = sharedPreferences.getString("name", "");

	
		View addFavorBtn = findViewById(R.id.add_favor_tag);
		addFavorBtn.setOnClickListener(onClickListener);
		

		View finishLy = findViewById(R.id.finish_ly);
		finishLy.setOnClickListener(onClickListener);
		
		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		
		dialog2 = new AddFavorDialog(AddAnswerToFavorTagActivity.this, 310, 210, R.layout.add_favor_dialog, R.style.Theme_dialog);
		//warningMsgTextView = (TextView) dialog2.findViewById(R.id.warning_message);
		sureLy = (Button) dialog2.findViewById(R.id.sure);
		cancleLy = (Button) dialog2.findViewById(R.id.cancle);
		favorNameEditTxt = (EditText) dialog2.findViewById(R.id.favor_name);
		favorDisEditTxt = (EditText) dialog2.findViewById(R.id.favor_dis);
		sureLy.setOnClickListener(onClickListener);
		cancleLy.setOnClickListener(onClickListener);
	
		
		new ApacheHttpThread().start();	
		
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
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_favor_tag/?user_id="+userID+"&answer_id="+answerId);
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


	
	public class ApacheHttpThreadAddAnswerToFavor extends Thread {
		String strs = "";

		public ApacheHttpThreadAddAnswerToFavor() {
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
				HttpResponse httpResponse = null;
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// 如果是Post提交可以将参数封装到集合中传递
				for (int i = 0; i < mWillAddAnserToFavorTagList.size(); i++)
				// for (int i = 0; i < 1; i++)
				{
					List dataList = new ArrayList();
					
	
					dataList.add(new BasicNameValuePair("item_id", String.valueOf(answerId)));
					dataList.add(new BasicNameValuePair("user_id",String.valueOf(userID)));
					dataList.add(new BasicNameValuePair("item_type", "answer"));
					dataList.add(new BasicNameValuePair("tag_title", mWillAddAnserToFavorTagList.get(i).getTitle()));
					dataList.add(new BasicNameValuePair("tag_discription", "discription null"));
					dataList.add(new BasicNameValuePair("action", mWillAddAnserToFavorTagList.get(i).getAction()));
					
					// UrlEncodedFormEntity用于将集合转换为Entity对象
					httpPost.setEntity(new UrlEncodedFormEntity(dataList,HTTP.UTF_8));
					// 获取相应消息
					httpResponse = client.execute(httpPost);
					

		           /* Message msg = new Message();
					msg.what = 0x19;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					bundle.putString("FOCUS_ACTION_ID", mWillAddAnserToFavorTagList.get(i).getTitle());
									
					msg.setData(bundle);

					myHandler.sendMessage(msg);*/
					
				
				}
				
				
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
	
				is = entity.getContent();     

				new ApacheHttpThread().start();	
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
			    
		            Message msg = new Message();
					msg.what = 0x19;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					bundle.putString("FOCUS_ACTION_ID", json2);
									
					msg.setData(bundle);

					myHandler.sendMessage(msg);
		           
		           
		          
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
