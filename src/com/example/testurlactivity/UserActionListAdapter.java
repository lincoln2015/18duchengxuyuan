package com.example.testurlactivity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UserActionListAdapter extends ArrayAdapter<UserAction> {
	
	
	   private LayoutInflater mInflater;
	   private Context mContext;
	   ArrayList<UserAction> mUserActionList;

	 public UserActionListAdapter(Context context, int resource, ArrayList<UserAction> userActionList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mUserActionList = userActionList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mUserActionList.size();
	}

	@Override
	public UserAction getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		
		return mUserActionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return super.getView(position, convertView, parent);
		

		View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
        
            view = mInflater.inflate(R.layout.notification_list_item, parent, false);
        }

        TextView nameView = (TextView) view.findViewById(R.id.name);
        
        TextView actionsMessageView = (TextView) view.findViewById(R.id.actions_message);
        
        TextView questionArticalMessageView = (TextView) view.findViewById(R.id.question_artical_message);
        
        TextView answerContentView = (TextView) view.findViewById(R.id.answer_content);
        TextView addTimeView = (TextView) view.findViewById(R.id.add_time);
       
      //  categoryTitleView.setText("from  " + mQuestionList.get(position).getCategoryTitle());
        nameView.setText(mUserActionList.get(position).getUser().getUserName());
        actionsMessageView.setText(mUserActionList.get(position).getLastAction());
        
        if (mUserActionList.get(position).getQuestion() != null)
        	questionArticalMessageView.setText(mUserActionList.get(position).getQuestion().getContent());
        
        if (mUserActionList.get(position).getQuestionAnswer() != null)
        {
        		//answerContentView.setText(mUserActionList.get(position).getQuestionAnswer().getAnserContent());
    	
 	        	String contentStr = mUserActionList.get(position).getQuestionAnswer().getAnserContent();

 	            RegexUtil2 regeUtil = new RegexUtil2();
 	            
 	            String tmpStr =  regeUtil.geTtextOfHtml(contentStr);
 	            
 	            tmpStr = regeUtil.replaceBlank(tmpStr);
 	            
 	            if (tmpStr.length() == 0)
 	            	answerContentView.setText("[图片]");
 	            else
 	            	answerContentView.setText(tmpStr);
 	      
        }
       else
        	answerContentView.setVisibility(View.GONE);
        
        addTimeView.setText(mUserActionList.get(position).getAddTime());
        
        
        nameView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	
		 		
		 	//Toast.makeText(mContext, String.valueOf(getItem(position).getUser().getUserName()), Toast.LENGTH_LONG).show();
		
		 	Intent intent = new Intent(mContext, userDetailActivity.class);
			Bundle mBundle = new Bundle();    
	  		mBundle.putSerializable("key", getItem(position).getUser());    
	  		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 		intent.putExtras(mBundle);
	 		mContext.startActivity(intent);
	 		
			}
		});
        
        questionArticalMessageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

		 		Intent intent = new Intent(mContext, questionDetailActivity.class);
			
		 		Bundle mBundle = new Bundle();    
		  		mBundle.putSerializable("key", getItem(position).getQuestion());    
		  		// Toast.makeText(getActivity(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
		  		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		  		intent.putExtras(mBundle);    
		 	  
				
		 		mContext.startActivity(intent);
		
		 		
		 
			}
		});

        answerContentView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		 	 Intent intent = new Intent(mContext, AnswerContentDetailActivity.class);
				  /* 通过Bundle对象存储需要传递的数据 */
           	  Bundle bundle = new Bundle();
           	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
           	  bundle.putString("answer_id", String.valueOf(getItem(position).getQuestionAnswer().getAnswerId()));
           	
           	  /*把bundle对象assign给Intent*/
           	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           	  intent.putExtras(bundle);

           	  mContext.startActivity(intent); 
				
			}
		});
        
        return view;
	}
	

}
