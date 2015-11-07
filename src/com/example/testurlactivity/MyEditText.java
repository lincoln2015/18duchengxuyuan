package com.example.testurlactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends EditText {   
    public MyEditText(Context context) {   
        super(context);   
    }   
    public MyEditText(Context context, AttributeSet attrs) {   
        super(context, attrs);   
    }   
    @SuppressLint("NewApi")
	public void insertDrawable(Bitmap bitmap, int x, int y, String tag) {   
    	
        final SpannableString ss = new SpannableString(tag);   
        //�õ�drawable���󣬼���Ҫ�����ͼƬ    
       // Drawable d = getResources().getDrawable(id);   
        

		BitmapDrawable bitDrawable = new BitmapDrawable(bitmap);
	
		bitDrawable.setBounds(x, y,	bitDrawable.getIntrinsicHeight() + 100, bitDrawable.getIntrinsicHeight() + 100);

   /*
        Rect rect = new Rect();
        rect.set(x, y, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        d.setBounds(rect);
        */
       
        //�����drawable��������ַ���easy    
        ImageSpan span = new ImageSpan(bitDrawable, ImageSpan.ALIGN_BASELINE);   
        //����0���ǲ�����"easy".length()����4��[0,4)��ֵ��ע����ǵ����Ǹ������ͼƬ��ʱ��ʵ���Ǹ�����"easy"����ַ�����    
        ss.setSpan(span, 0, tag.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);   
        append(ss);   
        

    }   
    
    
	public void insertToEditText(int id, String str) {   
		int start ;
		Editable et ;
		int size ;
	    final SpannableString ss = new SpannableString(str);   
        //�õ�drawable���󣬼���Ҫ�����ͼƬ    
        Drawable d = getResources().getDrawable(id);   
        
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());   
     
        //�����drawable��������ַ���easy    
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);   
        //����0���ǲ�����"easy".length()����4��[0,4)��ֵ��ע����ǵ����Ǹ������ͼƬ��ʱ��ʵ���Ǹ�����"easy"����ַ�����    
        ss.setSpan(span, 0, str.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE); 
    	
		et = 	getText();
		start = getSelectionStart();
		et.insert(start,  "\n");
		setText(et);
		//size = et.length();
		setSelection(start + "\n".length());
		

		et = 	getText();
		start = getSelectionStart();
		et.insert(start,  ss);
		setText(et);
		// size = et.length();
		setSelection(start +  ss.length());
		// setSelection(start + ss.length());
		
		et = 	getText();
		start = getSelectionStart();
		et.insert(start,  "\n");
		setText(et);
		//size = et.length();
		setSelection(start + "\n".length());
       
    }   

}  

