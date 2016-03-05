package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.testurlactivity.FocusQuestionsFragment.ApacheHttpThread;

import android.content.Intent;
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
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class homeFragment extends Fragment implements OnClickListener {
	
	private Button testBtn;
	 private Button connectBtn;
	 private Button urlConnectionBtn;
	 private Button httpUrlConnectionBtn;
	 private Button httpClientBtn;

	 private View backView;
	 private TextView loginTextView;
	 View answerView;
	 private Button loginBtn;
	 private ImageView showImageView;
	 private TextView showTextView;
	 private WebView webView;
	 private Bitmap bitmap;
	 static InputStream is = null;
	 static String json2 = "";
	 private ListView mMontactListView;

	 private View mRootView;
	 private QuestionListAdapter mAdapter;
	 
	private ArrayList<Question> mQuestionList;

	ArrayList<Bitmap> bitMapList;

	int QUESTION_DETAIL_START_REQUEST = 1000;
	int QUESTION_DETAIL_END_REQUEST = 1001;
	int ADD_QUESTION_START_REQUEST = 1002;
	int ADD_QUESTION_END_REQUEST = 1003;

	int USER_LOGIN_START_REQUEST = 1004;
	int USER_LOGIN_END_REQUEST = 1005;

	int QUESTION_DETAIL_START_REQUEST_2 = 2000;
	int QUESTION_DETAIL_END_REQUEST_2 = 2001;

	private View moreView;
	private TextView tv_load_more;
	private ProgressBar pb_load_progress;

    private int startIndex = 0;
    private int requestSize = 10;
    private int lastItem;
    private int nextPage = 1;
    
    private String TAG = "homeFragment";
	 

    private static final int LOAD_DATA_FINISH = 1;
	 @Override
	 public void onClick(View v) {
	  // 直接使用URL对象进行连接
	  if (v == connectBtn) {
		  new urlThread().start();

	  }
	  // 直接使用URLConnection对象进行连接   
	  if (v == urlConnectionBtn) {
		  new urlConnectThread().start();
		  
	  }
	  
	  // 直接使用HttpURLConnection对象进行连接
	  if (v == httpUrlConnectionBtn) {

		  new httpUrlConnectionThread().start();	
	  }


	  // 使用ApacheHttp客户端进行连接(重要方法)
	  if (v == httpClientBtn) {
		  new ApacheHttpThread().start();	
		  
	  }
	  
	  // 使用ApacheHttp客户端进行连接(重要方法)
	  if (v == loginTextView) {

		   /* Intent intent = new Intent(getActivity(), UserLoginActivity.class);
		    startActivity(intent);*/
		    
		    if (URLHelper.ISLOGIN)
		    {
		    	// login out
		    	 URLHelper.ISLOGIN = false;

		    	  	Fragment homeContent = null;
	 				homeContent = new homeFragment();
	 				 
	 				TestURLActivity fca = (TestURLActivity) getActivity();
	 				fca.switchContent(homeContent);

		    }
		    else
		    {
		    	// login int
		  				  
				/* Intent intent = new Intent(getActivity(), UserLoginActivity.class);
				    
				 startActivity(intent);
				 */

	 			Intent intent = new Intent(getActivity(), UserLoginActivity.class);

	 			startActivityForResult(intent, USER_LOGIN_START_REQUEST);
		    }
		  
	  }
	  
	  
	  if (v == loginBtn) {
		 // new ApacheHttpThread().start();	
		  
		  Intent intent = new Intent(getActivity(), UserLoginActivity.class);
		    
	    	startActivity(intent);
		  
	  }
	  
	  if (v == testBtn) {
		 // new ApacheHttpThread().start();	
		  
		  Intent intent = new Intent(getActivity(), testImgviewActivity.class);
		    
	    	startActivity(intent);
		  
	  }
	  if (v == backView) {
			
			TestURLActivity fca = (TestURLActivity) getActivity();
			fca.toggle();
		  }
	  
	
	  
	 
	 }
	 
	 
	 
	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    	
			if (msg.what == LOAD_DATA_FINISH) {
				   tv_load_more.setText(R.string.no_more_data);
                   pb_load_progress.setVisibility(View.GONE);

			}

			if (msg.what == 0x14) {
				showImageView.setImageBitmap(bitmap);

			}
			if (msg.what == 0x15) {
				Bundle bundle = msg.getData();

				showTextView.setText(bundle.getString("text1"));

			}
	          
	         if (msg.what == 0x16) {
	     				Bundle bundle = msg.getData();

	     				showTextView.setText(bundle.getString("text2"));
	                 
	         }
	         if (msg.what == 0x18) {
  				Bundle bundle = msg.getData();
  				
  				tv_load_more.setText(R.string.load_more_data);
				pb_load_progress.setVisibility(View.GONE);

  				mAdapter.updateListView(mQuestionList, bitMapList);

	         }
	 		
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();
					
					
					new urlThreadGetImg().start();
				
					
					 if(mQuestionList != null && !mQuestionList.isEmpty())
					 {
						 mAdapter = new QuestionListAdapter(getActivity(), R.layout.question_list_item, mQuestionList);
	
						 mMontactListView.setAdapter(mAdapter);
					 }
					 
			
	          
	         	}
	         if (msg.what == 0x19) {
					Bundle bundle = msg.getData();
	
					new urlThreadGetImg().start();
  
	         	}
  
	     }

	 };


	public homeFragment() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		

		if(requestCode == QUESTION_DETAIL_START_REQUEST && resultCode == QUESTION_DETAIL_END_REQUEST)
        {
			new ApacheHttpThread().start();
        }
		
		if(requestCode == ADD_QUESTION_START_REQUEST && resultCode == ADD_QUESTION_END_REQUEST)
        {
			//Thread.sleep(6);
			 new ApacheHttpThread().start();
			
        }
		
		if(requestCode == USER_LOGIN_START_REQUEST && resultCode == USER_LOGIN_END_REQUEST)
        {
			Log.d("user login start @@@", "666666");

			// Toast.makeText(getActivity(), "66666", Toast.LENGTH_SHORT).show();
			// Thread.sleep(6);
			// new ApacheHttpThread().start();
			Fragment homeContent = null;
			homeContent = new homeFragment();

			TestURLActivity fca = (TestURLActivity) getActivity();
			fca.switchContent(homeContent);

        }
		
		if(requestCode == QUESTION_DETAIL_START_REQUEST_2 && resultCode == QUESTION_DETAIL_END_REQUEST_2)
        {
			Toast.makeText(getActivity(), "77777", Toast.LENGTH_SHORT).show();
			// Thread.sleep(6);
			// new ApacheHttpThread().start();
			Fragment homeContent = null;
			homeContent = new homeFragment();

			TestURLActivity fca = (TestURLActivity) getActivity();
			fca.switchContent(homeContent);
			
			
			Log.d("question start @@@", "777777");

        }
	
		
	}
	
	private void loadMoreData() {

		tv_load_more.setText(R.string.loading_data);
		pb_load_progress.setVisibility(View.VISIBLE);

		new Thread() {

			public void run() {

				try {
					// 通过webview来解析网页

					HttpClient client = new DefaultHttpClient();

					HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl
							+ "/test/ajax/list_question_page/?page=" + nextPage);
					// HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl +
					// "/test/ajax/list_question/");
					// HttpGet httpGet = new
					// HttpGet("http://192.168.0.104/wecenter2/?/explore/ajax/list/");
					// HttpGet httpGet = new
					// HttpGet("http://192.168.0.104/wecenter2/?flagmobile=1");
					// 获取相应消息
					HttpResponse httpResponse = client.execute(httpGet);
					// 获取消息内容
					HttpEntity entity = httpResponse.getEntity();

					is = entity.getContent();
					// 把消息对象直接转换为字符串
					// String content = EntityUtils.toString(entity);
					// showTextView.setText(content);

					// ///////////////////*************////////////////////

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
						
						/*
						JSONArray json = new JSONObject(sb.toString())
								.getJSONArray("value");*/
						
						JSONObject tempJson = new JSONObject(json2);
						if (tempJson.has("value"))
						{

							JSONArray json = tempJson.getJSONArray("value");
							for (int i = 0; i < json.length(); i++) {
								JSONObject jsonObject = (JSONObject) json.opt(i);
	
								JSONObject qestionInfoJsonObject = new JSONObject(
										jsonObject.getString("question_info"));
								Question question = new Question(
										qestionInfoJsonObject);
	
								if (jsonObject
										.getString("question_newest_answer_info") != "false") {
									JSONObject questionNewestAnswerInfoJsonObject = new JSONObject(
											jsonObject
													.getString("question_newest_answer_info"));
									question.setNewestAnswer(questionNewestAnswerInfoJsonObject
											.getString("answer_content"));
									question.setNewestAnswerID(questionNewestAnswerInfoJsonObject
											.getInt("answer_id"));
								} else {
									question.setNewestAnswer("");
								}
	
								if (jsonObject
										.getString("question_publish_user_info") != "false") {
									JSONObject qestionPublishUserInfoJsonObject = new JSONObject(
											jsonObject
													.getString("question_publish_user_info"));
									question.setUserName(qestionPublishUserInfoJsonObject
											.getString("user_name"));
	
									question.setUserAvatarFile(qestionPublishUserInfoJsonObject
											.getString("avatar_file").replace(
													"localhost", URLHelper.IP_URL));
									// add laterly for get publish user
									question.setPublishUser(new User(
											qestionPublishUserInfoJsonObject));
								} else {
									question.setUserName("");
									question.setUserAvatarFile("");
								}
	
								if (jsonObject.getString("category_info") != "false") {
									JSONObject categoryInfoJsonObject = new JSONObject(
											jsonObject.getString("category_info"));
									question.setCategoryTitle(categoryInfoJsonObject
											.getString("title"));
									question.setCategoryId(categoryInfoJsonObject
											.getInt("id"));
	
								} else {
									question.setCategoryTitle("");
								}
	
								mQuestionList.add(question);
							}
							nextPage++;
							
							Message msg = new Message();
							msg.what = 0x19;
	
							Bundle bundle = new Bundle();
							bundle.clear();
	
							// bundle.putString("recv_server", new String(buffer));
							bundle.putString("text3", json2.toString());
							msg.setData(bundle);
							myHandler.sendMessage(msg);
	
							Thread.sleep(3000);
						
						}		
						else
						{

							myHandler.sendEmptyMessage(LOAD_DATA_FINISH);
						}

						
					} catch (Exception e) {
						Log.e("Buffer Error",
								"Error converting result " + e.toString());
						Log.d("json", json2.toString());
					}

					
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// myHandler.sendEmptyMessage(LOAD_DATA_FINISH);

			}

			;
		}.start();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		
		/*
		 * super.onCreate(savedInstanceState);
		 * setContentView(R.layout.activity_main);
		 */
	    View view = inflater.inflate(R.layout.activity_main, container, false);
	    
	    mRootView = view;

		new ApacheHttpThread().start();

		
		loginTextView = (TextView) view.findViewById(R.id.login_ly);
		loginTextView.setOnClickListener(this);
		
		backView = (View) view.findViewById(R.id.back_ly);
		backView.setOnClickListener(this);
		
		 mMontactListView = (ListView)mRootView.findViewById(R.id.questionlistview);
		 
		LayoutInflater inflater2 = LayoutInflater.from(getActivity().getApplicationContext());
        moreView = inflater2.inflate(R.layout.home_listview_footer_more, null);
        tv_load_more = (TextView) moreView.findViewById(R.id.tv_load_more);
        pb_load_progress = (ProgressBar) moreView.findViewById(R.id.pb_load_progress);
        
        mMontactListView.addFooterView(moreView);
        
        mMontactListView.setOnScrollListener(new OnScrollListener() {

        	@Override
        	public void onScrollStateChanged(AbsListView paramAbsListView, int scrollState) {
        	
        		 if (lastItem == mAdapter.getCount()
        	                && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

        	            Log.e(TAG, "load more");

        	            startIndex += requestSize;

        	            loadMoreData();
        	        }

        	}

        	@Override
        	public void onScroll(AbsListView paramAbsListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        		 lastItem = firstVisibleItem + visibleItemCount - 1;
        	}
        });
       
		
		
		bitMapList = new ArrayList<Bitmap> ();
		
		 if (URLHelper.ISLOGIN)
			 loginTextView.setText("登出");
		 else 
			 loginTextView.setText("登录");
		 
		
		 
		return view;
	}
	
	
	public class urlThread extends Thread {

		public urlThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// super.run();

			try {
				// URL url = new
				// URL("http://192.168.1.100:8080/myweb/image.jpg");
				URL url = new URL("http://ecshopxax.sinaapp.com/favicon.ico");
				InputStream is = url.openStream();
				bitmap = BitmapFactory.decodeStream(is);

				Message msg = new Message();
				msg.what = 0x14;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}
	
	public class urlConnectThread extends Thread {

		public urlConnectThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// super.run();
			try {

				URL url = new URL("http://ecshopxax.sinaapp.com/js/index.js");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				byte[] bs = new byte[1024];
				int len = 0;
				StringBuffer sb = new StringBuffer();
				while ((len = is.read(bs)) != -1) {
					String str = new String(bs, 0, len);
					sb.append(str);
				}

				Message msg = new Message();
				msg.what = 0x15;

				Bundle bundle = new Bundle();
				bundle.clear();

				// bundle.putString("recv_server", new String(buffer));
				bundle.putString("text1", sb.toString());

				msg.setData(bundle);

				myHandler.sendMessage(msg);

			
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}
	
	public class httpUrlConnectionThread extends Thread {

		public httpUrlConnectionThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			
			try {
				URL url = new URL("http://ecshopxax.sinaapp.com/category-16-b0.html");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					String message = connection.getResponseMessage();

					Message msg = new Message();
					msg.what = 0x16;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					bundle.putString("text2", message);

					msg.setData(bundle);

					myHandler.sendMessage(msg);
					
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
			
		}

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
				
				HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/list_question_page/?page="+nextPage);
				// HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/list_question/");
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
		            
		         
		           JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		           
		           mQuestionList = new ArrayList<Question>();
		              
	                for(int i=0;i<json.length();i++) 
	                { 
	                    JSONObject jsonObject = (JSONObject)json.opt(i); 
	                    
	                    JSONObject qestionInfoJsonObject =  new JSONObject(jsonObject.getString("question_info"));
	                    Question question  = new Question(qestionInfoJsonObject);
	                    
	                    if (jsonObject.getString("question_newest_answer_info") != "false")
	                    {
	                    	JSONObject questionNewestAnswerInfoJsonObject =  new JSONObject(jsonObject.getString("question_newest_answer_info"));
	                    	  question.setNewestAnswer(questionNewestAnswerInfoJsonObject.getString("answer_content"));
	                    	  question.setNewestAnswerID(questionNewestAnswerInfoJsonObject.getInt("answer_id"));
	                    }
	                    else
	                    {
	                    	 question.setNewestAnswer("");
	                    }
	                    
	                    if (jsonObject.getString("question_publish_user_info") != "false")
	                    {
	                    	JSONObject qestionPublishUserInfoJsonObject =  new JSONObject(jsonObject.getString("question_publish_user_info"));
	                    	question.setUserName(qestionPublishUserInfoJsonObject.getString("user_name"));
	                    	
	                      	question.setUserAvatarFile(qestionPublishUserInfoJsonObject.getString("avatar_file").replace("localhost", URLHelper.IP_URL));
	                    	// add laterly for get publish user
	                    	question.setPublishUser(new User(qestionPublishUserInfoJsonObject) );
	                    }
	                    else
	                    {
	                    	 question.setUserName("");
	                    	 question.setUserAvatarFile("");
	                    }
	                    
	                    if (jsonObject.getString("category_info") != "false")
	                    {
	                    	JSONObject categoryInfoJsonObject =  new JSONObject(jsonObject.getString("category_info"));
	                    	 question.setCategoryTitle(categoryInfoJsonObject.getString("title"));
	                     	 question.setCategoryId(categoryInfoJsonObject.getInt("id"));
	                    	 
	                    }
	                    else
	                    {
	                    	 question.setCategoryTitle("");
	                    }
	              
	                    
	                    mQuestionList.add(question);
	                }       
	                nextPage++;
		          
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
				
				

				while (i < mQuestionList.size())
				{
					if (mQuestionList.get(i).getAvatarFile() !="" && mQuestionList.get(i).getAvatarFile() !="null")
					{
						URL url = new URL(mQuestionList.get(i).getAvatarFile());
						InputStream is = url.openStream();
						bitmap = BitmapFactory.decodeStream(is);
						
						bitMapList.add(bitmap);
					}
					else
						bitMapList.add(null);
					
					i++;
				}
			
		
				
				Message msg = new Message();
				msg.what = 0x18;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
		

}
