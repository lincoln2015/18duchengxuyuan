package com.example.testurlactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NotificationUsersShowActivity extends Activity {
	
	 private ArrayList<User> mUserList;
	 
	 private ListView mUserListView;
	 
	 private View mRootView;
	 private UserListAdapter mAdapter;
	 


	public NotificationUsersShowActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.notification_users_show);
		
		
		Intent intent = getIntent();
		
		
		//mUserList = new ArrayList<User>();
		
		mUserList =  (ArrayList<User>) intent.getSerializableExtra("key"); 
		
		
		 mUserListView = (ListView)findViewById(R.id.user_list);
			
			// int question list
			  
			 // bind 
		mAdapter = new UserListAdapter(getApplicationContext(), R.layout.question_focus_userlist_item, mUserList);


			 //set adapter	
		mUserListView.setAdapter(mAdapter);
			 
			 

		mUserListView.setOnItemClickListener(new OnItemClickListener(){

				 	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				 		//Toast.makeText(getApplicationContext(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
				 	
				 		
				 		
				 			Intent intent = new Intent(getApplicationContext(),	userDetailActivity.class);
					
					 		Bundle mBundle = new Bundle();    
					  		 mBundle.putSerializable("key", mAdapter.getItem(arg2));    
					  		 intent.putExtras(mBundle);    
				
					  		// startActivityForResult(intent,USER_DETAIL_START_REQUEST);
					  		startActivity(intent);
						 	
				
				 	
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
	
}
