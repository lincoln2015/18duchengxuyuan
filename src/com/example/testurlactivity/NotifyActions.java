package com.example.testurlactivity;

import org.json.JSONException;
import org.json.JSONObject;

public class NotifyActions {
	
String notify_key;	
	
int user_setting;

int combine;

String desc;

boolean choise;



public NotifyActions(JSONObject  jsonObject)
{
	try {
		this.user_setting = jsonObject.getInt("user_setting");
		this.combine = jsonObject.getInt("combine");
		this.desc = jsonObject.getString("desc");
		this.choise = jsonObject.getBoolean("choise");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
 }


public String getDesc()
{
	
	return this.desc;
}


public boolean getChoise()
{
	return this.choise;
}


public void setChoise(boolean choise)
{
	this.choise = choise;
}

public void setNotifyKey(String key)
{
	this.notify_key = key;
}

public String getNotifyKey()
{
	return this.notify_key;
}

}
