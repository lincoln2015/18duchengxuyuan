package com.example.testurlactivity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class NotificationSettingListAdapter extends ArrayAdapter<NotifyActions> {
	
	   private LayoutInflater mInflater;
	   private Context mContext;
	   ArrayList<NotifyActions> mSettingNotifyActionsList;
	   

	public NotificationSettingListAdapter(Context context, int resource, ArrayList<NotifyActions> settingList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mSettingNotifyActionsList = settingList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mSettingNotifyActionsList.size();
	}

	@Override
	public NotifyActions getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		
		return mSettingNotifyActionsList.get(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return super.getView(position, convertView, parent);
		
		

		View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
        
            view = mInflater.inflate(R.layout.account_setting_notification_item, parent, false);
        }

       final CheckBox choseBox = (CheckBox) view.findViewById(R.id.item_checkbox);
       TextView settingItemText = (TextView) view.findViewById(R.id.setting_item_text);
       
        
        View notificationItemLy   =   (View) view.findViewById(R.id.notification_item_ly);
        
        
        settingItemText.setText(getItem(position).getDesc());
        
        choseBox.setChecked(getItem(position).getChoise());
        
        
        choseBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if (choseBox.isChecked())
					mSettingNotifyActionsList.get(position).setChoise(true);
				else
					mSettingNotifyActionsList.get(position).setChoise(false);
			}
		});
        
        
        return view;
	}
	
	

}
