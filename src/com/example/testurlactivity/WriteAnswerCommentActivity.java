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

import com.example.testurlactivity.AnswerCommentsShowActivity.ApacheHttpThread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WriteAnswerCommentActivity extends Activity implements OnTouchListener{
	
	// ��ָ���һ���ʱ����С�ٶ�
	private static final int XSPEED_MIN = 200;
	// ��ָ���һ���ʱ����С����
	private static final int XDISTANCE_MIN = 150;
	// ��¼��ָ����ʱ�ĺ����ꡣ
	private float xDown;
	// ��¼��ָ�ƶ�ʱ�ĺ����ꡣ
	private float xMove;
	// ���ڼ�����ָ�������ٶȡ�
	private VelocityTracker mVelocityTracker;
	
	
	
	private static int USER_START_DETAIL_REQUEST = 10000;
	private static int USER_END_DETAIL_REQUEST = 10001;
	ArrayList<AnswerComment> mAnswerCommentList;

	ListView mListView;

	AnswerCommentsListAdapter  mAnswerCommentsListAdapter;
	

	String commentMsg;
	int currentUserId;
	int inviteUserId;
	int userID;
	String currentUserName;
	
	User user;
	
	static InputStream is = null;
	static String json2 = "";

	String answerID;

	SharedPreferences sharedPreferences;

	EditText editComment;

	String mentionUserName = null;
	int mentionUserId;

	CommonDialog dialog2 = null;
	View sureLy;
	String warningMsg = "";
	TextView warningMsgTextView = null;
	
	
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    
	         if (msg.what == 0x16) {
	     				Bundle bundle = msg.getData();

	     			//	showTextView.setText(bundle.getString("text2"));
	                 
	         }
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();
					
					//showTextView.setText(bundle.getString("text3"));

						//Log.d("apche", bundle.getString("text3"))
					//	Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
				/*	webView.loadDataWithBaseURL(null, bundle.getString("text3"), "text/html",
							"utf-8", null);
					*/
					
		
	         }
	  
	         
	      
	        
	     }

	 };
	
	
	
	
	
	
		
		 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	        	
	        	if (v.getId() == R.id.sure_ly) {
	                
	            		dialog2.dismiss();
	     			
	     			//mMessage.setText("������...");
	            	           
	                }
	        	
	        	if (v.getId() == R.id.back_ly) {

					/*
					 * Toast.makeText(getApplicationContext(), "click add question",
					 * Toast.LENGTH_SHORT).show();
					 */
					// new a thread to update the qusetion item foucu_count
					finish();
					// �����л����������ұ߽��룬����˳�
					overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
				}
	    
	        	if (v.getId() == R.id.cancel) {
	                
	    		 	// finish();
		    		// new a thread to send to server to check user lgoin 
	        		 finish();
	           	           
	         }
	        if (v.getId() == R.id.publish) {
	        		commentMsg = editComment.getText().toString();
	        		 if (commentMsg.equals(""))
	              	 {
	              		// alertQuestionContent();
	              		  warningMsgTextView.setText("δ��������");
	              		  if (dialog2 != null)
	              			  dialog2.show();//��ʾDialog

	              	 }
	        		 else
	        		 {
			        	  new ApacheHttpThread().start();	  
			        	  finish();
	        		 }
	         }
	        if (v.getId() == R.id.to_user_ly) {
        	        	   
        		Intent intent = new Intent(getApplication(),WriteAnswerCommentMentionUserActivity.class);

				 startActivityForResult(intent, USER_START_DETAIL_REQUEST);
				 
				 overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	        }
   
	   
	        } 

	    };

 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			

			
			if(requestCode == USER_START_DETAIL_REQUEST && resultCode == USER_END_DETAIL_REQUEST)
	        {
				 Bundle bundle = data.getExtras();
				   
				   // ��ȡBundle�е����ݣ�ע�����ͺ�key
				 mentionUserName = bundle.getString("user_name");
				 mentionUserId = bundle.getInt("user_id", 1);
			
				// Toast.makeText(getApplicationContext(), questionDetailStr, Toast.LENGTH_SHORT).show();
	        }
			
		
			
			  // Toast.makeText(getApplicationContext(), "on activity result", Toast.LENGTH_SHORT).show();
	}
 
	public WriteAnswerCommentActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.write_answer_comment);
		
		Intent intent = getIntent();
		
		answerID = intent.getStringExtra("answer_id");
		
	
	    SharedPreferences sharedPreferences = this.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    currentUserId = sharedPreferences.getInt("user_id", 1);
        
        currentUserName = sharedPreferences.getString("name", "");
        
        TextView publishView = (TextView)findViewById(R.id.publish);
        
        editComment = (EditText)findViewById(R.id.edit_comment);
        
        View toUserView = (View)findViewById(R.id.to_user_ly);
        
        
        publishView.setOnClickListener(onClickListener);
        toUserView.setOnClickListener(onClickListener);
        
        
    	LinearLayout ll = (LinearLayout) findViewById(R.id.write_answer_ly);
		ll.setOnTouchListener(this);

		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		
		dialog2 = new CommonDialog(WriteAnswerCommentActivity.this, 250, 100, R.layout.common_dialog, R.style.Theme_dialog);
		warningMsgTextView = (TextView) dialog2.findViewById(R.id.warning_message);
		sureLy = (View) dialog2.findViewById(R.id.sure_ly);
		sureLy.setOnClickListener(onClickListener);
        
       // new ApacheHttpThread().start();	
		
	}
	
	

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mentionUserName != null)
			editComment.setText("@"+mentionUserName);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
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
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/save_answer_comment/?answer_id="+answerID+"&current_uid="+currentUserId+"&message="+commentMsg);
				// HttpGet httpGet = new HttpGet("http://192.168.0.104/wecenter2/?/explore/ajax/list/");
				// HttpGet httpGet = new HttpGet("http://192.168.0.104/wecenter2/?flagmobile=1");
				// ��ȡ��Ӧ��Ϣ
				HttpResponse httpResponse = client.execute(httpGet);
				// ��ȡ��Ϣ����
				HttpEntity entity = httpResponse.getEntity();
				 
				is = entity.getContent();     
				// ����Ϣ����ֱ��ת��Ϊ�ַ���
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				
				
				/////////////////////*************////////////////////
				/*
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

					// JSONArray json = new
					// JSONObject(sb.toString()).getJSONArray("value");
					JSONArray json = new JSONObject(sb.toString()).getJSONArray("value");

					for (int i = 0; i < json.length(); i++) {
						JSONObject jsonObject = (JSONObject) json.opt(i);
						AnswerComment answerComment = new AnswerComment(jsonObject);
					
						mAnswerCommentList.add(answerComment);
					}

					Message msg = new Message();
					msg.what = 0x17;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					// bundle.putString("text3",String.valueOf(len));
					bundle.putString("text3", json2);

					msg.setData(bundle);

					myHandler.sendMessage(msg);
		         
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		            Log.d("json", json2.toString());
		        }
				 */
				
				/////////////////////*************////////////////////

				
			
				
				
				// ����url�����н���
				// webView.loadUrl(url);
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	// ת����˵��������http://blog.csdn.net/ff20081528/article/details/17845753
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
					//��ľ���
					int distanceX = (int) (xMove - xDown);
					//��ȡ˳ʱ�ٶ�
					int xSpeed = getScrollVelocity();
					//�������ľ�����������趨����С�����һ�����˲���ٶȴ��������趨���ٶ�ʱ�����ص���һ��activity
					Log.d("@@move", "xmove:"+xMove +",speed :"+xSpeed);
					if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
						finish();
						//�����л����������ұ߽��룬����˳�
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
			 * ����VelocityTracker���󣬲�������content����Ļ����¼����뵽VelocityTracker���С�
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
			 * ����VelocityTracker����
			 */
			private void recycleVelocityTracker() {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			
			/**
			 * ��ȡ��ָ��content���滬�����ٶȡ�
			 * 
			 * @return �����ٶȣ���ÿ�����ƶ��˶�������ֵΪ��λ��
			 */
			private int getScrollVelocity() {
				mVelocityTracker.computeCurrentVelocity(1000);
				int velocity = (int) mVelocityTracker.getXVelocity();
				return Math.abs(velocity);
			}
	

}
