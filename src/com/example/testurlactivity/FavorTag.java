package com.example.testurlactivity;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;

public class FavorTag  implements Serializable {
	int id;
	int uid;
	String title;
	int item_id;
	String type;
	String discription;

	
	
	//extend val
	String user_name;
	int count;
	ArrayList<QuestionAnswer> questionAnswerList;
	
	
	int flag_will_add_old;
	int flag_will_add_new;

	

	public FavorTag() {
		// TODO Auto-generated constructor stub
	}
	

	
	public FavorTag(String title) {
		// TODO Auto-generated constructor stub
		this.title = title;
	}
	
	public boolean isFavorAddToChange()
	{
		if (flag_will_add_old == flag_will_add_new)
			return false;
		else
			return true;
	}
	
	public String getAction()
	{
		if (flag_will_add_new == 1)
			return "add";
		else
			return "remove";
			
	}
	
	
	
	
	public FavorTag(JSONObject jsonObject) {
		// TODO Auto-generated constructor stub
		
		questionAnswerList = new ArrayList<QuestionAnswer>();
		
		try {
			this.title = jsonObject.getString("title");
			this.count =  Integer.valueOf(jsonObject.getString("favor_item_count"));
			this.flag_will_add_old = Integer.valueOf(jsonObject.getString("is_aready_add"));
			this.flag_will_add_new  =this.flag_will_add_old;
			
			if (jsonObject.has("favor_item"))
			{
				
				JSONArray subjson = new JSONObject(jsonObject.toString()).getJSONArray("favor_item"); 
	            
		          
	              
                for(int i=0;i<subjson.length();i++) 
                { 
                    JSONObject subSubjsonObject = (JSONObject)subjson.opt(i); 
                    
                   // QuestionAnswer questionAnser = new QuestionAnswer(subSubjsonObject);
                    
                    QuestionAnswer questionAnser = new QuestionAnswer();
                    
                    // should be optimise ,user json object directly instead of set val lonely by anxiang.xiao 20150907
                    if (subSubjsonObject.has("answer_info") && !subSubjsonObject.isNull("answer_info"))
                    {
                    	questionAnser.setAnserContent((new JSONObject(subSubjsonObject.getString("answer_info"))).getString("answer_content"));
                    	questionAnser.setAnswerId((new JSONObject(subSubjsonObject.getString("answer_info"))).getInt("answer_id"));

                    }
                    if (subSubjsonObject.has("question_info") && !subSubjsonObject.isNull("question_info"))
                    {
                    	questionAnser.setQuestionContent((new JSONObject(subSubjsonObject.getString("question_info"))).getString("question_content"));
                    }
                    if (subSubjsonObject.has("user_info") && !subSubjsonObject.isNull("user_info"))
                    {	
                    	questionAnser.setPulishUserAvatarFile((new JSONObject(subSubjsonObject.getString("user_info"))).getString("avatar_file"));
                    }
                   
                    /*if (subSubjsonObject.has("answer_info"))
                    {
                    	questionAnser.setAnserContent((new JSONObject(subSubjsonObject.getString("answer_info"))).getString("answer_content"));
                    	
                    	questionAnser.setAnserAgreeCount((new JSONObject(subSubjsonObject.getString("answer_info"))).getString("agree_count"));
                    }
                    if (subSubjsonObject.has("question_info"))
                    {
                    	questionAnser.setQuestionContent((new JSONObject(subSubjsonObject.getString("question_info"))).getString("question_content"));
                    }
                    if  (subSubjsonObject.has("article_info")) 
                    {
                    	JSONObject tempjson = new JSONObject(subSubjsonObject.getString("article_info"));
                    	
                    	if (tempjson.has("title"))
                    		questionAnser.setQuestionContent(tempjson.getString("title"));
                    	if (tempjson.has("last_action_str"))
                    		questionAnser.setAnserContent(tempjson.getString("last_action_str"));
                    }
                    */
                  
                    questionAnswerList.add(questionAnser);
                } 
	              
               
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void set_aready_add_flag(int flag)
	{
		this.flag_will_add_new =flag;
	}
	
	public int get_aready_add_flag()
	{
		return this.flag_will_add_old;
	}
	
	public void updateFlagState()
	{
		this.flag_will_add_old = this.flag_will_add_new;
	}
	

	public String getTitle()
	{
		return this.title;
	}
	
	public  void setUserName(String name)
	{
		this.user_name = name;
	}
	
	public String getUserName()
	{
		return this.user_name;
	}
	
	public int getCount()
	{
		return this.count;
	}
	
	public ArrayList<QuestionAnswer> getFavorTagItemList()
	{
		return questionAnswerList;
	}
	
}
