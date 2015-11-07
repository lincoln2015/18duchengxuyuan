package com.example.testurlactivity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FavorTagListAdapter extends ArrayAdapter<FavorTag> {
	

	  private LayoutInflater mInflater;
	   private TextView mContactInfoText;
	   private Context mContext;
	   ArrayList<FavorTag> mFavorTagList;

	 public FavorTagListAdapter(Context context, int resource, ArrayList<FavorTag> favorTagList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = mContext;
		this.mFavorTagList = favorTagList;
	}
	 
	public void updateListView(ArrayList<FavorTag> favorTagList){
			this.mFavorTagList = favorTagList;
			
			notifyDataSetChanged();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
        
            view = mInflater.inflate(R.layout.favor_list_item, parent, false);
        }

        TextView tagTitleView = (TextView) view.findViewById(R.id.tag_title);
        
        TextView newestAnswerView = (TextView) view.findViewById(R.id.newest_answer);
        
        /*TextView updateTimeView = (TextView) view.findViewById(R.id.update_time);*/
        
        TextView messageCountView = (TextView) view.findViewById(R.id.counts);
        
        View answerLy = (View) view.findViewById(R.id.anwer_ly);
        
        
        
       
      //  categoryTitleView.setText("from  " + mQuestionList.get(position).getCategoryTitle());
        //	nameView.setText(mNotificationList.get(position).getCategoryTitle());
        tagTitleView.setText(mFavorTagList.get(position).getTitle());
        if (!mFavorTagList.get(position).getFavorTagItemList().isEmpty())
        {
			// newestAnswerView.setText(mFavorTagList.get(position).getFavorTagItemList().get(0).getAnserContent());
			String contentStr = mFavorTagList.get(position).getFavorTagItemList().get(0).getAnserContent();
			
			if (contentStr != "")
			{
				RegexUtil2 regeUtil = new RegexUtil2();
				String tmpStr = regeUtil.geTtextOfHtml(contentStr);
				tmpStr = regeUtil.replaceBlank(tmpStr);
				
				if (tmpStr.length() == 0)
					newestAnswerView.setText("[ͼƬ]");
				else
					newestAnswerView.setText(tmpStr);
	
				answerLy.setVisibility(View.VISIBLE);
			}

        }
        /*updateTimeView.setText(mInboxDialogList.get(position).getDialogUpdateTime());*/
       
        if (!mFavorTagList.get(position).getFavorTagItemList().isEmpty())
        {
        	 messageCountView.setText(String.valueOf(mFavorTagList.get(position).getFavorTagItemList().size()));
        }
        else
        {
        	 messageCountView.setText("0");
        	 answerLy.setVisibility(View.GONE);
        }
        return view;
	}
	
	

}
