package com.example.testurlactivity;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("NewApi")
public class Notification {

	public Notification() {
		// TODO Auto-generated constructor stub
	}

    int notification_id;// int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    int sender_uid;// int(11) DEFAULT NULL COMMENT '发送者ID',
    int recipient_uid;// int(11) DEFAULT '0' COMMENT '接收者ID',
    int action_type;// int(4) DEFAULT NULL COMMENT '操作类型',
    int model_type;// smallint(11) NOT NULL DEFAULT '0',
    String source_id;// varchar(16) NOT NULL DEFAULT '0' COMMENT '关联 ID',
    String add_time;// int(10) DEFAULT NULL COMMENT '添加时间',
    int read_flag;// tinyint(1) DEFAULT '0' COMMENT '阅读状态',
  
   ////  externd value 



   int extend_count;
   // "extend_details":{"102":{"count":2,"users":{"3":{"username":"lincoln","url":"question\/18?rf=false?ification_id=139&item_id=55#!answer_55"},"1":{"username":"admin","url":"question\/18?rf=false?ification_id=135&item_id=54#!answer_54"}}}},
 
   String messageStr;
   

   
   //exten val 2
   String p_user_name;
   int p_user_id;
   
  int questionId;
  String questionContent;
  
  int answerId;
  String answerContent;
   

   // exten val //users
   
   User mUser;
   Question mQuestion ;
   

   
   public Notification(JSONObject  notificationJsonObject)
   {

		Iterator it;

		add_time = "";// int(10) DEFAULT NULL COMMENT '添加时间',
		messageStr = "";

		p_user_name = "";
		p_user_id = 0;

		questionId = 0;
		questionContent = "";

		answerId = 0;
		answerContent = "";
		
		mUser=null;
		mQuestion=null;
		
		String tempUserStr;
	    
		try {

			if (notificationJsonObject.has("message"))
			{
				this.messageStr= notificationJsonObject.getString("message");
				
			}
			
			if (notificationJsonObject.has("add_time"))
			{
				this.add_time = notificationJsonObject.getString("add_time");
				
			}
			
		
			if (notificationJsonObject.has("p_user_name"))
			{
				this.p_user_name = notificationJsonObject.getString("p_user_name");
				this.p_user_id = Integer.valueOf(notificationJsonObject.getString("p_user_id"));
			}
			
			if (notificationJsonObject.has("question_id"))
			{
				this.questionId= Integer.valueOf(notificationJsonObject.getString("question_id"));
				this.questionContent= notificationJsonObject.getString("question_content");
			}
			
			if (notificationJsonObject.has("answer_id"))
			{
				this.answerId = Integer.valueOf(notificationJsonObject.getString("answer_id"));
				this.answerContent= notificationJsonObject.getString("answer_content");
			}
			
			if (notificationJsonObject.has("p_user_info"))
			{
				this.mUser = new User(new JSONObject(notificationJsonObject.getString("p_user_info")));
				
			
			}
			
			
			if (notificationJsonObject.has("question_info"))
			{
				this.mQuestion = new Question(new JSONObject(notificationJsonObject.getString("question_info")));
			
			}
			
			
		
	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
   	
   }
   
   public User getUser()
   {
	   return this.mUser;
   }
   
   public Question getQuestion()
   {
	   return this.mQuestion;
   }
 
   
   public String getUserName()
   {
	   return this.p_user_name;
   }
   
   public int getUserId()
   {
	   return this.p_user_id;
   }
   
   public String getMessage()
   {
	   return this.messageStr;
   }
   
   public String getAddTime()
   {
	   return this.add_time;
   }
  
   
   public String getQuestionContent()
   {
	   return this.questionContent;
   }
   
   public int getQuestionId()
   {
	   return this.questionId;
   }
   
   
   
   public String getAnswerContent()
   {
	   return this.answerContent;
   }
   
   public int getAnswerId()
   {
	   return this.answerId;
   }
   
   
 
}


