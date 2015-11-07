package com.example.testurlactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddQuestionFragment2 extends Fragment {

	MyEditText questionContentEdit;

	public AddQuestionFragment2() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		  View  contentView=  inflater.inflate(R.layout.fragment_add_question,null);

		  questionContentEdit = (MyEditText)contentView.findViewById(R.id.question_content);
		  
	      return contentView;
    }

	public MyEditText getQuestionEditText()
	{
		return questionContentEdit;
	}
	
}
