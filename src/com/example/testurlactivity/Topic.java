package com.example.testurlactivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Topic {

	public Topic() {
		// TODO Auto-generated constructor stub
	}
	
		int topic_id ; //'话题id',
     String  topic_title ; // '话题标题',
      String add_time;// int(10) //'添加时间',
      int  discuss_count ;//int(11) // '讨论计数',
      String topic_description ;//text // '话题描述',
      String topic_pic ;//varchar(255) //'话题图片',
      int  topic_lock;// tinyint(2) // '话题是否锁定 1 锁定 0 未锁定',
      int  focus_count; //int(11) // '关注计数',
      int  user_related ;//tinyint(1) // '是否被用户关联',
      String url_token;// varchar(32)//
      int  merged_id ;//int(11) //
      String seo_title;// varchar(255) //
      int parent_id ;//int(10) //
      int  is_parent;// tinyint(1) //
      int  discuss_count_last_week;// int(10) //
      int discuss_count_last_month ;//int(10)//
      int  discuss_count_update;// int(10) //
      
      
      public Topic(JSONObject  topicJsonObject)
 	 {
 			
 			try {
 				this.topic_id =topicJsonObject.getInt("topic_id");
 				this.topic_title = topicJsonObject.getString("topic_title");
 				this.topic_description = topicJsonObject.getString("topic_description");
 				this.discuss_count = topicJsonObject.getInt("discuss_count");
 				this.focus_count = topicJsonObject.getInt("focus_count");
 				
 				/*this.uid = userJsonObject.getInt("uid");
 				this.user_name = userJsonObject.getString("user_name");
 				//this.password = userJsonObject.getString("password");
 				//this.notification_unread =	Integer.valueOf(userJsonObject.getString("notification_unread"));
 				// this.inbox_unread =  userJsonObject.getInt("inbox_unread");
 				// this.fans_count = userJsonObject.getInt("fans_count");
 				this.fans_count = Integer.valueOf(userJsonObject.getString("fans_count"));
 				this.friend_count = userJsonObject.getInt("friend_count");
 				//this.invite_count = userJsonObject.getInt("invite_count");
 				this.question_count = userJsonObject.getInt("question_count");
 				this.answer_count = userJsonObject.getInt("answer_count");
 				this.agree_count = userJsonObject.getInt("agree_count");
 				this.thanks_count = userJsonObject.getInt("thanks_count");
 				//this.invitation_available = userJsonObject.getInt("invitation_available");
 				//this.email = userJsonObject.getString("email");
 				
 				this.topic_focus_count = userJsonObject.getInt("topic_focus_count");
 				*/
 				//this.is_first_login = userJsonObject.getInt("is_first_login");
 				
 			} catch (JSONException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	 }
 	 
    
      public int getTopicID()
      {
    	  return this.topic_id;
      }
      
      public String getTopicTitle()
      {
    	  return this.topic_title;
      }
      
      public String getTopciDiscription()
      {
    	  return this.topic_description;
      }

      public int getTopicDisscussCount()
      {
    	  return this.discuss_count;
      }
      
      public int getTopcicFocusCount()
      {
    	  return this.focus_count;
      }
}
