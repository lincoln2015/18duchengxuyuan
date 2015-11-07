package com.example.testurlactivity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InboxMessageListAdapter extends ArrayAdapter<InboxMessage> {
	
	  private LayoutInflater mInflater;
	   private Context mContext;
	   ArrayList<InboxMessage> mInboxMessageList;
	   ArrayList<Bitmap> bitMapList ;

	 public InboxMessageListAdapter(Context context, int resource, ArrayList<InboxMessage> inboxMessage) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mInboxMessageList = inboxMessage;
		bitMapList = null;
	}
	 
	public void updateListView(ArrayList<InboxMessage> list, ArrayList<Bitmap> bitmpList ){
			this.mInboxMessageList = list;
			this.bitMapList = bitmpList;
			notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mInboxMessageList.size();
	}

	@Override
	public InboxMessage getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		return mInboxMessageList.get(position);
	}

	@Override
	public int getPosition(InboxMessage item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
        
            view = mInflater.inflate(R.layout.inbox_message_item, parent, false);
        }

        TextView timeView = (TextView) view.findViewById(R.id.message_publish_time);
        
        TextView selfMessagetView = (TextView) view.findViewById(R.id.self_message);
        
        TextView otherMessageView = (TextView) view.findViewById(R.id.other_message);
        
        ImageView otherImg = (ImageView) view.findViewById(R.id.other_img);
        
        ImageView selfImg = (ImageView) view.findViewById(R.id.self_img);
        
        
        timeView.setText(mInboxMessageList.get(position).getAddTime());
        
        if (mInboxMessageList.get(position).getIsSelfMessage() == 1)
        {
        	selfMessagetView.setText(mInboxMessageList.get(position).getMessage());
        	otherMessageView.setVisibility(View.GONE);
        	
        	otherImg.setVisibility(View.GONE);
        	
        	 if (bitMapList != null)
 	        {
 	        	if (bitMapList.get(position) != null)
 	        	{
 	        		selfImg.setImageBitmap(bitMapList.get(position));
 	        	
 	        		selfImg.setVisibility(View.VISIBLE);
 	        	}
 	        }
 	        else
 	        	selfImg.setVisibility(View.GONE);
 	        	
        	
        }
        else
        {
        	otherMessageView.setText(mInboxMessageList.get(position).getMessage());
        	selfMessagetView.setVisibility(View.GONE);
        	selfImg.setVisibility(View.GONE);
        	
        	
        	
        	 if (bitMapList != null)
  	        {
  	        	if (bitMapList.get(position) != null)
  	        	{
  	        		otherImg.setImageBitmap(bitMapList.get(position));
  	        	
  	        		otherImg.setVisibility(View.VISIBLE);
  	        	}
  	        }
  	        else
  	        	otherImg.setVisibility(View.GONE);
        }
 
      //  categoryTitleView.setText("from  " + mQuestionList.get(position).getCategoryTitle());
        //	nameView.setText(mNotificationList.get(position).getCategoryTitle());
      /*  nameView.setText(mInboxDialogList.get(position).getUserNameTalkTo());
        dialogContentView.setText(mInboxDialogList.get(position).getDialogLastMessageContent());
        updateTimeView.setText(mInboxDialogList.get(position).getDialogUpdateTime());
        messageCountView.setText(String.valueOf(mInboxDialogList.get(position).getDialogMessageCount()));*/
        
        return view;
	}
	
	
	

}
