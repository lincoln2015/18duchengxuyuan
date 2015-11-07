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

public class FavorTagItemShowListAdapter extends ArrayAdapter<QuestionAnswer> {
	

	  private LayoutInflater mInflater;
	   private TextView mContactInfoText;
	   private Context mContext;
	   ArrayList<QuestionAnswer> mQuestionAnswerList;
		ArrayList<Bitmap> bitMapList;

	public FavorTagItemShowListAdapter(Context context, int resource,
			ArrayList<QuestionAnswer> questionAnswerList) {
		super(context, resource);
		// TODO Auto-generated constructor stub

		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mQuestionAnswerList = questionAnswerList;
		
		this.bitMapList = null;
	}

	public void updateListView(ArrayList<QuestionAnswer> list,	ArrayList<Bitmap> bitmapList) {
		
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
        
            view = mInflater.inflate(R.layout.fava_item_show_list_item, parent, false);
        }

        TextView questionContentView = (TextView) view.findViewById(R.id.question_content);
        
        TextView answerContentView = (TextView) view.findViewById(R.id.question_last_answer);
        
        /*TextView updateTimeView = (TextView) view.findViewById(R.id.update_time);*/
        
       TextView messageCountView = (TextView) view.findViewById(R.id.agree_count);
        
       
      //  categoryTitleView.setText("from  " + mQuestionList.get(position).getCategoryTitle());
        //	nameView.setText(mNotificationList.get(position).getCategoryTitle());
        questionContentView.setText(mQuestionAnswerList.get(position).getQuestionContent2());
        messageCountView.setText(String.valueOf(mQuestionAnswerList.get(position).getAgreeCount()));
        
        String answerContent =  mQuestionAnswerList.get(position).getAnserContent();
        RegexUtil2 regeUtil = new RegexUtil2();
        String tmpStr =  regeUtil.geTtextOfHtml(answerContent);
        tmpStr = regeUtil.replaceBlank(tmpStr);
         if (tmpStr.length() == 0)
        	 answerContentView.setText("[ͼƬ]");
         else
        	 answerContentView.setText(tmpStr);
        
        ImageView userImg = (ImageView) view.findViewById(R.id.answer_user_img);
        
        
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
