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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.testurlactivity.AnswerContentDetailActivity.urlThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class questionDetailActivity extends Activity  implements OnTouchListener{
	// 手指向右滑动时的最小速度
	private static final int XSPEED_MIN = 200;
	// 手指向右滑动时的最小距离
	private static final int XDISTANCE_MIN = 150;
	// 记录手指按下时的横坐标。
	private float xDown;
	// 记录手指移动时的横坐标。
	private float xMove;
	// 用于计算手指滑动的速度。
	private VelocityTracker mVelocityTracker;


	TextView questionContentTextView;
	TextView questionDetailTextView;
	TextView answerCountTextView;
	
	TextView focusCountTextView;
	TextView thanksCountTextView;
	
	
	private String questionContent; 
	private String questionDetail; 
	private String questionID; 
	private String answerCount; 
	private String thanksCount; 
	

	private int focusCount; 
	
	int ADD_START_REQUEST = 1000;
	int ADD_END_REQUEST = 1001;
	int userID;
	
	String currentUserName ="";
	
	 static InputStream is = null;
	 static String json2 = "";
	 private ListView mMontactListView;
	 
	 static InputStream is2 = null;
	 static String json22 = "";
	 
	 static InputStream is3 = null;
	 static String json23 = "";

	 private View mRootView;
	// private ArrayAdapter<QuestionAnswer> mAdapter;
	 private QuestionAnswerListAdapter mAdapter;
	 
	 private ArrayList<QuestionAnswer> mQuestionAnswerList;
	 
	 
	 int FOCUSED_ID = 0x1000;
	 int FOCUS_ACTION_ID = 0x2000;
	 String foucusText;
	 boolean isFocused = false;
	String realAction;
	
	View focusLy;
	View thanksLy;
	
	int QUESTION_DETAIL_START_REQUEST = 1000;
	int QUESTION_DETAIL_END_REQUEST = 1001;
	
	int QUESTION_DETAIL_START_REQUEST_2 = 2000;
	int QUESTION_DETAIL_END_REQUEST_2 = 2001;
	
	 private Bitmap bitmap; 
	 ArrayList<Bitmap> bitMapList;
	 ArrayList<Bitmap> questionDetailbitMapList;
	 ArrayList<String> picUrlList;
	 int indexBitmapList = 0;

	 String thanksBtnStr ="感谢提问者";
	 
	 ImageView thanksImg;
	 ImageView focusImg;
	 TextView thanksTextView;
	 TextView focusTextView;
	
	 
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    	 
	    	 if (msg.what == 0x11) {
	        	 Bundle bundle = msg.getData();
	        	 // Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
	        	// showTextView.setText(bundle.getString("text2"))
					if (bundle.getString("text3").equals("true"))
					{
					
						Drawable  d = getResources().getDrawable(R.drawable.zhang_3);//找到你要加入的图片
						//	shareImg.setBackground(d);
						thanksImg.setImageDrawable(d);
						thanksTextView.setText("已感谢");
						thanksLy.setClickable(false);

					}
					else
					{
						Drawable  d = getResources().getDrawable(R.drawable.zhang_3_grey);//找到你要加入的图片
						//	shareImg.setBackground(d);
						thanksImg.setImageDrawable(d);
						thanksTextView.setText("感谢题者");
						thanksLy.setClickable(true);
					}

	         }
	    	 
	    	 if (msg.what == 0x22) {
	        	 Bundle bundle = msg.getData();

	        	// Toast.makeText(getApplicationContext(), bundle.getString("text4") , Toast.LENGTH_LONG).show();

	         }

	    	 if (msg.what == 0x19) {
					Bundle bundle = msg.getData();

					// showTextView.setText(bundle.getString("text2"))
						if (bundle.getString("text3").equals("add"))
						{
							// Toast.makeText(getApplicationContext(), "thanks sucess", Toast.LENGTH_SHORT).show();
		
							Drawable  d = getResources().getDrawable(R.drawable.zhang_3);//找到你要加入的图片
							//	shareImg.setBackground(d);
							thanksImg.setImageDrawable(d);
							thanksTextView.setText("已感谢");
							thanksLy.setClickable(false);
							
							 thanksCountTextView.setText("感谢数  " +String.valueOf(Integer.valueOf(thanksCount) + 1));
						}
						else
							Toast.makeText(getApplicationContext(), "thanks failed", Toast.LENGTH_SHORT).show();
		         }
	    	 
	    	 if (msg.what == 0x14) {
					
					// Toast.makeText(getApplicationContext(), "ox14 message recv", Toast.LENGTH_SHORT).show(); 
					
					
					 CharSequence html = Html.fromHtml(questionDetail,new ImageGetter()
				        {
				    
				      @Override
						public Drawable getDrawable(String source) {
				    	  
							if (true) {
							
								// Toast.makeText(getApplicationContext(), source, Toast.LENGTH_SHORT).show();
						
								BitmapDrawable bitDrawable = new BitmapDrawable(questionDetailbitMapList.get(indexBitmapList));
								indexBitmapList++;
								bitDrawable.setBounds(0, 0,	bitDrawable.getIntrinsicHeight() + 300, bitDrawable.getIntrinsicHeight() +300);

								return bitDrawable;
							} else {
								// 获得系统资源（图片）
								// Bitmap bitmap = null;
								Drawable drawable = getResources().getDrawable(getResourceId(source));
							

								drawable.setBounds(0, 0, drawable.getIntrinsicHeight(),	drawable.getIntrinsicHeight());

								// 这里还可以对资源做进一步的处理
								return drawable;
							}
						}
					}, null);

					 questionDetailTextView.setText(html);
					
		         }
	       
	         if (msg.what == 0x17) {
	        	 Bundle bundle = msg.getData();
	        	 
	        	 answerCountTextView.setText(bundle.getString("answer_count") +"个回答");
				 focusCountTextView.setText("关注数  " + bundle.getString("focus_count"));
				 thanksCountTextView.setText("感谢数  " +bundle.getString("thanks_count"));

				 new urlThreadGetImg().start();
		 
	         }
	         if (msg.what == FOCUS_ACTION_ID) {
					Bundle bundle = msg.getData();
					
					
					// Toast.makeText(getApplicationContext(), bundle.getString("FOCUS_ACTION_ID") , Toast.LENGTH_LONG).show();
			
					if (realAction.equals("add"))
					{
						 Drawable  d = getResources().getDrawable(R.drawable.focus_grey_icon_blu_background);//找到你要加入的图片
							//	shareImg.setBackground(d);
						 focusImg.setImageDrawable(d);
						 focusTextView.setText("取消关注");
						 focusCount = focusCount +1;
						 focusCountTextView.setText("关注数  " + String.valueOf(focusCount));
						
					}
					else if (realAction.equals("remove"))
					{
						 Drawable  d = getResources().getDrawable(R.drawable.focus_grey_icon_grey_background);//找到你要加入的图片
						 focusImg.setImageDrawable(d);
						 focusTextView.setText("关注");
						 focusCount = focusCount -1;
						 focusCountTextView.setText("关注数  " + String.valueOf(focusCount));
						
					}
			
					
	         }
	  
	         if (msg.what == FOCUSED_ID) {
					Bundle bundle = msg.getData();
	
					// Toast.makeText(getApplicationContext(), bundle.getString("FOCUS_ACTION_ID") , Toast.LENGTH_LONG).show();
					if (isFocused)
					{

						Drawable  d = getResources().getDrawable(R.drawable.focus_grey_icon_blu_background);//找到你要加入的图片
						//	shareImg.setBackground(d);
						focusImg.setImageDrawable(d);
						 focusTextView.setText("取消关注");

					}
					else
					{
		
						Drawable  d = getResources().getDrawable(R.drawable.focus_grey_icon_grey_background);//找到你要加入的图片
						//	shareImg.setBackground(d);
						focusImg.setImageDrawable(d);
						 focusTextView.setText("关注");
		
					}
					
	         }
	  
	         if (msg.what == 0x20) {
					Bundle bundle = msg.getData();
					
					
					Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
					
					
				
					// focusBtn.setText(foucusText);
			
					
	         }
	  
	         if (msg.what == 0x18)
	         {
	        	 Bundle bundle = msg.getData();
	        		
	        	 mMontactListView = (ListView)findViewById(R.id.question_answer_list);
					
				 mAdapter = new QuestionAnswerListAdapter(getApplicationContext(), R.layout.question_answer_list_item, mQuestionAnswerList );
				 
				 mAdapter.updateListView(mQuestionAnswerList, bitMapList);

				 mMontactListView.setAdapter(mAdapter);
				

				 mMontactListView.setOnItemClickListener(new OnItemClickListener(){

						 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
						 		//Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
						 	
						 		 
						 		 Intent intent = new Intent(getApplication(), AnswerContentDetailActivity.class);
				 				  /* 通过Bundle对象存储需要传递的数据 */
				            	  Bundle bundle = new Bundle();
				            	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
				            	  bundle.putString("answer_id", String.valueOf(mAdapter.getItem(arg2).getAnswerId()));
				            	
				            	  /*把bundle对象assign给Intent*/
				            	  intent.putExtras(bundle);

				            	  startActivity(intent); 
								
				 			 	  overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
						  		
						  		// Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getUserId()), Toast.LENGTH_LONG).show();
						 		
						 		/// Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getFansCount()), Toast.LENGTH_LONG).show();
						  		
						 		
							
						 	
						 	}
						 });
	         }
	         
	         
	         
	        
	     }

	 };
	 
	 public int getResourceId(String name) {
			try {
				// 根据资源的ID的变量名获得Field的对象， 使用反射机制来实现的
				java.lang.reflect.Field field = R.drawable.class.getField(name);
				// 取得并返回资源的id的字段（静态变量）的值,使用反射机制
				return Integer.parseInt(field.get(null).toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
			
	 
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	   
	   
	             if (v.getId() == R.id.questioned_layout_btn) {
	          
	 				 Intent intent = new Intent(getApplication(), AddAnwserActivity.class);
	 				  /* 通过Bundle对象存储需要传递的数据 */
	            	  Bundle bundle = new Bundle();
	            	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
	            	  bundle.putString("question_id", questionID);
	            	
	            	  /*把bundle对象assign给Intent*/
	            	  intent.putExtras(bundle);

	 				  startActivityForResult(intent, ADD_START_REQUEST);
	 
	 				  overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	                }
	             
	             if (v.getId() == R.id.foucus_ly) {
	            	
		            	/* Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();*/
		            	// new a thread to update the qusetion item foucu_count 
	            	 		new ApacheHttpThread2().start();	
		                }
	             if (v.getId() == R.id.thanks_ly) {
		            	
		            
	            	 	new ApacheHttpThreadThanks().start();	
		          } 
	           
	             if (v.getId() == R.id.focus_count) {
	            		/* Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();*/
	            	 Intent intent = new Intent(getApplication(),
	            			 QuestionFocusUserActivity.class);
	            		                    	  
	            	  /* 通过Bundle对象存储需要传递的数据 */
	            	  Bundle bundle = new Bundle();
	            	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
	            	  bundle.putString("question_id", questionID);
	            	
	            	  /*把bundle对象assign给Intent*/
	            	  intent.putExtras(bundle);

	 				  startActivity(intent);
	 				  
	 				 overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	            	
		           }
			if (v.getId() == R.id.ask_reply) {
				/*
				 * Toast.makeText(getApplicationContext(), "click add question",
				 * Toast.LENGTH_SHORT).show();
				 */
				Intent intent = new Intent(getApplication(),
						InviteUserActivity.class);

				/* 通过Bundle对象存储需要传递的数据 */
				Bundle bundle = new Bundle();
				/* 字符、字符串、布尔、字节数组、浮点数等等，都可以传 */
				bundle.putString("question_id", questionID);

				/* 把bundle对象assign给Intent */
				intent.putExtras(bundle);

				startActivity(intent);
				
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

			}
			if (v.getId() == R.id.back_ly) {

				/*
				 * Toast.makeText(getApplicationContext(), "click add question",
				 * Toast.LENGTH_SHORT).show();
				 */
				// new a thread to update the qusetion item foucu_count
				// finish();
				// 设置切换动画，从右边进入，左边退出
			
			       Intent intent = new Intent();
			       questionDetailActivity.this.setResult(QUESTION_DETAIL_END_REQUEST_2,intent);
	            	 /*结束本Activity*/
			       questionDetailActivity.this.finish();
			       
			       overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
			}
			 if (v.getId() == R.id.share_ly) {
	 				
            	 Toast.makeText(getApplication(),  getString(R.string.version_not_support) +"分享", Toast.LENGTH_SHORT).show();
	 		 }
             


		}

	    };
	    
	    
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);

			if(requestCode == ADD_START_REQUEST && resultCode == ADD_END_REQUEST)
	        {
				 new ApacheHttpThread().start();	
				
	        }
	
	}


	public questionDetailActivity() {
		// TODO Auto-generated constructor stub
	}

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.question_detail);
		
		SharedPreferences sharedPreferences = getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        userID = sharedPreferences.getInt("user_id", 1);
        currentUserName = sharedPreferences.getString("name", "");
	
    	Intent intent = getIntent();
	
		/*	
		Intent intent = getIntent();
		questionContent = intent.getStringExtra("question_content");
		questionDetail = intent.getStringExtra("question_detail");
		questionID = intent.getStringExtra("question_id");
		answerCount = intent.getStringExtra("answer_count");*/
		Question question =  (Question) intent.getSerializableExtra("key"); 
		
		questionContent = question.getContent();
		questionDetail = question.getQuestionDetail();
		questionID = String.valueOf(question.getQuestionId());
		answerCount = String.valueOf(question.getAnswerCount());
		thanksCount = String.valueOf(question.getThanksCount());
		focusCount = question.getFocusCount();
		
		// Toast.makeText(getApplicationContext(), "id:"+questionID+",content:"+questionContent +",detail:"+questionDetail+",answercount:"+answerCount+",focuscount:"+focusCount+",thanksCount:"+thanksCount, Toast.LENGTH_SHORT).show();
		
		questionContentTextView = (TextView) findViewById(R.id.question_content);
		questionDetailTextView = (TextView) findViewById(R.id.question_detail);
		answerCountTextView = (TextView) findViewById(R.id.answer_count);
		focusCountTextView = (TextView) findViewById(R.id.focus_count);
		thanksCountTextView = (TextView) findViewById(R.id.thanks_count);

		questionContentTextView.setText(questionContent);
		// questionDetailTextView.setText(questionDetail);

		thanksImg = (ImageView) findViewById(R.id.thanks_img);
		focusImg = (ImageView) findViewById(R.id.focus_img);
		thanksTextView = (TextView) findViewById(R.id.thanks_text);
		focusTextView = (TextView) findViewById(R.id.focus_text);

		focusLy = (View) findViewById(R.id.foucus_ly);
		focusLy.setOnClickListener(onClickListener);
		thanksLy = (View) findViewById(R.id.thanks_ly);
		thanksLy.setOnClickListener(onClickListener);

		thanksCountTextView.setOnClickListener(onClickListener);
		focusCountTextView.setOnClickListener(onClickListener);

		View addAnswerBtn = findViewById(R.id.questioned_layout_btn);
		addAnswerBtn.setOnClickListener(onClickListener);
		View askReplyBtn = findViewById(R.id.ask_reply);
		askReplyBtn.setOnClickListener(onClickListener);

		LinearLayout ll = (LinearLayout) findViewById(R.id.questiondetail_ly);
		ll.setOnTouchListener(this);

		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		LinearLayout shareLy = (LinearLayout) findViewById(R.id.share_ly);
		shareLy.setOnClickListener(onClickListener);
		
		ScrollView srcollView = (ScrollView) findViewById(R.id.scrollView1);
		srcollView.smoothScrollTo(0, 20);
		
		int i = 0;
		RegexUtil regexUtil = new RegexUtil();
		picUrlList = new ArrayList<String>();
		
		if (questionDetail != "" && questionDetail != "null")
		{
	
			String tempStr = questionDetail;
			questionDetail = regexUtil.geTtextOfHtml(questionDetail, picUrlList);
	
			 i = 0;
			while (i < picUrlList.size()) {
				picUrlList.set(i,	picUrlList.get(i).replace("localhost", URLHelper.IP_URL));
				i++;
			}
			if (picUrlList.size() > 0)
				new urlThread(picUrlList).start();
			else
				questionDetailTextView.setText(tempStr);
		}
		
		new ApacheHttpThreadGetFocusInfo().start();
		new ApacheHttpThreadGetThanksState().start();
		
		 // new a net thread to get the comment list of the question bind to
		new ApacheHttpThread().start();	 
	
		

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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		 // new ApacheHttpThread().start();	
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
	public void finish() {
		// TODO Auto-generated method stub
	
		Intent intent = new Intent();
	

		// Toast.makeText(getApplicationContext(),"finish detail", Toast.LENGTH_SHORT).show();
		questionDetailActivity.this.setResult(QUESTION_DETAIL_END_REQUEST, intent);
		/* 结束本Activity */
		super.finish();
		
		

	}




	public class ApacheHttpThreadGetFocusInfo extends Thread {
		String strs = "";
		int len;
		  JSONObject json;
		  Iterator it ;
		public ApacheHttpThreadGetFocusInfo() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {


				HttpClient client = new DefaultHttpClient();
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/is_already_focuse/?question_id="+questionID+"&current_uid="+userID);
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
		            
		        
		            JSONObject json = new JSONObject(json2); 
		            
		            
		            if (json.getInt("is_focued") == 1)
		            {
		            	isFocused = true;
		            	// foucusText = "取消关注";
		            }   //else  if (json.getString("value").equals("remove"))
		            else
		            {
		            	isFocused = false;
		            	// foucusText = "关注";
		            }
		            Message msg = new Message();
					msg.what = FOCUSED_ID;

				/*	Bundle bundle = new Bundle();
					bundle.clear();

					bundle.putString("FOCUSED_ID", foucusText);
									
					msg.setData(bundle);*/

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
	
	
	public class ApacheHttpThread extends Thread {
		String strs = "";

		public ApacheHttpThread() {
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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/get_answer_list/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// 如果是Post提交可以将参数封装到集合中传递
				List dataList = new ArrayList();
				
			
				dataList.add(new BasicNameValuePair("question_id", questionID));
				
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
				mQuestionAnswerList = new ArrayList<QuestionAnswer>();
				
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
		            
		          
		              
	                for(int i=0;i<json.length();i++) 
	                { 
	                    JSONObject jsonObject = (JSONObject)json.opt(i); 
	                    QuestionAnswer questionAnswer = new QuestionAnswer(jsonObject);
	                    // strs += jsonObject.getString("question_content") +"@@@"; 
	         	                    
	                    questionAnswer.setAnserContent(jsonObject.getString("answer_content"));
	                   // questionAnswer.setQuestionDetail(jsonObject.getString("question_detail"));
	                  
	                    
	                   mQuestionAnswerList.add(questionAnswer);
	                } 
	                
	                Message msg = new Message();
					msg.what = 0x17;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					bundle.putString("text3", json2.toString());
					
					// the count may be change and should be update here 
					bundle.putString("answer_count", String.valueOf(json.length()));
					bundle.putString("focus_count",String.valueOf(focusCount) );
					bundle.putString("thanks_count", thanksCount);

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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/focus/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// 如果是Post提交可以将参数封装到集合中传递
				List dataList = new ArrayList();
				
			
				dataList.add(new BasicNameValuePair("question_id", questionID));
				dataList.add(new BasicNameValuePair("user_id", String.valueOf(userID)));
				
				// UrlEncodedFormEntity用于将集合转换为Entity对象
				httpPost.setEntity(new UrlEncodedFormEntity(dataList));
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
			        
		            if (json.getString("value").equals("add"))
		            {
		            	realAction = "add";
		            }
		            else  if (json.getString("value").equals("remove"))
		            {
		            	realAction = "remove";
		            }
		            else
		            	realAction = "other action";
		            Message msg = new Message();
					msg.what = FOCUS_ACTION_ID;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					bundle.putString("FOCUS_ACTION_ID", realAction);
									
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
				
				bitMapList = new  ArrayList<Bitmap>();

				while (i < mQuestionAnswerList.size())
				{
					if (mQuestionAnswerList.get(i).getPulishAnswerUser().getAvatarFile() !="" &&  mQuestionAnswerList.get(i).getPulishAnswerUser().getAvatarFile() !="null")
					{
						URL url = new URL(mQuestionAnswerList.get(i).getPulishAnswerUser().getAvatarFile().replace("localhost", URLHelper.IP_URL));
						InputStream is = url.openStream();
						bitmap = BitmapFactory.decodeStream(is);
						
						bitMapList.add(bitmap);
						
						/*Message msg = new Message();
						msg.what = 0x20;

						Bundle bundle = new Bundle();
						bundle.clear();

						// bundle.putString("recv_server", new
						// String(buffer));
						bundle.putString("text3", "@@"+mQuestionAnswerList.get(i).getPulishAnswerUser().getAvatarFile().replace("localhost", URLHelper.IP_URL));

						msg.setData(bundle);

						myHandler.sendMessage(msg);*/
					}
					else
						bitMapList.add(null);
					
					i++;
				}
			
				//mAdapter.updateListView(mQuestionAnswerList, bitMapList);
				
				Message msg = new Message();
				msg.what = 0x18;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
	 
	 public class urlThread extends Thread {
		   
		   ArrayList<String> urlList;

			public urlThread(ArrayList<String> picUrlList) {
				// TODO Auto-generated constructor stub
				urlList = picUrlList;
				
				// bitMapList = new  ArrayList<Bitmap>();
				
				questionDetailbitMapList = new  ArrayList<Bitmap>();
			}

			@Override
			public void run() {
			// TODO Auto-generated method stub
			// super.run();
			int i = 0;
			try {

				while (i < urlList.size()) {
					URL url = new URL(urlList.get(i));
					InputStream is = url.openStream();
					bitmap = BitmapFactory.decodeStream(is);

					if (questionDetailbitMapList != null) {
						questionDetailbitMapList.add(bitmap);

					}
					
					Message msg = new Message();
					msg.what = 0x22;

					Bundle bundle = new Bundle();
					bundle.clear();
	
					bundle.putString("text4", urlList.get(i));
					// bundle.putString("text4", json.getString("value"));
	
					msg.setData(bundle);
	
					myHandler.sendMessage(msg);
					i++;
				}
				Message msg = new Message();
				msg.what = 0x14;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
	 
	 public class ApacheHttpThreadGetThanksState extends Thread {
			String strs = "";

			public ApacheHttpThreadGetThanksState() {
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
					HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/get_question_thanks/");
					// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
					// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
					// 如果是Post提交可以将参数封装到集合中传递
					List dataList = new ArrayList();
		
					dataList.add(new BasicNameValuePair("question_id", questionID ));
					dataList.add(new BasicNameValuePair("current_uid", String.valueOf(userID)));
					
					
					// UrlEncodedFormEntity用于将集合转换为Entity对象
					httpPost.setEntity(new UrlEncodedFormEntity(dataList));
					// 获取相应消息
					HttpResponse httpResponse = client.execute(httpPost);
					// 获取消息内容
					HttpEntity entity = httpResponse.getEntity();
					// 把消息对象直接转换为字符串
					// String content = EntityUtils.toString(entity);
					// showTextView.setText(content);
					is3 = entity.getContent();     
					
					try {
			            BufferedReader reader = new BufferedReader(new InputStreamReader(is3, "UTF-8"));
			            StringBuilder sb = new StringBuilder();
			            String line = null;
			            while ((line = reader.readLine()) != null) {
			                sb.append(line + "\n");
			            }
			            is3.close();
			            json23 = sb.toString();
			            
			            
			            JSONObject json = new JSONObject(json23); 
				       
			            Message msg = new Message();
						msg.what = 0x11;
						Bundle bundle = new Bundle();
						bundle.clear();
						bundle.putString("text3", json.getString("value"));				
						msg.setData(bundle);

						myHandler.sendMessage(msg);

			          
			        } catch (Exception e) {
			            Log.e("Buffer Error", "@@@@@Error converting result " + e.toString());
			            Log.d("json", json23.toString());
			        }
			
				} catch (ClientProtocolException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		}
		
	 
	  public class ApacheHttpThreadThanks extends Thread {
			String strs = "";

			public ApacheHttpThreadThanks() {
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
					HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/question_thanks/");
					// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
					// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
					// 如果是Post提交可以将参数封装到集合中传递
					List dataList = new ArrayList();
		
					dataList.add(new BasicNameValuePair("question_id", String.valueOf(questionID) ));
					dataList.add(new BasicNameValuePair("current_uid", String.valueOf(userID)));
					dataList.add(new BasicNameValuePair("user_name", currentUserName ));
					
					// UrlEncodedFormEntity用于将集合转换为Entity对象
					httpPost.setEntity(new UrlEncodedFormEntity(dataList));
					// 获取相应消息
					HttpResponse httpResponse = client.execute(httpPost);
					// 获取消息内容
					HttpEntity entity = httpResponse.getEntity();
					// 把消息对象直接转换为字符串
					// String content = EntityUtils.toString(entity);
					// showTextView.setText(content);
					is2 = entity.getContent();     
					
					try {
			            BufferedReader reader = new BufferedReader(new InputStreamReader(is2, "UTF-8"));
			            StringBuilder sb = new StringBuilder();
			            String line = null;
			            while ((line = reader.readLine()) != null) {
			                sb.append(line + "\n");
			            }
			            is2.close();
			            json22 = sb.toString();
			            
			            
			            JSONObject json = new JSONObject(json22); 
				       
			            Message msg = new Message();
						msg.what = 0x19;
						Bundle bundle = new Bundle();
						bundle.clear();
						bundle.putString("text3", json.getString("value"));				
						msg.setData(bundle);

						myHandler.sendMessage(msg);

			          
			        } catch (Exception e) {
			            Log.e("Buffer Error", "Error converting result " + e.toString());
			            Log.d("json", json22);
			        }
			
				} catch (ClientProtocolException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		}
		


	// 转载请说明出处：http://blog.csdn.net/ff20081528/article/details/17845753
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			createVelocityTracker(event);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xDown = event.getRawX();
				Log.d("@@down", "xdown:"+xDown);
				break;
			case MotionEvent.ACTION_MOVE:
				xMove = event.getRawX();
				//活动的距离
				int distanceX = (int) (xMove - xDown);
				//获取顺时速度
				int xSpeed = getScrollVelocity();
				//当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity
				Log.d("@@move", "xmove:"+xMove +",speed :"+xSpeed);
				if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
					finish();
					//设置切换动画，从右边进入，左边退出
					overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
				}
				break;
			case MotionEvent.ACTION_UP:
				Log.d("@@up", "xDown:"+xDown+ ",xMove ="+xMove);
				recycleVelocityTracker();
				break;
			default:
				break;
			}
			return true;
		}
		
		/**
		 * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
		 * 
		 * @param event
		 *        
		 */
		private void createVelocityTracker(MotionEvent event) {
			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
			}
			mVelocityTracker.addMovement(event);
		}
		
		/**
		 * 回收VelocityTracker对象。
		 */
		private void recycleVelocityTracker() {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
		
		/**
		 * 获取手指在content界面滑动的速度。
		 * 
		 * @return 滑动速度，以每秒钟移动了多少像素值为单位。
		 */
		private int getScrollVelocity() {
			mVelocityTracker.computeCurrentVelocity(1000);
			int velocity = (int) mVelocityTracker.getXVelocity();
			return Math.abs(velocity);
		}
}
