package com.example.testurlactivity;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionCategory {
	
	int id;
	String title;
	String type;
	int parent_id;
	int sort;
	String url_token;

	public QuestionCategory() {
		// TODO Auto-generated constructor stub
		
		
	}
	
	
	public String getCategoryTitel()
	{
		
		return this.title;
	}

	public int getCategoryId()
	{
		
		return this.id;
	}



	 public QuestionCategory(JSONObject  jsonObject)
	 {
		 try {
	
				this.id = jsonObject.getInt("id");
				this.title = jsonObject.getString("title");
				this.type = jsonObject.getString("type");
						
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
}
