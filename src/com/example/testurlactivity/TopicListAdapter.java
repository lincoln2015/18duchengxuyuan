package com.example.testurlactivity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TopicListAdapter extends ArrayAdapter<Topic> {
	
	private LayoutInflater mInflater;
	private TextView mContactInfoText;
	private Context mContext;
	ArrayList<Topic> mTopicList;

	public TopicListAdapter(Context context, int resource,
			ArrayList<Topic> topicList) {
		super(context, resource);
		// TODO Auto-generated constructor stub

		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mTopicList = topicList;
	}
	
	@Override
	public Topic getItem(int position) {
		// TODO Auto-generated method stub
	//	return super.getItem(position);
		
		return mTopicList.get(position);
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mTopicList.size();
	}

	@Override
	public int getPosition(Topic item) {
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
        
            view = mInflater.inflate(R.layout.focus_topic_list_item, parent, false);
        }

        TextView topicTitleTextView = (TextView) view.findViewById(R.id.topic_title);
        TextView topicDicriptionTextView = (TextView) view.findViewById(R.id.topic_dicription);
        
       // TextView userNameTextView = (TextView) view.findViewById(R.id.user_name);
        
       // TextView userIntroduceTextView = (TextView) view.findViewById(R.id.user_introduce);
       
        topicTitleTextView.setText(mTopicList.get(position).getTopicTitle());
        topicDicriptionTextView.setText(mTopicList.get(position).getTopciDiscription());
      //  userNameTextView.setText(mUserList.get(position).getUserName() + "   ");
      //  commentContentTextView.setText(mQuestionCommentList.get(position).getCommentContent() + "   ");
      
       
        //questionContentView.setText("hello");
        
       	        
        return view;
	}
	
	
	
	

}
