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

public class UserListAdapter extends ArrayAdapter<User> {
	
	private LayoutInflater mInflater;
	private TextView mContactInfoText;
	private Context mContext;
	ArrayList<User> mUserList;
	
	ArrayList<Bitmap> bitMapList;

	public UserListAdapter(Context context, int resource,
			ArrayList<User> userList) {
		super(context, resource);
		// TODO Auto-generated constructor stub

		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mUserList = userList;
		
		this.bitMapList = null;
	}
	
	public void updateListView(ArrayList<User> list, 	 ArrayList<Bitmap> bitmapList){
		this.mUserList = list;
		this.bitMapList = bitmapList;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		  return mUserList.size();
	}

	@Override
	public User getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		   return mUserList.get(position);
	}

	@Override
	public int getPosition(User item) {
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
		// return super.getView(position, convertView, parent);
		
		
		View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
        
            view = mInflater.inflate(R.layout.question_focus_userlist_item, parent, false);
        }

        TextView userNameTextView = (TextView) view.findViewById(R.id.user_name);
        
        ImageView userImg = (ImageView) view.findViewById(R.id.user_img);
        TextView userIntroduceTextView = (TextView) view.findViewById(R.id.user_introduce);
      
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
      

       // TextView userNameTextView = (TextView) view.findViewById(R.id.user_name);
        if (mUserList.get(position).getSignature() != "" && mUserList.get(position).getSignature() != "null")
        	userIntroduceTextView.setText(mUserList.get(position).getSignature());
        else
        	userIntroduceTextView.setVisibility(View.GONE);
        
        userNameTextView.setText(mUserList.get(position).getUserName());
      //  userNameTextView.setText(mUserList.get(position).getUserName() + "   ");
      //  commentContentTextView.setText(mQuestionCommentList.get(position).getCommentContent() + "   ");
      
       
        //questionContentView.setText("hello");
        
       	        
        return view;
	}


	

}
