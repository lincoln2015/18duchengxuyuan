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

public class NotificationListAdapter extends ArrayAdapter<Notification> {
	
	   private LayoutInflater mInflater;
	   private TextView mContactInfoText;
	   private Context mContext;
	   ArrayList<Notification> mNotificationList;

	 public NotificationListAdapter(Context context, int resource, ArrayList<Notification> notificationList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mNotificationList = notificationList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mNotificationList.size();
	}

	@Override
	public Notification getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		
		return mNotificationList.get(position);
	}

	@Override
	public int getPosition(Notification item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
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
        
        
        View nameLy = (View) view.findViewById(R.id.name_ly);
        View questionlMessageLy = (View) view.findViewById(R.id.question_message_ly);
        View answerContentLy = (View) view.findViewById(R.id.answer_content_ly);
      
       
      //  categoryTitleView.setText("from  " + mQuestionList.get(position).getCategoryTitle());
        nameView.setText(mNotificationList.get(position).getUserName());
        actionsMessageView.setText(mNotificationList.get(position).getMessage());
        questionArticalMessageView.setText(mNotificationList.get(position).getQuestionContent());
        answerContentView.setText(mNotificationList.get(position).getAnswerContent());
        addTimeView.setText(mNotificationList.get(position).getAddTime());
        
        
        String contentStr = mNotificationList.get(position).getAnswerContent();
        if (contentStr =="")
        	answerContentView.setVisibility(View.GONE);
        else
        {
        
            RegexUtil2 regeUtil = new RegexUtil2();
            
            String tmpStr =  regeUtil.geTtextOfHtml(contentStr);
            
            tmpStr = regeUtil.replaceBlank(tmpStr);
            
            if (tmpStr.length() == 0)
            	answerContentView.setText("[图片]");
            else
            	answerContentView.setText(tmpStr);
            

            answerContentView.setVisibility(View.VISIBLE);
        }
        
        
        nameLy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(mContext, userDetailActivity.class);
				Bundle mBundle = new Bundle();    
		  		mBundle.putSerializable("key", getItem(position).getUser());    
		  		// Toast.makeText(getActivity(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
		 		intent.putExtras(mBundle);
		 		mContext.startActivity(intent);
		
		 		
		 
			}
		});
        
        questionlMessageLy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

		 		Intent intent = new Intent(mContext, questionDetailActivity.class);
			
		 		Bundle mBundle = new Bundle();    
		  		mBundle.putSerializable("key", getItem(position).getQuestion());    
		  		// Toast.makeText(getActivity(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
		 		intent.putExtras(mBundle);    
		 	  
				
		 		mContext.startActivity(intent);
		
		 		
		 
			}
		});
  
        answerContentLy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		 	 Intent intent = new Intent(mContext, AnswerContentDetailActivity.class);
				  /* 通过Bundle对象存储需要传递的数据 */
           	  Bundle bundle = new Bundle();
           	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
           	  bundle.putString("answer_id", String.valueOf(getItem(position).getAnswerId()));
           	
           	  /*把bundle对象assign给Intent*/
           	  intent.putExtras(bundle);

           	  mContext.startActivity(intent); 
				
			}
		});
        
        return view;
	}
	
	

}
