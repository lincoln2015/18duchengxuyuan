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

import com.example.testurlactivity.homeFragment.httpUrlConnectionThread;
import com.example.testurlactivity.homeFragment.urlConnectThread;
import com.example.testurlactivity.homeFragment.urlThread;
import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread;
import com.example.testurlactivity.questionDetailActivity.ApacheHttpThreadGetFocusInfo;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryShowDetailActivity extends Activity implements OnTouchListener {
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
	
	
	int categoryId;
	int userID;
	String categoryTitle;

	 
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	   
	        	Intent intent = new Intent(getApplicationContext(),	CategoryQuestionsActivity.class);	 
	        	Bundle bundle = new Bundle();
           	  	bundle.putString("category_id", String.valueOf(categoryId));
           	
	            
	            if (v.getId() == R.id.addtime_order_ly) {
		            	//  Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
	            	/* Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();*/
	            	
	            	  bundle.putString("order_flag", "add_time");
	            	  
	            	  intent.putExtras(bundle);

	 				  startActivity(intent);
	 				  
	 				  overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		            	
			       }
	            if (v.getId() == R.id.answercount_order_ly) {
	            	
	              bundle.putString("order_flag", "answer_count");
	            	
            	  intent.putExtras(bundle);

 				  startActivity(intent);
 				  
 				 overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	            	
		       }
	            if (v.getId() == R.id.agreecount_order_ly) {
	          
	              bundle.putString("order_flag", "agree_count");
	              
            	  intent.putExtras(bundle);

 				  startActivity(intent);
 				  
 				  overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	            	
		       }
	            if (v.getId() == R.id.viewcount_order_ly) {
	            	
	              bundle.putString("order_flag", "view_count");
	            	
            	  intent.putExtras(bundle);

 				  startActivity(intent);
 				  
 				  overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	            	
		       }
	            
	            if (v.getId() == R.id.fanscount_order_ly) {
	            	
	              bundle.putString("order_flag", "focus_count");
	            
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
					finish();
					// �����л����������ұ߽��룬����˳�
					overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
				}
       
	        }

	    };



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.category_show_detail_show);
		
		
		Intent intent = getIntent();
		categoryId = Integer.valueOf(intent.getStringExtra("category_id"));
		
		categoryTitle = intent.getStringExtra("category_title");
		
		
		
		TextView titleView = (TextView) findViewById(R.id.title);
		View addtimeOrderView = (View) findViewById(R.id.addtime_order_ly);
		View answercountOrderView = (View) findViewById(R.id.answercount_order_ly);
		View agreecountOrderView = (View) findViewById(R.id.agreecount_order_ly);
		View viewcountOrderView = (View) findViewById(R.id.viewcount_order_ly);
		View fanscountOrderView = (View) findViewById(R.id.fanscount_order_ly);
	
		
		SharedPreferences sharedPreferences = getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
        userID = sharedPreferences.getInt("user_id", 1);
        
        titleView.setText(categoryTitle);
        
        addtimeOrderView.setOnClickListener(onClickListener);
        answercountOrderView.setOnClickListener(onClickListener);
        agreecountOrderView.setOnClickListener(onClickListener);
        viewcountOrderView.setOnClickListener(onClickListener);
        fanscountOrderView.setOnClickListener(onClickListener);
        
        
		LinearLayout ll = (LinearLayout) findViewById(R.id.category_show_ly);
		ll.setOnTouchListener(this);

		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
      	

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
