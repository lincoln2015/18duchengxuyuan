package com.example.testurlactivity;

import java.util.ArrayList;
import java.util.HashMap;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class QuestionCategoryListAdapter extends ArrayAdapter<QuestionCategory> {
	

	private LayoutInflater mInflater;
	private Context mContext;
	ArrayList<QuestionCategory> mQuestionCategoryList;
	
	 HashMap<String,Boolean> states=new HashMap<String,Boolean>();//���ڼ�¼ÿ��RadioButton��״̬������ֻ֤��ѡһ��  
	
	//ArrayList<Bitmap> bitMapList;

	public QuestionCategoryListAdapter(Context context, int resource, ArrayList<QuestionCategory> questionCategoryList) {
		super(context, resource);
		// TODO Auto-generated constructor stub

		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mQuestionCategoryList = questionCategoryList;
		
		 
		
		//this.bitMapList = null;
	}
	
	public void updateListView(ArrayList<QuestionCategory> categoryList){
		this.mQuestionCategoryList = categoryList;
		//this.bitMapList = bitmapList;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// return super.getCount();
		
		return mQuestionCategoryList.size();
	}

	@Override
	public QuestionCategory getItem(int position) {
		// TODO Auto-generated method stub
		// return super.getItem(position);
		
		return mQuestionCategoryList.get(position);
	}

	/*@Override
	public View getView(final  int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
        
            view = mInflater.inflate(R.layout.question_category_item, parent, false);
        

        TextView categoryTitleTextView = (TextView) view.findViewById(R.id.category_title);
        
      
        
       // TextView userNameTextView = (TextView) view.findViewById(R.id.user_name);
        
       // TextView userIntroduceTextView = (TextView) view.findViewById(R.id.user_introduce);
       
        categoryTitleTextView.setText(mQuestionCategoryList.get(position).getCategoryTitel());
// Log.d("question category @", "position:"+position +"##id:"+mQuestionCategoryList.get(position).getCategoryId() +"##titel:"+mQuestionCategoryList.get(position).getCategoryTitel());
      //  userNameTextView.setText(mUserList.get(position).getUserName() + "   ");
      //  commentContentTextView.setText(mQuestionCommentList.get(position).getCommentContent() + "   ");
      
       
        //questionContentView.setText("hello");

	}
	
        return view;


		
		  
	    ViewHolder holder;  
	    if (convertView == null) {  
	        convertView = LayoutInflater.from(context).inflate(R.layout.question_category_item, null);  
	        holder = new ViewHolder();  
	        holder.background = (LinearLayout) convertView.findViewById(R.id.search_user_list_item);  
	        holder.userName = (TextView) convertView.findViewById(R.id.search_user_name);  
	        convertView.setTag(holder);  
	    }else{  
	        holder=(ViewHolder) convertView.getTag();  
	    }  
	       final RadioButton radio=(RadioButton) convertView.findViewById(R.id.radio_btn);  
	    holder.rdBtn = radio;  
	      
	    holder.userName.setText(userList.get(position));  
	    //����Itemλ�÷��䲻ͬ����          
	    if(userList.size() > 0)  
	    {  
	        if(userList.size() == 1)  
	        {  
	            holder.background.setBackgroundResource(R.drawable.more_item_press);  
	        }  
	        else{  
	            if(position == 0){  
	                holder.background.setBackgroundResource(R.drawable.more_itemtop_press);  
	            }  
	            else if(position == userList.size()-1){  
	                holder.background.setBackgroundResource(R.drawable.more_itembottom_press);  
	            }  
	            else{  
	                holder.background.setBackgroundResource(R.drawable.more_itemmiddle_press);  
	            }  
	        }  
	    }  
	//��RadioButton��ѡ��ʱ������״̬��¼��States�У�����������RadioButton��״̬ʹ���ǲ���ѡ��      
	       holder.rdBtn.setOnClickListener(new View.OnClickListener() {  
	             
	           public void onClick(View v) {  
	               
	               //���ã�ȷ�����ֻ��һ�ѡ��  
	               for(String key:states.keySet()){  
	                   states.put(key, false);  
	                     
	               }  
	               states.put(String.valueOf(position), radio.isChecked());  
	               SearchUserAdapter.this.notifyDataSetChanged();  
	           }  
	       });  
	      
	       boolean res=false;  
	       if(states.get(String.valueOf(position)) == null || states.get(String.valueOf(position))== false){  
	           res=false;  
	           states.put(String.valueOf(position), false);  
	       }  
	       else  
	           res = true;  
	         
	       holder.rdBtn.setChecked(res);  
	  
	    return convertView;  

		
	}
	
	static class ViewHolder {  
		   LinearLayout background;  
		          TextView userName;  
		          RadioButton rdBtn;  
		}  */
	
	@Override  
	public View getView(final int position, View convertView, ViewGroup parent) {  
	    ViewHolder holder;  
	    if (convertView == null) {  
	        convertView = LayoutInflater.from(mContext).inflate(R.layout.question_category_item, null);  
	        holder = new ViewHolder();  
	        holder.background = (LinearLayout) convertView.findViewById(R.id.qustion_category_ly);  
	        holder.categoryTitle = (TextView) convertView.findViewById(R.id.category_title);  
	        convertView.setTag(holder);  
	    }else{  
	        holder=(ViewHolder) convertView.getTag();  
	    }  
	       final RadioButton radio=(RadioButton) convertView.findViewById(R.id.radio_categroy_id);  
	    holder.rdBtn = radio;  
	      
	    holder.categoryTitle.setText(mQuestionCategoryList.get(position).getCategoryTitel());  
	    //����Itemλ�÷��䲻ͬ����          
	   /* if(userList.size() > 0)  
	    {  
	        if(userList.size() == 1)  
	        {  
	            holder.background.setBackgroundResource(R.drawable.more_item_press);  
	        }  
	        else{  
	            if(position == 0){  
	                holder.background.setBackgroundResource(R.drawable.more_itemtop_press);  
	            }  
	            else if(position == userList.size()-1){  
	                holder.background.setBackgroundResource(R.drawable.more_itembottom_press);  
	            }  
	            else{  
	                holder.background.setBackgroundResource(R.drawable.more_itemmiddle_press);  
	            }  
	        }  
	    }  */
	//��RadioButton��ѡ��ʱ������״̬��¼��States�У�����������RadioButton��״̬ʹ���ǲ���ѡ��      
	       holder.rdBtn.setOnClickListener(new View.OnClickListener() {  
	             
	           public void onClick(View v) {  
	               
	               //���ã�ȷ�����ֻ��һ�ѡ��  
	               for(String key:states.keySet()){  
	                   states.put(key, false);  
	                     
	               }  
	               if (radio.isChecked())
	               {
		               states.put(String.valueOf(position), true);  
		               QuestionCategoryListAdapter.this.notifyDataSetChanged();  
	               }
	           }  
	       });  
	      
	       boolean res=false;  
	       if(states.get(String.valueOf(position)) == null || states.get(String.valueOf(position))== false){  
	           res=false;  
	           states.put(String.valueOf(position), false);  
	       }  
	       else  
	           res = true;  
	         
	       holder.rdBtn.setChecked(res);  
	  
	    return convertView;  
	}  
	  
	static class ViewHolder {  
	   LinearLayout background;  
	          TextView categoryTitle;  
	          RadioButton rdBtn;  
	}  


}