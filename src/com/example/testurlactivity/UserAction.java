package com.example.testurlactivity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAction implements Serializable {
	int history_id;
	int uid;
	int associate_type;
	int associate_action;
	
	int associate_id;
	
	String add_time;
	
	int anonymous;
	
	String associate_content;
	
	String last_action_str;
	
	String title;
	
	User user;
	Question question;
	QuestionAnswer questionAnswer;
	
	
	  public UserAction(JSONObject  actionJsonObject)
	    {
		  add_time = "";
		  last_action_str = "";
		  
		  user = null;
		  question = null;
		  questionAnswer = null;
		  
			try {
			
				this.history_id = actionJsonObject.getInt("history_id");
				this.add_time =actionJsonObject.getString("add_time");
				this.last_action_str = actionJsonObject.getString("last_action_str");
				
				this.user = new User(new JSONObject( actionJsonObject.getString("user_info")));
				
				if(actionJsonObject.has("question_info"))
					this.question = new Question(new JSONObject( actionJsonObject.getString("question_info")));
				
				if(actionJsonObject.has("answer_info"))
					this.questionAnswer = new QuestionAnswer(new JSONObject( actionJsonObject.getString("answer_info")));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	    	
	    }
	  
	  public String getAddTime()
	  {
		  return this.add_time;
	  }
	  
	  public String getLastAction()
	  {
		  return this.last_action_str;
	  }
	  
	  
	  public User getUser()
	  {
		  return this.user;
	  }
	  
	  public Question getQuestion()
	  {
		  return this.question;
	  }
	  
	  public QuestionAnswer getQuestionAnswer()
	  {
		  return this.questionAnswer;
	  }

}
