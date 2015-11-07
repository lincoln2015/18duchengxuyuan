package com.example.testurlactivity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerCommentsListAdapter extends ArrayAdapter<AnswerComment> {
	
	
	  private LayoutInflater mInflater;
	   private TextView mContactInfoText;
	   private Context mContext;
	   ArrayList<AnswerComment> mAnswerCommentList;

		ArrayList<Bitmap> bitMapList;

	 public AnswerCommentsListAdapter(Context context, int resource, ArrayList<AnswerComment> answerCommentList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mAnswerCommentList = answerCommentList;
	}
	 
	public void updateListView(ArrayList<AnswerComment> list, 	 ArrayList<Bitmap> bitmapList){
			this.mAnswerCommentList = list;
			this.bitMapList = bitmapList;
			notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		return mAnswerCommentList.size();
	}

	@Override
	public AnswerComment getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		
		return mAnswerCommentList.get(position);
	}

	@Override
	public int getPosition(AnswerComment item) {
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
        
            view = mInflater.inflate(R.layout.answer_comment_item, parent, false);
        }

        TextView userNameView = (TextView) view.findViewById(R.id.user_name);
        
        TextView commentMsgView = (TextView) view.findViewById(R.id.comment_msg);
        
        TextView timeView = (TextView) view.findViewById(R.id.time);
        
     
        userNameView.setText(mAnswerCommentList.get(position).getUserName());
        commentMsgView.setText(mAnswerCommentList.get(position).getMessage());
        timeView.setText(mAnswerCommentList.get(position).getTime());
        
        ImageView userImg = (ImageView) view.findViewById(R.id.user_img);
        
      
        if (bitMapList != null)
        {
        	if (bitMapList.get(position) != null)
        	{
        		userImg.setImageBitmap(bitMapList.get(position));
        		userImg.setVisibility(View.VISIBLE);
        	}
        }
        else
        	userImg.setVisibility(View.GONE);
  
        
        return view;
	}
	
	
	

}
