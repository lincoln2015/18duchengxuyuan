package com.example.testurlactivity;

import org.json.JSONException;
import org.json.JSONObject;

public class InboxDialog {
	int id;
	
	int sender_uid;
	
	int sender_unread; //发送者未读否
	
	int recipient_uid;
	
	int recipient_unread; //接受者未读否
	
	String add_time;
	
	String update_time;
	
	int sender_count; //发送者显示对话条数
	
	int recipient_count; //接收者显示对话条数
	
	
	InboxMessage dialogLastMessage;
	
	
	
	////////////// extend value
	String userNameTalkTo;
	int unread;
	int count;
	int uid;
	User userTalkTo;

	public InboxDialog() {
		// TODO Auto-generated constructor stub
		
			
		
			
			
		}
	
	public int getUid()
	{
		return this.uid;
	}
	
	public InboxDialog(JSONObject jsonObject) {
		// TODO Auto-generated constructor stub
		try {
			dialogLastMessage = new InboxMessage(jsonObject.getString("last_message"));
			
			this.uid = jsonObject.getInt("uid");
			this.userNameTalkTo = jsonObject.getString("user_name");
			
			this.update_time =  jsonObject.getString("update_time");
			
			if (jsonObject.has("unread"))
				this.unread = Integer.valueOf(jsonObject.getString("unread"));
			else
				this.unread = 0;
			
			if (jsonObject.has("count"))
				this.count =  Integer.valueOf(jsonObject.getString("count"));
			else
				this.count = 0;
			
			if (jsonObject.has("talk_to_user_info"))
				this.userTalkTo =  new User(new JSONObject(jsonObject.getString("talk_to_user_info")));
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
			
			
		}
	
	public User getUserTalkTo()
	{
		return this.userTalkTo;
	}
	
	public int getDialogMessageCount()
	{
		
		if (this.unread != 0)
			return this.unread;
		else
			return this.count;
		
		// return this.count;
	}
	
	
	public String getUserNameTalkTo()
	{
		return this.userNameTalkTo;
	}
	
	public String getDialogLastMessageContent()
	{
		return dialogLastMessage.getMessage();
	}

	
	public String getDialogUpdateTime()
	{
		return this.update_time;
	}
		
	
  
	
}
