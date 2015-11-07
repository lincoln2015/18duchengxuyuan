package com.example.testurlactivity;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.example.testurlactivity.InviteUserActivity.ApacheHttpThreadInvite;
import com.example.testurlactivity.InviteUserActivity.urlThreadGetImg;
import com.example.testurlactivity.QuestionCommentActivity.ApacheHttpThread;
import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class FavorTagItemShowActivity extends Activity {
	
	ArrayList<QuestionAnswer> questionAnswerList ;
	FavorTagItemShowListAdapter mAdapter;
	ListView itemShowListView ;
	
	 ArrayList<Bitmap> bitMapList;
	 private Bitmap bitmap;
	 
	 
	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    		Bundle bundle = msg.getData();
	    		
		if (msg.what == 0x18) {

			Toast.makeText(getApplicationContext(), bundle.getString("flag") , Toast.LENGTH_LONG).show();
			
		

		}
	
	     }

	 };
	

	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	   
	   
	            
			if (v.getId() == R.id.back_ly) {

				finish();
				// 设置切换动画，从右边进入，左边退出
				overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
			}

		}

	    };
	

	public FavorTagItemShowActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.favor_item_show_list);
		
		Intent intent = getIntent();
		
		
		FavorTag favorTag =  (FavorTag) intent.getSerializableExtra("key"); 
		
		
		questionAnswerList = favorTag.getFavorTagItemList();
		
		
		itemShowListView = (ListView)findViewById(R.id.item_show_list);
		
        TextView answerCountTextView = (TextView)findViewById(R.id.answers_count_text);
		
        answerCountTextView.setText(String.valueOf(questionAnswerList.size()) + "个问答");
        
        
		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
        
		new urlThreadGetImg().start();
		 // bind 
		mAdapter = new FavorTagItemShowListAdapter(getApplicationContext(), R.layout.fava_item_show_list_item, questionAnswerList);

		itemShowListView.setAdapter(mAdapter);
		 
		itemShowListView.setOnItemClickListener(new OnItemClickListener(){

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
				
				
			 	bitMapList = new ArrayList<Bitmap>();
				while (i < questionAnswerList.size())
				{
					if (questionAnswerList.get(i).getPulishUserAvatarFile() !="")
					{
						URL url = new URL(questionAnswerList.get(i).getPulishUserAvatarFile().replace("localhost", URLHelper.IP_URL));
						InputStream is = url.openStream();
						bitmap = BitmapFactory.decodeStream(is);
						
						bitMapList.add(bitmap);
		
					}
					else
						bitMapList.add(null);
					
					i++;
				}
			
				mAdapter.updateListView(questionAnswerList ,bitMapList);
				
				Message msg = new Message();
				msg.what = 0x18;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
		

}
