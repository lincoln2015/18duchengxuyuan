package com.example.testurlactivity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {

	
	int uid;// '用户的 UID',
	String user_name;// '用户名',
	String email; // 'EMAIL',
	String mobile; // '用户手机',
	String password;// '用户密码',
	String salt; // '用户附加混淆码',
	String avatar_file;// '头像文件',
	int sex;// '性别',
	int birthday;// '生日',
	String province; // '省',
	String city;// '市',
	int job_id; // '职业ID',
	int reg_time;// '注册时间',
	String reg_ip; // '注册IP',
	String last_login; // '最后登录时间',
	String last_ip;// '最后登录 IP',
	int online_time; // '在线时间',
	String last_active; // '最后活跃时间',
	int notification_unread; // '未读系统通知',
	int inbox_unread; // '未读短信息',
	int inbox_recv; // '0-所有人可以发给我,1-我关注的人',
	int fans_count;// '粉丝数',
	int friend_count;// '观众数',
	int invite_count; // '邀请我回答数量',
	int article_count; // '文章数量',
	int question_count; // '问题数量',
	int answer_count;// '回答数量',
	int topic_focus_count; // COMMENT '关注话题数量',
	int invitation_available; // '邀请数量',
	int group_id;// '用户组',
	int reputation_group;// '威望对应组',
	int forbidden; // '是否禁止用户',
	int valid_email; // '邮箱验证',
	int is_first_login;// '首次登录标记',
	int agree_count; // '赞同数量',
	int thanks_count; // '感谢数量',
	int views_count; // '个人主页查看数量',
	int reputation;// '威望',
	int reputation_update_time;// '威望更新',
	int weibo_visit; // '微博允许访问',
	int integral; // DEFAULT '0',
	int draft_count; // DEFAULT NULL,
	String common_email; // '常用邮箱',
	String url_token; // '个性网址',
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
			 	return "男";
		 else
			 	return "女";
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
