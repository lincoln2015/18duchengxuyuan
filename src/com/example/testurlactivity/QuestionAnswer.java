package com.example.testurlactivity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionAnswer implements Serializable {
	
	int answer_id ;  // '回答id'
	int question_id ;// '问题id'
    String answer_content;  // '回答内容',
    String add_time ; // '添加时间',
    int against_count;  // '反对人数',
    int agree_count;  // '支持人数',
    int  uid ; // '发布问题用户ID',
    int comment_count;  // '评论总数',
    int uninterested_count ; // '不感兴趣',
    int thanks_count;   //'感谢数量',
    int category_id ; //'分类id',
    int has_attach;  //'是否存在附件',
    String ip ;
    int force_fold ;// '强制折叠',
    int anonymous  ;
    String publish_source ;
    
    
    // extend val for user temply by anxiang.xiao 20150802 ,should be modify later
    String questionContent;
    String userName;
    
    Question question =null;
    User user =null;
    
    String pulishUserAvatarFile ="";
    
    
    
	public QuestionAnswer() {
		// TODO Auto-generated constructor stub
		
		this.answer_id = 0;
		this.question_id = 0;
		this.answer_content = "";
		this.uid = 0;

	}
	
	
	public Question getQuestion()
	{
		return this.question;
	}
	

	
	public String getUserName()
	{
		if (user != null)
			return this.user.getUserName();
		else
			return "user name null";
	}
	
	
	public String getAddTime()
	{
		return this.add_time;
	}
	
	public int getAnswerId()
	{
		return this.answer_id;
	}
	
	public void setAnswerId(int answerId)
	{
		this.answer_id = answerId;
	}
	
	public int getAgreeCount()
	{
		return this.agree_count;
	}

	public QuestionAnswer(int answer_id) {
		// TODO Auto-generated constructor stub
		this.answer_id = answer_id;
	}
	
	public String  getAnserContent() {
		
		return this.answer_content;
	}
	
	public void  setAnserContent(String answerContent) {
		
		 this.answer_content = answerContent;
	}
	
	public void  setAnserAgreeCount(String answerAgreeCount) {
		
		 this.agree_count = Integer.valueOf(answerAgreeCount);
	}
	
	
	public void  setQuestionContent(String questionContent) {
		
		 this.questionContent = questionContent;
	}
	
	public void  setPulishUserAvatarFile(String avtarFile) {
		
		 this.pulishUserAvatarFile = avtarFile;
	}
	
	public  String getPulishUserAvatarFile()
	{
		
		return this.pulishUserAvatarFile;
	}
	
	
	public void  setAnwerPublishUserName(String userName) {
		
		 this.userName = userName;
	}
	
	
	public String  getQuestionContent() {
		
		if (question != null)
			return this.question.getContent();
		else
			return "content null";
	}
	
	public String  getQuestionContent2() {
		
		return this.questionContent;
	}
	
	
	public User getPulishAnswerUser()
	{
		return this.user;
	}
	

	
	public QuestionAnswer(JSONObject  questionAnswerJsonObject)
	{
			
			try {
				this.answer_id = questionAnswerJsonObject.getInt("answer_id");
				this.question_id = questionAnswerJsonObject.getInt("question_id");
				this.answer_content = questionAnswerJsonObject.getString("answer_content");
				this.uid = questionAnswerJsonObject.getInt("uid");
				
				this.add_time = questionAnswerJsonObject.getString("add_time");
				this.agree_count = questionAnswerJsonObject.getInt("agree_count");
				
				if (questionAnswerJsonObject.has("question_info"))
				{
					String questionInfo = questionAnswerJsonObject.getString("question_info");
					if (questionInfo != "")
						question = new Question(new JSONObject( questionInfo));
				}
				
				if (questionAnswerJsonObject.has("user_info"))
				{
					String userInfo = questionAnswerJsonObject.getString("user_info");
					
					if (userInfo != "")
						user = new User(new JSONObject(userInfo));
				}
				
				
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
				
				this.topic_focus_count = userJsonObject.getInt("topic_focus_count");*/
				
				//this.is_first_login = userJsonObject.getInt("is_first_login");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 

}
