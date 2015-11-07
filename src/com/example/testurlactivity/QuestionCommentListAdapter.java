package com.example.testurlactivity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class QuestionCommentListAdapter extends ArrayAdapter<QuestionComment> {
	
	private LayoutInflater mInflater;
	private TextView mContactInfoText;
	private Context mContext;
	ArrayList<QuestionComment> mQuestionCommentList;

	public QuestionCommentListAdapter(Context context, int resource,
			ArrayList<QuestionComment> commentList) {
		super(context, resource);
		// TODO Auto-generated constructor stub

		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mQuestionCommentList = commentList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mQuestionCommentList.size();
	}

	@Override
	public QuestionComment getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		return mQuestionCommentList.get(position);
	}

	@Override
	public int getPosition(QuestionComment item) {
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
        
            view = mInflater.inflate(R.layout.question_comment_item, parent, false);
        }

       // TextView userNameTextView = (TextView) view.findViewById(R.id.user_name);
        
        TextView userNameTextView = (TextView) view.findViewById(R.id.user_name);
        
        TextView commentContentTextView = (TextView) view.findViewById(R.id.comment_content);
       
       // userNameTextView.setText(mQuestionAnswerList.get(position).getContent());
        userNameTextView.setText(mQuestionCommentList.get(position).getUserName() + "   ");
        commentContentTextView.setText(mQuestionCommentList.get(position).getCommentContent() + "   ");
      
       
        //questionContentView.setText("hello");
        
       	        
        return view;
	}
	
	

}
