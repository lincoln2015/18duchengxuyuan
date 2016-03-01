package com.example.testurlactivity;

import java.util.ArrayList;






import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionListAdapter extends ArrayAdapter<Question> {
	
	   private LayoutInflater mInflater;
	   private TextView mContactInfoText;
	   private Context mContext;
	   ArrayList<Question> mQuestionList;
	   
		int QUESTION_DETAIL_START_REQUEST = 1000;
		int QUESTION_DETAIL_END_REQUEST = 1001;
		
		int	QUESTION_DETAIL_START_REQUEST_2 = 2000;
		int QUESTION_DETAIL_END_REQUEST_2 = 2001;
		
		 ArrayList<Bitmap> bitMapList;

	public QuestionListAdapter(Context context, int resource, ArrayList<Question> questionList) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mQuestionList = questionList;
		
		
		bitMapList = null;
	}
	
	public void updateListView(ArrayList<Bitmap> bitmapList){
		// this.mQuestionList = list;
		this.bitMapList = bitmapList;
		notifyDataSetChanged();
}



	@Override
	public void add(Question object) {
		// TODO Auto-generated method stub
		super.add(object);
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return super.getContext();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return mQuestionList.size();
	//	return super.getCount();
	}

	@Override
	public Question getItem(int position) {
		// TODO Auto-generated method stub
	//	return super.getItem(position);
		return mQuestionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return super.getView(position, convertView, parent);
		

			View view = null;
	        if (convertView != null) {
	            view = convertView;
	        } else {
	        
	            view = mInflater.inflate(R.layout.question_list_item, parent, false);
	        }

	    	
	    	ImageView imgeView = (ImageView) view.findViewById(R.id.question_by_user_img);
	        
	        TextView questionContentView = (TextView) view.findViewById(R.id.question_content);
	        
	        TextView categoryTitleView = (TextView) view.findViewById(R.id.category_title);
	        
	        TextView answerCountView = (TextView) view.findViewById(R.id.answer_count);
	        
	        TextView questionByUserNameView = (TextView) view.findViewById(R.id.question_by_user_name);
	        
	        TextView questionNewestAnswerContentView = (TextView) view.findViewById(R.id.question_newest_answer_content);

	        
	        int answerCount = mQuestionList.get(position).getAnswerCount();
	   
	   
	      //  categoryTitleView.setText("from  " + mQuestionList.get(position).getCategoryTitle());
	        questionByUserNameView.setText(mQuestionList.get(position).getUserName());
	        categoryTitleView.setText(mQuestionList.get(position).getCategoryTitle());
	        questionContentView.setText(mQuestionList.get(position).getContent());
	        
	        if (answerCount ==0)
	        {
	        	answerCountView.setVisibility(View.GONE);
	        	questionNewestAnswerContentView.setVisibility(View.GONE);
	        }
	        else
	        {
	        	answerCountView.setText(Integer.toString(answerCount) + "   ");
	        	
	        	String contentStr = mQuestionList.get(position).getNewestAnswer();

	            RegexUtil2 regeUtil = new RegexUtil2();
	            
	            String tmpStr =  regeUtil.geTtextOfHtml(contentStr);
	            
	            tmpStr = regeUtil.replaceBlank(tmpStr);
	            
	            if (tmpStr.length() == 0)
	            	questionNewestAnswerContentView.setText("[图片]");
	            else
	            	questionNewestAnswerContentView.setText(tmpStr);
	            

	     		answerCountView.setVisibility(View.VISIBLE);
	        	questionNewestAnswerContentView.setVisibility(View.VISIBLE);
	        }
	        
	     
	        
	        if (bitMapList != null)
	        {
	        	if (bitMapList.get(position) != null)
	        	{
	        		imgeView.setImageBitmap(bitMapList.get(position));
	        	
	        		imgeView.setVisibility(View.VISIBLE);
	        	}
	        }
	        else
	        	imgeView.setVisibility(View.GONE);
	        
	        questionByUserNameView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					 if (URLHelper.ISLOGIN == true)
	            	 {
							Intent intent = new Intent(mContext, userDetailActivity.class);
							
							
					 		Bundle mBundle = new Bundle();    
					  		mBundle.putSerializable("key", getItem(position).getPublishUser()); 
					 		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					  	
					 		intent.putExtras(mBundle);    
					 	  
							
					 		mContext.startActivity(intent);
					 		
					 		Activity activity = (Activity)mContext;
					 		 
						 	activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		            	 
	            	 }
	            	 else
	            	 {		
	            		
	            		 Intent intent = new Intent(mContext, UserLoginActivity.class);   

	            		 mContext.startActivity(intent);
	 
	            	 }
	            
					
			 		
			 	
				}
			});
	  
	        
	        
	        categoryTitleView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
	 
				 	 if (URLHelper.ISLOGIN == true)
	            	 {

					 		Intent intent = new Intent(mContext, CategoryShowDetailActivity.class);

					 		  Bundle bundle = new Bundle();
				           	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
				           	  bundle.putString("category_id", String.valueOf(getItem(position).getCategoryId()));
				           	 bundle.putString("category_title", String.valueOf(getItem(position).getCategoryTitle()));
				           	  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				           	  intent.putExtras(bundle);    
						
				           	  mContext.startActivity(intent);
				           	  
				 	 		 Activity activity = (Activity)mContext;
					 		 
						 	 activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	            	 }
	            	 else
	            	 {		
	            		
	            		 Intent intent = new Intent(mContext, UserLoginActivity.class);   
	            
	            		 mContext.startActivity(intent);
	 
	            	 }
	            
				}
			});
	  
	        
	        
	        questionContentView.setOnClickListener(new View.OnClickListener() {
	        	

				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
			 	
			 		 if (URLHelper.ISLOGIN == true)
	            	 {
			 			 	/*				 		
	 						Intent intent = new Intent(mContext, questionDetailActivity.class);
							
					 		Bundle mBundle = new Bundle();    
					  		mBundle.putSerializable("key", getItem(position));    
					  		// Toast.makeText(getActivity(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
					 		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					  		intent.putExtras(mBundle);    
		
					 		mContext.startActivity(intent);
					 		
					 		 Activity activity = (Activity)mContext;
					 		 
					 		activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
					 		*/
			 			 
			 			 	Intent intent = new Intent(mContext, questionDetailActivity.class);
					 		Bundle mBundle = new Bundle();    
					  		mBundle.putSerializable("key", getItem(position));    
					  		// Toast.makeText(getActivity(), String.valueOf(mAdapter.getItem(arg2).getQuestionId()), Toast.LENGTH_LONG).show();
					 		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					  		intent.putExtras(mBundle);    
					 		Activity activity = (Activity)mContext;
					 		activity.startActivityForResult(intent, QUESTION_DETAIL_START_REQUEST_2);
						 	activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	 			
	            	 }
	            	 else
	            	 {		
	            		
	            		 Intent intent = new Intent(mContext, UserLoginActivity.class);   
	            
	            		 mContext.startActivity(intent);
	 
	            	 }
	            
				}
			});
	  
	        questionNewestAnswerContentView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
	
					 	 if (URLHelper.ISLOGIN == true)
		            	 {
					 		 Intent intent = new Intent(mContext, AnswerContentDetailActivity.class);
			 				  /* 通过Bundle对象存储需要传递的数据 */
			            	  Bundle bundle = new Bundle();
			            	  /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
			            	  bundle.putString("answer_id", String.valueOf(getItem(position).getNewestAnswerID()));
			            	
			            	  /*把bundle对象assign给Intent*/
			           			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			            	  intent.putExtras(bundle);

			            	  mContext.startActivity(intent); 
			            	  
			     	 		  Activity activity = (Activity)mContext;
					 		 
						 	  activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		            	 }
		            	 else
		            	 {		
		            		
		            		 Intent intent = new Intent(mContext, UserLoginActivity.class);   
		            
		            		 mContext.startActivity(intent);
		 
		            	 }
						
				}
			});
	  
	       
	     
	       
	        //questionContentView.setText("hello");
	        
	       	        
	        return view;
	}
	
	

}
