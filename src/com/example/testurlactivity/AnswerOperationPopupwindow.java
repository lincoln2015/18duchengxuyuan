package com.example.testurlactivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class AnswerOperationPopupwindow extends PopupWindow {
	private View thanksLyView, agreeLyView, disagreeLyView,addToFavorLyView,commentLyView;
	private View mMenuView;
	private ImageView favorImgView;
	private ImageView commentImgView;
	private ImageView thanksImgView;
	private ImageView agreeImgView;
	private ImageView disagreeImgView;

	public AnswerOperationPopupwindow(Activity context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		mMenuView = inflater.inflate(R.layout.answer_operation_popwindow, null);
		thanksLyView = (View) mMenuView.findViewById(R.id.thanks_ly);
		agreeLyView = (View) mMenuView.findViewById(R.id.agree_ly);
		disagreeLyView = (View) mMenuView.findViewById(R.id.disagree_ly);
		addToFavorLyView = (View) mMenuView.findViewById(R.id.add_to_favor_ly);
		commentLyView = (View) mMenuView.findViewById(R.id.commentLy);
		
		favorImgView = (ImageView)mMenuView.findViewById(R.id.faver_imgview);
		commentImgView = (ImageView)mMenuView.findViewById(R.id.comment_img);
		thanksImgView = (ImageView)mMenuView.findViewById(R.id.thanks_img);
		agreeImgView = (ImageView)mMenuView.findViewById(R.id.agree_img);
		disagreeImgView = (ImageView)mMenuView.findViewById(R.id.disagree_img);
		
		// 取消按钮
		thanksLyView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 销毁弹出框
				//dismiss();
			}
		});
		// 设置按钮监听
		thanksLyView.setOnClickListener(itemsOnClick);
		agreeLyView.setOnClickListener(itemsOnClick);
		disagreeLyView.setOnClickListener(itemsOnClick);
		addToFavorLyView.setOnClickListener(itemsOnClick);
		commentLyView.setOnClickListener(itemsOnClick);
		
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		// disable by anxiang.xiao 
		// this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		// ColorDrawable dw = new ColorDrawable(0xb0000000);
		 ColorDrawable dw = new ColorDrawable(0xffffff);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int height = mMenuView.findViewById(R.id.pop_ly).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}
	
	public void setFavorTouch(Context context, int id)
	{
		
		// favorImgView.setBackgroundResource(R.drawable.shouchang_blu_icon_white_background);
		// favorImgView.setBackgroundResource(0x000000);
		
		Drawable  d = context.getResources().getDrawable(id);//找到你要加入的图片
		//	shareImg.setBackground(d);
		favorImgView.setImageDrawable(d);
	}
	
	public void setThanksTouch(Context context, int id)
	{
		
		// favorImgView.setBackgroundResource(R.drawable.shouchang_blu_icon_white_background);
		// favorImgView.setBackgroundResource(0x000000);
		
		Drawable  d = context.getResources().getDrawable(id);//找到你要加入的图片
		//	shareImg.setBackground(d);
		thanksImgView.setImageDrawable(d);
	}
	
	public void setAgreeTouch(Context context, int id)
	{
		
		// favorImgView.setBackgroundResource(R.drawable.shouchang_blu_icon_white_background);
		// favorImgView.setBackgroundResource(0x000000);
		
		Drawable  d = context.getResources().getDrawable(id);//找到你要加入的图片
		//	shareImg.setBackground(d);
		agreeImgView.setImageDrawable(d);
	}
	
	public void setDisagreeTouch(Context context, int id)
	{
		
		// favorImgView.setBackgroundResource(R.drawable.shouchang_blu_icon_white_background);
		// favorImgView.setBackgroundResource(0x000000);
		
		Drawable  d = context.getResources().getDrawable(id);//找到你要加入的图片
		//	shareImg.setBackground(d);
		disagreeImgView.setImageDrawable(d);
	}
	
	public void setCommentTouch(Context context, int id)
	{
		// commentImgView.setBackgroundResource(R.drawable.comment_blu_icon_white_background);
		
		Drawable  d = context.getResources().getDrawable(id);//找到你要加入的图片
		//	shareImg.setBackground(d);
		commentImgView.setImageDrawable(d);
	}
	
	public void setIsAnswerThanksState(Context context, boolean isAnswerThanks)
	{
		if (isAnswerThanks)
		{
			// commentImgView.setBackgroundResource(R.drawable.comment_blu_icon_white_background);
			
			Drawable  d = context.getResources().getDrawable(R.drawable.love_blu_icon_wihite_ground);//找到你要加入的图片
			//	shareImg.setBackground(d);
			thanksImgView.setImageDrawable(d);
			
			thanksLyView.setClickable(false);
		}
		else
		{
			// commentImgView.setBackgroundResource(R.drawable.comment_blu_icon_white_background);

			Drawable d = context.getResources().getDrawable(R.drawable.love_grey_icon_white_background);// 找到你要加入的图片
			// shareImg.setBackground(d);
			thanksImgView.setImageDrawable(d);

			thanksLyView.setClickable(true);
		}
			
	}
	
	public void setIsAnswerVoteState(Context context, boolean isAnswerVote, int voteValue)
	{
		if (isAnswerVote)
		{
			// commentImgView.setBackgroundResource(R.drawable.comment_blu_icon_white_background);
			if (voteValue == 1)
			{
				Drawable  d1 = context.getResources().getDrawable(R.drawable.agree_blu_icon_white_back_ground);//找到你要加入的图片
				//	shareImg.setBackground(d);
				agreeImgView.setImageDrawable(d1);
				
				Drawable  d2 = context.getResources().getDrawable(R.drawable.disagree_grey_icon_white_background);//找到你要加入的图片
				//	shareImg.setBackground(d);
				disagreeImgView.setImageDrawable(d2);
				
				agreeLyView.setClickable(false);
				disagreeLyView.setClickable(true);
			}
			else
			{
				Drawable  d1 = context.getResources().getDrawable(R.drawable.disagree_blu_icon_white_back_ground);//找到你要加入的图片
				//	shareImg.setBackground(d);
				disagreeImgView.setImageDrawable(d1);
				
				Drawable  d2 = context.getResources().getDrawable(R.drawable.agree_grey_icon_white_background);//找到你要加入的图片
				//	shareImg.setBackground(d);
				agreeImgView.setImageDrawable(d2);
				
				disagreeLyView.setClickable(false);
				agreeLyView.setClickable(true);
			}
		}
		else
		{
			Drawable  d1 = context.getResources().getDrawable(R.drawable.disagree_grey_icon_white_background);//找到你要加入的图片
			//	shareImg.setBackground(d);
			disagreeImgView.setImageDrawable(d1);
			
			Drawable  d2 = context.getResources().getDrawable(R.drawable.agree_grey_icon_white_background);//找到你要加入的图片
			//	shareImg.setBackground(d);
			agreeImgView.setImageDrawable(d2);
			
			disagreeLyView.setClickable(true);
			agreeLyView.setClickable(true);
		}
			
	}


}


