package com.example.testurlactivity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class Question implements Serializable {
	
	
	 int question_id ;
	 String question_content; // char[] question_content;
    String question_detail = "";
    int add_time  ;
    int update_time; 
    
    int  published_uid ; 
    String user_name;
    
    String user_avatar_file;
    
  //  Bitmap user_avatar_bitmap;
    
    
    int answer_count ;
    int answer_users;
    int view_count;
    int focus_count;
    int comment_count;
    int  action_history_id;
    
    int category_id ;
    String categoryTitle;
    String categoryType;
    
    int agree_count ;
    int against_count;
    int best_answer ;
    int has_attach;
    String unverified_modify;
    int unverified_modify_count;
    int  ip ;
    int last_answer;
    double popular_value;
    int popular_value_update ;
    int lock ;
    int  anonymous ;
    int thanks_count ;
    String question_content_fulltext;
    int  is_recommend ;
    int  weibo_msg_id ;
    int received_email_id ;
    int chapter_id ;
    int  sort ;
    
    
    String newestAnswer;
    int newestAnswerID;
    
    
    // 
    User publishUser;
    

	public Question() {
		// TODO Auto-generated constructor stub
		
	}
	
    
    public Question(JSONObject  qestionInfoJsonObject)
    {
		try {
			
			this.user_avatar_file = "";
			
			if (qestionInfoJsonObject.has("question_info")) {
				JSONObject actionQuestionInfoJsonObject = new JSONObject(qestionInfoJsonObject.getString("question_info"));
				
				answer_count = actionQuestionInfoJsonObject.getInt("answer_count");
				focus_count = actionQuestionInfoJsonObject.getInt("focus_count");
				thanks_count = actionQuestionInfoJsonObject.getInt("thanks_count");
				question_content = actionQuestionInfoJsonObject.getString("question_content");
				if (actionQuestionInfoJsonObject.has("question_detail"))
					 question_detail = actionQuestionInfoJsonObject.getString("question_detail");
				question_id = actionQuestionInfoJsonObject.getInt("question_id");
				comment_count = actionQuestionInfoJsonObject.getInt("comment_count");
			}
			else
			{
				// this.user_avatar_bitmap = null;
				this.question_id = qestionInfoJsonObject.getInt("question_id");
				this.question_content = qestionInfoJsonObject.getString("question_content");
				if (qestionInfoJsonObject.has("question_detail"))
					this.question_detail = qestionInfoJsonObject.getString("question_detail");
				this.answer_count = qestionInfoJsonObject.getInt("answer_count");
				this.comment_count = qestionInfoJsonObject.getInt("comment_count");
				this.focus_count = qestionInfoJsonObject.getInt("focus_count");
				this.thanks_count = qestionInfoJsonObject.getInt("thanks_count");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// strs += jsonObject.getString("question_content") +"@@@";
    	
    }

	public Question(int questionId) {
		// TODO Auto-generated constructor stub
		this.question_id = questionId;
	}
	
	public int getQuestionId()
	{
		return this.question_id;
	}
	
	public void  setQuestionContent(String content) {
		// TODO Auto-generated constructor stub
		this.question_content = content;
	}
	
	
	public void  setFocusCount(int count) {
		// TODO Auto-generated constructor stub
		this.focus_count = count;
	}
	
	
	
	public void  setCategoryType(String type) {
		this.categoryType = type;
	}
	
	public void  setCategoryTitle(String title) {
		// TODO Auto-generated constructor stub
		this.categoryTitle = title;
	}
	
	
	public void  setQuestionDetail(String detail) {
		// TODO Auto-generated constructor stub
		this.question_detail = detail;
	}
	
	public void setNewestAnswer(String newestAnswer)
	{
		 this.newestAnswer =newestAnswer;
	}
	
	public void setNewestAnswerID(int newestAnswerID)
	{
		 this.newestAnswerID =newestAnswerID;
	}
	public int getNewestAnswerID()
	{
		return  this.newestAnswerID;
	}
	
	
	
	public void  setUserName(String name) {
		// TODO Auto-generated constructor stub
		this.user_name = name;
	}
	
/*	
	public void  setUserAvatarBitmap(Bitmap user_bitmap) {
		// TODO Auto-generated constructor stub
		this.user_avatar_bitmap = user_bitmap;
	}
	*/
	
	public void  setUserAvatarFile(String avatarFile) {
		// TODO Auto-generated constructor stub
		this.user_avatar_file = avatarFile;
	}
	
	
	public void  setAnswerCount(int count) {
	
		this.answer_count =count;
	}


	public String  getContent() {
	
		return this.question_content;
	}
	
	public String  getCategoryType() {
	
		return this.categoryType;
	}
	
	public String  getCategoryTitle() {
		// TODO Auto-generated constructor stub
		return this.categoryTitle;
	}

	
	public int  getAnswerCount() {
		// TODO Auto-generated constructor stub
		return this.answer_count;
	}
	
	public int  getThanksCount() {
		// TODO Auto-generated constructor stub
		return this.thanks_count;
	}
	
	
	public String  getQuestionDetail() {
		// TODO Auto-generated constructor stub
		return this.question_detail;
	}
	
	public String  getUserName() {
		// TODO Auto-generated constructor stub
		return this.user_name;
	}
	
	public String  getAvatarFile() {
		// TODO Auto-generated constructor stub
		return this.user_avatar_file;
	}
	
/*	public Bitmap  getUserAvatarBitmap() {
		// TODO Auto-generated constructor stub
		return this.user_avatar_bitmap;
	}
	*/
	
	
	public String getNewestAnswer()
	{
		return this.newestAnswer;
	}
	
	
	public int getCommentCount()
	{
		return this.comment_count;
	}
	public void setCommentCount(int count)
	{
		 this.comment_count = count;
	}
	
	
	public int getFocusCount()
	{
		return this.focus_count;
	}
	
	public User getPublishUser()
	{
		return this.publishUser;
	}
	
	public void setPublishUser(User user)
	{
		this.publishUser = user;
	}
	
	public int getCategoryId()
	{
		return this.category_id;
	}
	
	public void setCategoryId(int id)
	{
		this.category_id = id;
	}

}