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

public class QuestionAnswerListAdapter extends ArrayAdapter<QuestionAnswer> {

	private LayoutInflater mInflater;
	private TextView mContactInfoText;
	private Context mContext;
	ArrayList<QuestionAnswer> mQuestionAnswerList;
	
	 ArrayList<Bitmap> bitMapList;

	public QuestionAnswerListAdapter(Context context, int resource,
			ArrayList<QuestionAnswer> questionAnswerList) {
		super(context, resource);
		// TODO Auto-generated constructor stub

		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mQuestionAnswerList = questionAnswerList;
		
		this.bitMapList = null;
	}
	
	public void updateListView(ArrayList<QuestionAnswer> list,  ArrayList<Bitmap> bitmapList){
		this.mQuestionAnswerList = list;
		
		this.bitMapList = bitmapList;
		
		notifyDataSetChanged();
}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mQuestionAnswerList.size();
	}

	@Override
	public QuestionAnswer getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		
		return mQuestionAnswerList.get(position);
	}

	@Override
	public int getPosition(QuestionAnswer item) {
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
        
            view = mInflater.inflate(R.layout.question_answer_list_item, parent, false);
        }

       // TextView userNameTextView = (TextView) view.findViewById(R.id.user_name);
        
        TextView questionAnswerTextView = (TextView) view.findViewById(R.id.question_answer);
        
        TextView userNameTextView = (TextView) view.findViewById(R.id.user_name);
        
    	ImageView imgeView = (ImageView) view.findViewById(R.id.user_img);
       
        userNameTextView.setText(mQuestionAnswerList.get(position).getUserName());
        
        
       
        
        RegexUtil2 regeUtil = new RegexUtil2();
        
        String tmpStr =  regeUtil.geTtextOfHtml(mQuestionAnswerList.get(position).getAnserContent());
        
        tmpStr = regeUtil.replaceBlank(tmpStr);
        
        if (tmpStr.length() == 0)
        	questionAnswerTextView.setText("[ͼƬ]");
        else
        	questionAnswerTextView.setText(tmpStr);
        
 
        
        
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
       
        //questionContentView.setText("hello");
        
       	        
        return view;
		   
	}
	
	

}
