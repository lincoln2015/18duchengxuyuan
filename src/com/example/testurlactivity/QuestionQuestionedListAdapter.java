

package com.example.testurlactivity;

import java.util.ArrayList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class QuestionQuestionedListAdapter extends ArrayAdapter<Question> {
	
	   private LayoutInflater mInflater;
	   private TextView mContactInfoText;
	   private Context mContext;
	   ArrayList<Question> mQuestionList;

	public QuestionQuestionedListAdapter(Context context, int resource, ArrayList<Question> questionList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mQuestionList = questionList;
	}

	@Override
	public void add(Question object) {
		// TODO Auto-generated method stub
		super.add(object);
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return super.getContext();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return mQuestionList.size();
	//	return super.getCount();
	}

	@Override
	public Question getItem(int position) {
		// TODO Auto-generated method stub
	//	return super.getItem(position);
		return mQuestionList.get(position);
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
	        
	            view = mInflater.inflate(R.layout.user_questioned_question_item, parent, false);
	        }

	        TextView questionContentView = (TextView) view.findViewById(R.id.question_content);
	        
	        TextView focusedCountView = (TextView) view.findViewById(R.id.focused_count);
	        
	        TextView answerCountView = (TextView) view.findViewById(R.id.answer_count);
	        
	  
	       
	        questionContentView.setText(mQuestionList.get(position).getContent());
	        answerCountView.setText(Integer.toString(mQuestionList.get(position).getAnswerCount()) + "   ");
	        focusedCountView.setText(Integer.toString(mQuestionList.get(position).getFocusCount()));
	     
	  
	       
	       
	        //questionContentView.setText("hello");
	        
	       	        
	        return view;
	}
	
	

}
