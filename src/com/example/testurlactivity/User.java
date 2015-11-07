package com.example.testurlactivity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {

	
	int uid;// '�û��� UID',
	String user_name;// '�û���',
	String email; // 'EMAIL',
	String mobile; // '�û��ֻ�',
	String password;// '�û�����',
	String salt; // '�û����ӻ�����',
	String avatar_file;// 'ͷ���ļ�',
	int sex;// '�Ա�',
	int birthday;// '����',
	String province; // 'ʡ',
	String city;// '��',
	int job_id; // 'ְҵID',
	int reg_time;// 'ע��ʱ��',
	String reg_ip; // 'ע��IP',
	String last_login; // '����¼ʱ��',
	String last_ip;// '����¼ IP',
	int online_time; // '����ʱ��',
	String last_active; // '����Ծʱ��',
	int notification_unread; // 'δ��ϵͳ֪ͨ',
	int inbox_unread; // 'δ������Ϣ',
	int inbox_recv; // '0-�����˿��Է�����,1-�ҹ�ע����',
	int fans_count;// '��˿��',
	int friend_count;// '������',
	int invite_count; // '�����һش�����',
	int article_count; // '��������',
	int question_count; // '��������',
	int answer_count;// '�ش�����',
	int topic_focus_count; // COMMENT '��ע��������',
	int invitation_available; // '��������',
	int group_id;// '�û���',
	int reputation_group;// '������Ӧ��',
	int forbidden; // '�Ƿ��ֹ�û�',
	int valid_email; // '������֤',
	int is_first_login;// '�״ε�¼���',
	int agree_count; // '��ͬ����',
	int thanks_count; // '��л����',
	int views_count; // '������ҳ�鿴����',
	int reputation;// '����',
	int reputation_update_time;// '��������',
	int weibo_visit; // '΢���������',
	int integral; // DEFAULT '0',
	int draft_count; // DEFAULT NULL,
	String common_email; // '��������',
	String url_token; // '������ַ',
	int url_token_update; // DEFAULT '0',
	String verified;// DEFAULT NULL,
	String default_timezone;// DEFAULT NULL,
	String email_settings;// DEFAULT '',
	String weixin_settings; // DEFAULT '',
	String recent_topics;
	
	
	boolean FOLLOW_ME;
	boolean QUESTION_INVITE;
	boolean NEW_ANSWER ;
	boolean NEW_MESSAGE ;
	boolean QUESTION_MOD ;
	
	
	//
	String signature;
	
	
	public User()
	{
		
		
	}
	

	 public User(JSONObject  userJsonObject)
	 {
			
			try {
				
				this.FOLLOW_ME =false;
				this.QUESTION_INVITE =false;
				this.NEW_ANSWER =false;
				this.NEW_MESSAGE =false;
				this.QUESTION_MOD =false;
				
				
				this.signature = "";
				this.avatar_file = "";
				
				
				this.uid = userJsonObject.getInt("uid");
				this.user_name = userJsonObject.getString("user_name");
				this.sex = userJsonObject.getInt("sex");
				if (userJsonObject.has("email"))
					this.email = userJsonObject.getString("email");
				//this.password = userJsonObject.getString("password");
				//this.notification_unread =	Integer.valueOf(userJsonObject.getString("notification_unread"));
				// this.inbox_unread =  userJsonObject.getInt("inbox_unread");
				// this.fans_count = userJsonObject.getInt("fans_count");
				this.fans_count = Integer.valueOf(userJsonObject.getString("fans_count"));
				this.friend_count = userJsonObject.getInt("friend_count");
				this.invite_count = userJsonObject.getInt("invite_count");
				this.question_count = userJsonObject.getInt("question_count");
				this.answer_count = userJsonObject.getInt("answer_count");
				this.agree_count = userJsonObject.getInt("agree_count");
				this.thanks_count = userJsonObject.getInt("thanks_count");
				//this.invitation_available = userJsonObject.getInt("invitation_available");
				//this.email = userJsonObject.getString("email");
				
				this.topic_focus_count = userJsonObject.getInt("topic_focus_count");
				this.reputation =  userJsonObject.getInt("reputation");
				
				//this.is_first_login = userJsonObject.getInt("is_first_login");
				
				
				if (userJsonObject.has("email_settings"))
				{
					String emailSettings = userJsonObject.getString("email_settings");
					
					if (emailSettings.contains("FOLLOW_ME"))
						this.FOLLOW_ME = true;	
					
					if (emailSettings.contains("QUESTION_INVITE"))
						this.QUESTION_INVITE = true;	
					
					if (emailSettings.contains("NEW_ANSWER"))
						this.NEW_ANSWER = true;	
					
					if (emailSettings.contains("NEW_MESSAGE"))
						this.NEW_MESSAGE = true;	
					
					if (emailSettings.contains("QUESTION_MOD"))
						this.QUESTION_MOD = true;	
			
				}
				
				if (userJsonObject.has("signature"))
					this.signature = userJsonObject.getString("signature");
				
				if (userJsonObject.has("avatar_file"))
					this.avatar_file = userJsonObject.getString("avatar_file");
				
									
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 public String getAvatarFile()
	 {
		 if (this.avatar_file != "")
			 return this.avatar_file;
		 else
			 return "";
	 }
	 
	 public String getSignature()
	 {
		 return this.signature;
	 }
	 

	 
	public  boolean getFollowMeChoise()
	{
		return this.FOLLOW_ME;
	}
	
	public void setFollowMeChoise(boolean choise)
	{
		this.FOLLOW_ME = choise;
	}
	
	public  boolean getQuestionInviteChoise()
	{
		return this.QUESTION_INVITE;
	}
	
	public void setQuestionInviteChoise(boolean choise)
	{
		this.QUESTION_INVITE = choise;
	}
	
	public  boolean getNewAnswerChoise()
	{
		return this.NEW_ANSWER;
	}
	
	public void setNewAnswerChoise(boolean choise)
	{
		this.NEW_ANSWER = choise;
	}
	
	 
	public  boolean getNewMessageChoise()
	{
		return this.NEW_MESSAGE;
	}
	
	public void setNewMessageChoise(boolean choise)
	{
		this.NEW_MESSAGE = choise;
	}
	
	 
	public  boolean getQuestionModChoise()
	{
		return this.QUESTION_MOD;
	}
	
	public void setQuestionModChoise(boolean choise)
	{
		this.QUESTION_MOD = choise;
	}

	 
	 public int getUserId()
	 {
		 
		 return this.uid;
	 }
	 
	 public String getUserName()
	 {
		 return this.user_name;
	 }
	 
	 public String getSex()
	 {
		 if (sex == 1)
			 	return "��";
		 else
			 	return "Ů";
	 }
	 
	 public String getEmail()
	 {
		 return this.email;
	 }
	 
	 public int getInviteCount()
	 {
		 return this.invite_count;
	 }
	 
	 public int getReputation()
	 {
		 return this.reputation;
	 }
	 
	 
	 public int getQuestionCount()
	 {
		 return this.question_count;
	 }
	 
	 public int getAnswerCount()
	 {
		 return this.answer_count;
	 }
	 
	 public int getTopicCount()
	 {
		 return this.topic_focus_count;
	 }
	 
	 public int getFansCount()
	 {
		 return this.fans_count;
	 }
	 
	 public int getFriendCount()
	 {
		 return this.friend_count;
	 }
	 
	 public int getAgreeCount()
	 {
		 return this.agree_count;
	 }
	 
	 public int getThanksCount()
	 {
		 return this.thanks_count;
	 }
	 

	 
	 public void setQuestionCount(int count)
	 {
		 this.question_count = count;
	 }
	 
	 public void setAnswerCount(int count)
	 {
		 this.answer_count = count;
	 }
	 
	 

}
