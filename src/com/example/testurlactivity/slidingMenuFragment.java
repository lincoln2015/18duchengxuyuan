package com.example.testurlactivity;


import com.example.testurlactivity.homeFragment.ApacheHttpThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class slidingMenuFragment extends Fragment {
	
	String currentUserName;
	SharedPreferences sharedPreferences;
	
	Fragment homeContent = null;
	Fragment notificationContent = null;
	
	Activity thisActivity =null;
	
	int USER_LOGIN_START_REQUEST = 1004;
	int USER_LOGIN_END_REQUEST = 1005;
	
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	   
	        
	             if (v.getId() == R.id.home_ly) {
	            	 
	            	 homeContent = new homeFragment();
	            	 switchFragment(homeContent);
	            
	                }
	             
	             if (v.getId() == R.id.name_ly) {
 
	            	 if (URLHelper.ISLOGIN == true)
	            	 {
	            		 switchFragment(new UserDetailFragment());
		            	 
	            	 }
	            	 else
	            	 {		
	            		
	            	/*	 Intent intent = new Intent(thisActivity, UserLoginActivity.class);   
	            
		            	 startActivity(intent);*/
		            	 
		            	 
		            	Intent intent = new Intent(thisActivity, UserLoginActivity.class);

		    	 		startActivityForResult(intent, USER_LOGIN_START_REQUEST);
		            	 
		            	 
	            	 }
	            		
	            
	                }
	             if (v.getId() == R.id.focus_ly) {
 
		            	 if (URLHelper.ISLOGIN == true)
		            	 {
		            		 switchFragment(new FocusQuestionsFragment());
			            	 
		            	 }
		            	 else
		            	 {		
		            		
		            		 
				            	Intent intent = new Intent(thisActivity, UserLoginActivity.class);

				    	 		startActivityForResult(intent, USER_LOGIN_START_REQUEST);
		            	 }
		            
	                }
	             if (v.getId() == R.id.collection_ly) {

		            	 if (URLHelper.ISLOGIN == true)
		            	 {
		            		 switchFragment(new MyFavorFragment());
			            	 
		            	 }
		            	 else
		            	 {		
		            		
		            		 
				            	Intent intent = new Intent(thisActivity, UserLoginActivity.class);

				    	 		startActivityForResult(intent, USER_LOGIN_START_REQUEST);
		            	 }
	            
	                }
	             if (v.getId() == R.id.personal_talk_ly) {
	            	
	            	 
		            	 if (URLHelper.ISLOGIN == true)
		            	 {
		            		 switchFragment(new PersonalTalkFragment());
			            	 
		            	 }
		            	 else
		            	 {		
		            		
		            		 
				            	Intent intent = new Intent(thisActivity, UserLoginActivity.class);

				    	 		startActivityForResult(intent, USER_LOGIN_START_REQUEST);
		            	 }
	            	 
	            
	                }
	          
	             if (v.getId() == R.id.message_ly) {

		            	 if (URLHelper.ISLOGIN == true)
		            	 {
		            		 notificationContent = new NotificationFragment();
			            	 switchFragment(notificationContent);
			            	 
			            	 
		            	 }
		            	 else
		            	 {		
		            		
		            		 
				            	Intent intent = new Intent(thisActivity, UserLoginActivity.class);

				    	 		startActivityForResult(intent, USER_LOGIN_START_REQUEST);
		            	 }
	            
	                }
	             
	             if (v.getId() == R.id.account_setting_ly) {
    	 
		            	 if (URLHelper.ISLOGIN == true)
		            	 {
		            		 switchFragment(new AccountSettingFragment());
			            	 
		            	 }
		            	 else
		            	 {		
		            		 
				            	Intent intent = new Intent(thisActivity, UserLoginActivity.class);

				    	 		startActivityForResult(intent, USER_LOGIN_START_REQUEST);
		            	 }
		            	 
	                }
	             if (v.getId() == R.id.search) {

	            	 if (URLHelper.ISLOGIN == true)
	            	 {
	            		 switchFragment(new SearchFragment());
		            	 
	            	 }
	            	 else
	            	 {		
	            		 
			            	Intent intent = new Intent(thisActivity, UserLoginActivity.class);

			    	 		startActivityForResult(intent, USER_LOGIN_START_REQUEST);
	            	 }
	            	 
	                }
	             if (v.getId() == R.id.add_question_ly) {
	            	 
	            	 if (URLHelper.ISLOGIN == true)
	            	 {
	            		 switchFragment(new AddQuestionFragment());
		            	 
	            	 }
	            	 else
	            	 {		
	            		 
			            	Intent intent = new Intent(thisActivity, UserLoginActivity.class);

			    	 		startActivityForResult(intent, USER_LOGIN_START_REQUEST);
	            	 }
	            	 
                }
	             
	             
	    
	        }

	    };

	    @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			

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

		}


	public slidingMenuFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		thisActivity = getActivity();
		View view = inflater.inflate(R.layout.slidingmenu, null);
		
	    
		View btn = (View) view.findViewById(R.id.home_ly);
		      
		btn.setOnClickListener(onClickListener);
		
		
		View btn3 = (View) view.findViewById(R.id.name_ly);
	      
		btn3.setOnClickListener(onClickListener);
		
		
		View btn4 = (View) view.findViewById(R.id.focus_ly);
	      
		btn4.setOnClickListener(onClickListener);
		
		
		View btn5 = (View) view.findViewById(R.id.collection_ly);
	      
		btn5.setOnClickListener(onClickListener);
		
		View btn6 = (View) view.findViewById(R.id.personal_talk_ly);
	      
		btn6.setOnClickListener(onClickListener);
		
		View btn7 = (View) view.findViewById(R.id.message_ly);
	      
		btn7.setOnClickListener(onClickListener);
		
		View btn8 = (View) view.findViewById(R.id.add_question_ly);
	      
		btn8.setOnClickListener(onClickListener);
		
		View btn9 = (View) view.findViewById(R.id.search);
	      
		btn9.setOnClickListener(onClickListener);
		
		
		View btn10 = (View) view.findViewById(R.id.account_setting_ly);
	      
		btn10.setOnClickListener(onClickListener);
		
		
		
		TextView nameTextView = (TextView) view.findViewById(R.id.name); 
		
	
		  // ªÿœ‘
	   sharedPreferences = thisActivity.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	   String currentUserName = sharedPreferences.getString("name", "");
		
		
		
		//nameTextView.setText(currentUserName);
	
		
		return view;	
	}
	
	
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof TestURLActivity) {
			TestURLActivity fca = (TestURLActivity) getActivity();
			fca.switchContent(fragment);
		} 
	}

}
