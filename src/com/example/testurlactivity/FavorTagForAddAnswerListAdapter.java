package com.example.testurlactivity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class FavorTagForAddAnswerListAdapter extends ArrayAdapter<FavorTag> {
	

	  private LayoutInflater mInflater;
	   private TextView mContactInfoText;
	   private Context mContext;
	   ArrayList<FavorTag> mFavorTagList;



	 public FavorTagForAddAnswerListAdapter(Context context, int resource, ArrayList<FavorTag> favorTagList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mFavorTagList = favorTagList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mFavorTagList.size();
	}

	@Override
	public FavorTag getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		
		return mFavorTagList.get(position);
	}

	@Override
	public int getPosition(FavorTag item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public View getView(final  int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
        
            view = mInflater.inflate(R.layout.add_answer_to_favor_tag_lsit_item, parent, false);
        }

        TextView tagTitleView = (TextView) view.findViewById(R.id.tag_title);
        
        // TextView userNametView = (TextView) view.findViewById(R.id.tag_dicription);
        
        TextView messageCountView = (TextView) view.findViewById(R.id.count);
        
        final  CheckBox choseBox = (CheckBox) view.findViewById(R.id.chose);
      
      
        choseBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if (choseBox.isChecked())
					mFavorTagList.get(position).set_aready_add_flag(1);
				else
					mFavorTagList.get(position).set_aready_add_flag(0);
			}
		});
        
      
        
		// categoryTitleView.setText("from  " +
		// mQuestionList.get(position).getCategoryTitle());
		// nameView.setText(mNotificationList.get(position).getCategoryTitle());
		tagTitleView.setText(mFavorTagList.get(position).getTitle());
		// userNametView.setText(mFavorTagList.get(position).getUserName());
		messageCountView.setText(String.valueOf(mFavorTagList.get(position).getCount()) + " ¸öÊÕ²Ø");

		boolean flag;
		if (mFavorTagList.get(position).get_aready_add_flag() == 1)
			flag = true;
		else
			flag = false;

		choseBox.setChecked(flag);
        
        return view;
	}
	
	

}
