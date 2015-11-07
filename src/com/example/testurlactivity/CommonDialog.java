package com.example.testurlactivity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class CommonDialog extends Dialog {
	
	private static int default_width = 160; // Ĭ�Ͽ��
	private static int default_height = 120;// Ĭ�ϸ߶�
	
	private static String warning_message ="";

	public CommonDialog(Context context, int layout, int style) {
		this(context, default_width, default_height, layout, style);
	}

	public CommonDialog(Context context, int width, int height, int layout,	int style) {
		super(context, style);
		// set content
		setContentView(layout);
		// set window params
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// set width,height by density and gravity
		float density = getDensity(context);
		params.width = (int) (width * density);
		params.height = (int) (height * density);
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		this.setCancelable(false);
	}

	private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}
	
	private void setWarnningMsg(String warningMsg)
	{
		this.warning_message = warningMsg;
	}
}