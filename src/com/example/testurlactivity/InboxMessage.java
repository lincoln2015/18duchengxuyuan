package com.example.testurlactivity;

import org.json.JSONException;
import org.json.JSONObject;

public class InboxMessage {
	
	int id;
	int uid; //������id
	
	int dialog_id; //��Ϣ�����Ի�id
	
	String message; //��Ϣ����
	
	String add_time; //��Ϣ����ʱ��
	
	int sender_remove;
	
	int recipient_remove;
	
	int receipt;
	
	int isSelfMessage;
	
	
	//extern 
	
	String user_avatar_file;

	public InboxMessage(String msg) {
		this.message = msg;
		// TODO Auto-generated constructor stub
	}
	
	public InboxMessage() {
		
	}
	
	public void setIsSelfMessage(int is)
	{
		this.isSelfMessage = is;
	}
	
	public int getIsSelfMessage()
	{
		return this.isSelfMessage;
	}
	
	public InboxMessage(JSONObject json) {
		
		try {
			this.message = json.getString("message");
			this.add_time = json.getString("add_time");
			
			this.user_avatar_file = json.getString("avatar_file");
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getAvatarFile()
	{
		return this.user_avatar_file;
	}
	
	public String getAddTime()
	{
		return this.add_time;
	}
	
	public String getMessage()
	{
		return this.message;
	}

}
