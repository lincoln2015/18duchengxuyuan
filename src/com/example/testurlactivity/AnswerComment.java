package com.example.testurlactivity;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerComment {
	
	int id;
	int answer_id;
	int uid;
	String message;
	String time;
	
	
	//extend val
	User user;
	QuestionAnswer questionAnswer;
	
	
	// exten val 2
	String userName;
	
	String avatar_file;
	

	public AnswerComment() {
		// TODO Auto-generated constructor stub
	}
	

    public String getUserName()
    {
    	return this.userName;
    }
    
    public String getAvatarFile()
    {
    	return this.avatar_file;
    }
    
    public String getMessage()
    {
    	return this.message;
    }
    
    public String getTime()
    {
    	return this.time;
    }
	
	
	public AnswerComment(JSONObject  answerCommentJsonObject)
	{
		try {
			this.id = answerCommentJsonObject.getInt("id");
			this.message = answerCommentJsonObject.getString("message");
			this.userName = answerCommentJsonObject.getString("user_name");
			this.time = answerCommentJsonObject.getString("time");
			this.avatar_file = answerCommentJsonObject.getString("avatar_file");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	 }

}
