package com.example.testurlactivity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoucusQuestionListAdapter extends ArrayAdapter<Question> {
	
	   private LayoutInflater mInflater;
	   private TextView mContactInfoText;
	   private Context mContext;
	   ArrayList<Question> mQuestionList;

	   public FoucusQuestionListAdapter(Context context, int resource, ArrayList<Question> questionList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mQuestionList = questionList;
	}
	   
	public void updateListView(ArrayList<Question> list){
			this.mQuestionList = list;
			notifyDataSetChanged();
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		return mQuestionList.size();
	}

	@Override
	public Question getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		
		return mQuestionList.get(position);
	}

	@Override
	public int getPosition(Question item) {
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
        
            view = mInflater.inflate(R.layout.user_focus_questions_list_item, parent, false);
        }

        TextView questionContentView = (TextView) view.findViewById(R.id.question_content);
        
        TextView focusCountView = (TextView) view.findViewById(R.id.focus_count);
        
        TextView answerCountView = (TextView) view.findViewById(R.id.answer_count);
        
       
   
   
      //  categoryTitleView.setText("from  " + mQuestionList.get(position).getCategoryTitle());
   
        questionContentView.setText(mQuestionList.get(position).getContent());
        answerCountView.setText("   " + Integer.toString(mQuestionList.get(position).getAnswerCount()));
        focusCountView.setText(String.valueOf(mQuestionList.get(position).getFocusCount()));
  
       
       
        //questionContentView.setText("hello");
        
       	        
        return view;
	}
	
	

}
