package com.example.testurlactivity;

public class QuestionComment {
	
	int id;
	int question_id;
	int uid;
	String message;
	int time;
	
	
	String userName;
       

	public QuestionComment(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	
	}
	
	public void setUserName(String name)
	{
		this.userName = name;
	}
	
	public void setCommentContent(String content)
	{
		this.message = content;
	}
	
	public String getUserName()
	{
		return this.userName;
	}

	public String getCommentContent()
	{
		return this.message;
	}
}
