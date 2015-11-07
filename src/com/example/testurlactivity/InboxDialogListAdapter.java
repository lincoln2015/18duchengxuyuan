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

public class InboxDialogListAdapter extends ArrayAdapter<InboxDialog> {
	
	  private LayoutInflater mInflater;
	   private TextView mContactInfoText;
	   private Context mContext;
	   ArrayList<InboxDialog> mInboxDialogList;
	   
	   ArrayList<Bitmap> bitMapList;

	 public InboxDialogListAdapter(Context context, int resource, ArrayList<InboxDialog> inboxDialogList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mInboxDialogList = inboxDialogList;
		
		this.bitMapList = null;
	}
	 
	public void updateListView(ArrayList<Bitmap> bitmapList){
			// this.mQuestionList = list;
			this.bitMapList = bitmapList;
			notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mInboxDialogList.size();
	}

	@Override
	public InboxDialog getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		
		return mInboxDialogList.get(position);
	}

	@Override
	public int getPosition(InboxDialog item) {
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
        
            view = mInflater.inflate(R.layout.personal_talk_list_item, parent, false);
        }

    	ImageView imgeView = (ImageView) view.findViewById(R.id.user_img);
    	
        TextView nameView = (TextView) view.findViewById(R.id.user_talk_to_name);
        
        TextView dialogContentView = (TextView) view.findViewById(R.id.dialog_content);
        
        TextView updateTimeView = (TextView) view.findViewById(R.id.update_time);
        
        TextView messageCountView = (TextView) view.findViewById(R.id.message_count_2);
        
       
      //  categoryTitleView.setText("from  " + mQuestionList.get(position).getCategoryTitle());
        //	nameView.setText(mNotificationList.get(position).getCategoryTitle());
        nameView.setText(mInboxDialogList.get(position).getUserNameTalkTo());
        dialogContentView.setText(mInboxDialogList.get(position).getDialogLastMessageContent());
        updateTimeView.setText(mInboxDialogList.get(position).getDialogUpdateTime());
        messageCountView.setText(String.valueOf(mInboxDialogList.get(position).getDialogMessageCount() + " Ìõ¶Ô»°"));
        
        
        
        if (bitMapList != null)
        {
        	if (bitMapList.get(position) != null)
        	{
        		imgeView.setImageBitmap(bitMapList.get(position));
        	
        		imgeView.setVisibility(View.VISIBLE);
        	}
        }
        else
        	imgeView.setVisibility(View.GONE);
        
        
        return view;
	}
	
	

}
