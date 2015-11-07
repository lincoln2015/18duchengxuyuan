package com.example.testurlactivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	// public static String patt = "\\[em_[0-9]*\\]"; 
	// public static String patt = "\\[attach\\](.*?)\\[/attach\\]"; 
	// public static String patt = "<a.*href=\".*\">(.+?)</a>"; 
	// public static String patt = "\\[attach\\](.*?)\\[/attach\\]"; 
	public static String patt ; 
	
    private static String newStr ;
    
    
    public RegexUtil()
    {
    	this.patt = "\\[attach\\](.+?)\\[/attach\\]"; 
    	this.newStr = null;
    }
    
    public static String geTtextOfHtml(String str , ArrayList<String> picUrlList)
    {
    	/*Pattern pattern = Pattern.compile(patt, Pattern.DOTALL); 
    	Matcher matcher = pattern.matcher(str); 
    	String string = matcher.replaceAll(""); 
    	//System.out.println(string); 
    	
    	return string;*/

    	
        Pattern pattern = Pattern.compile(patt, Pattern.DOTALL);
        
        Matcher matcher = pattern.matcher(str);
        
        StringBuffer sb = new StringBuffer();
        
        while(matcher.find())
        {
            //s 即为匹配成功的字符串
            String s = matcher.group();
            
            if (picUrlList != null)
            	picUrlList.add(s.substring(8, s.length()-9));
            
            if(newStr == null)
            {
                newStr = str.replace(s.toString(), "<div style='text-algin:center;'><br> <img src='" +s.substring(8, s.length()-9)+"'/><br> </div>");
            }
            else
            	newStr = newStr.replace(s.toString(), "<div style='text-algin:center;'><br> <img  src='" +s.substring(8, s.length()-9)+"'/><br> </div>");
            
            matcher.appendReplacement(sb, "<div style='text-algin:center;'><br> <img  src='" +s.substring(8, s.length()-9)+"'/><br> </div>");
            
            
          /*  if(newStr == null)
            {
                newStr = str.replace(s.toString(), "<br> <img src='" +s.substring(8, s.length()-9)+"'/><br>");
            }
            else
            	newStr = newStr.replace(s.toString(), "<br> <img  src='" +s.substring(8, s.length()-9)+"'/><br>");
            
            matcher.appendReplacement(sb, "<br> <img  src='" +s.substring(8, s.length()-9)+"'/><br>");*/
            
            
        }
        String tail = str.substring(str.lastIndexOf(">")+1);
        
        if(newStr!= null)
           return newStr;
        else 
        	return str;
        
       /* if(newStr!= null)
        {
            str = newStr;
        }
        return str;
        
        */
        
 /*       newStr = str.replace(s.toString(), "<img src='" +s.substring(8, s.length()-9)+"'/>");
        
        str = newStr ;*/
   
        
        
    }


}