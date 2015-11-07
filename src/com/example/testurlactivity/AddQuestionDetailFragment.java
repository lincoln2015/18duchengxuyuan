package com.example.testurlactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddQuestionDetailFragment extends Fragment {
	
	MyEditText detailEdit;

	public AddQuestionDetailFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		  View  contentView=  inflater.inflate(R.layout.fragment_add_question_detail,null);

		  detailEdit = (MyEditText)contentView.findViewById(R.id.question_detail);

	      return contentView;
    }
	
	
	public MyEditText getDetailEditText()
	{
		return detailEdit;
	}
	

}
