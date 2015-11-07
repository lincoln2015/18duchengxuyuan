package com.example.testurlactivity;

import java.io.InputStream;
import java.util.ArrayList;

import com.example.testurlactivity.AddCommentActivity.ApacheHttpThread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class AddFavorTagActivity extends Activity {
	
	

	int currentUserId;
	int inviteUserId;
	int userID;
	String currentUserName;
	
	User user;
	
	 static InputStream is = null;
	 static String json2 = "";
	

	 
	SharedPreferences sharedPreferences;
	
	EditText editTitel;
	EditText editDiscription;
	
	String titelStr;
	String discriptionStr;
	
	 View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {
	        	
	        	
	        	
	        	   if (v.getId() == R.id.finish) {
	        		   
	        		   titelStr = editTitel.getText().toString();
	        		   discriptionStr = editDiscription.getText().toString();
	        		   // should connect to server to submit the add action and then call finsh 
	              
	        		 	// finish();
	               	           
	                   }
	   
	            
	         
	          
	        }

	    };

	public AddFavorTagActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_favor_tag);
		
		Intent intent = getIntent();
		
		// questionID = intent.getStringExtra("question_id");
		
	
	    SharedPreferences sharedPreferences = this.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    currentUserId = sharedPreferences.getInt("user_id", 1);
        
        currentUserName = sharedPreferences.getString("name", "");
        
    	View finishText = findViewById(R.id.finish);
    	finishText.setOnClickListener(onClickListener);
        
     	editTitel =(EditText) findViewById(R.id.edit_title);
     	editDiscription =(EditText) findViewById(R.id.edit_discription);
    	
  	
 
	
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
	
	

}
