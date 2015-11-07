package com.example.testurlactivity;

import java.io.InputStream;
import java.net.URL;

import com.example.testurlactivity.UserDetailFragment.urlThreadGetImg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserDetailForJudgeActivity extends Activity implements OnTouchListener{
	
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
	
	TextView  agreeCountTextView ;
	TextView thanksCountTextView;
	TextView inviteCountTextView;
	TextView reputationCountTextView;
	TextView userNameTextView;
	TextView userSexTextView;
	

	
	String  agreeCountStr;
	String   thanksCountStr;
	String   inviteCountStr;
	String  reputationCountStr;
	String   userNameStr;
	String  userSexStr;
	
	
	String av_file ="";
	private Bitmap bitmap = null;
	ImageView userImg;
	
	
	public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    
	       
	         if (msg.what == 0x17) {
					Bundle bundle = msg.getData();

					// showTextView.setText(bundle.getString("text2"))
			
					userImg.setImageBitmap(bitmap);
					
					userImg.setVisibility(View.VISIBLE);

			
		         }
	
	        
	     }

	 };
	
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	    
	        	
	        	if (v.getId() == R.id.back_ly) {

					finish();
					// �����л����������ұ߽��룬����˳�
					overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
				}
	   
	        } 

	    };


	public UserDetailForJudgeActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.user_detail_for_alive_judge);
		
		Intent intent = getIntent();
		
		User user =  (User) intent.getSerializableExtra("key"); 
		agreeCountStr = String.valueOf(user.getAgreeCount());
		thanksCountStr =String.valueOf(user.getThanksCount()) ;
		inviteCountStr =String.valueOf(user.getInviteCount()) ;
		reputationCountStr = String.valueOf(user.getReputation());
		userNameStr = user.getUserName();
		userSexStr = user.getSex();
		
		av_file = user.getAvatarFile();
		av_file = av_file.replace("localhost", URLHelper.IP_URL);
		
		userImg = (ImageView) findViewById(R.id.user_img);
		userImg.setVisibility(View.GONE);

		
		agreeCountTextView = (TextView) findViewById(R.id.agree_count);
		thanksCountTextView = (TextView) findViewById(R.id.thanks_count);
		inviteCountTextView = (TextView) findViewById(R.id.invite_count);
		reputationCountTextView = (TextView) findViewById(R.id.reputation_count);
		userNameTextView = (TextView) findViewById(R.id.user_name);
		userSexTextView = (TextView) findViewById(R.id.user_sex);
		
		
		agreeCountTextView.setText(agreeCountStr);
		thanksCountTextView.setText(thanksCountStr);
		inviteCountTextView.setText(inviteCountStr);
		reputationCountTextView.setText(reputationCountStr);
		userNameTextView.setText(userNameStr);
		userSexTextView.setText("  "+userSexStr);
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.user_detail_judge_ly);
		ll.setOnTouchListener(this);
		
	    LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		    
		new urlThreadGetImg().start();
		
	
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
					
					

					URL url = new URL(av_file);
					InputStream is = url.openStream();
					bitmap = BitmapFactory.decodeStream(is);

					Message msg = new Message();
					msg.what = 0x17;

					myHandler.sendMessage(msg);

				} catch (Exception e) {

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
