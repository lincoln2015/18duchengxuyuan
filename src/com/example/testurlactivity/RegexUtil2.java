package com.example.testurlactivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil2 {
	
	// public static String patt = "\\[em_[0-9]*\\]"; 
	// public static String patt = "\\[attach\\](.*?)\\[/attach\\]"; 
	// public static String patt = "<a.*href=\".*\">(.+?)</a>"; 
	// public static String patt = "\\[attach\\](.*?)\\[/attach\\]"; 
	public static String patt ; 
	
    private static String newStr ;
    
    
    public RegexUtil2()
    {
    	this.patt = "\\[attach\\](.+?)\\[/attach\\]"; 
    	this.newStr = null;
    }
    
    public String replaceBlank(String str)
    {
    	String dest = "";
    	
    	if (str != null)
    	{
    		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
    		Matcher m = p.matcher(str);
    		dest = m.replaceAll("");
    	}
    	
    	return dest;
    }
    
    public static String geTtextOfHtml(String str)
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
            
            if(newStr == null)
            {
                newStr = str.replace(s.toString(), "");
            }
            else
            	newStr = newStr.replace(s.toString(), "");
            
            matcher.appendReplacement(sb, "");
            
            
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