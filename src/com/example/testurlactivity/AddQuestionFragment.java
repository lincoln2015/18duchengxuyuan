package com.example.testurlactivity;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.testurlactivity.NotificationFragment.ApacheHttpThread;
import com.example.testurlactivity.questionDetailActivity.ApacheHttpThread2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddQuestionFragment extends Fragment {
	
//use for fragment structure start
 	 AddQuestionDetailFragment addDetailFragment;
	 AddQuestionDetailFragment oldAddDetailFragment;
	 AddQuestionToCategoryFragment addCategoryFragment;
	 AddQuestionToCategoryFragment  oldAddCategoryFragment;
	 AddQuestionFragment2 addQuestionFragment;
	 AddQuestionFragment2 oldAddQuestionFragment;
	 protected static final String TAG = "AddQuestionFragment";
	 View addPicLy;
	 private String picPath = null;
	//ArrayList<String> picPathList;
	//	ArrayList<Bitmap> bitMapList;
//ure for fragment struture end
	 
	 
	private String questionID;

	private ArrayList<InboxDialog> mInboxDialogList;

	private ListView mInboxDialogView;

	private InboxDialogListAdapter mAdapter;

	User user;



	Activity thisActivity;
	private View mRootView;

	SharedPreferences sharedPreferences;

	private Bitmap bitmap;

	ArrayList<Bitmap> bitMapList;
	
	EditText questionContentEditText;
	private static int QUESTION_START_DETAIL_REQUEST = 10000;
	private static int QUESTION_END_DETAIL_RESULT = 10001;
	
	
	private static int QUESTION_START_CATEGORY_REQUEST = 10002;
	private static int QUESTION_END_CATEGORY_REQUEST = 10003;
	
	

	int ADD_QUESTION_START_REQUEST = 1002;
	int ADD_QUESTION_END_REQUEST = 1003;
	
	String questionContentStr = "";
	String questionDetailStr = "";
	
	
	int userID;
	String userName;
	
	ArrayList<String> picPathList = null;
	
	static InputStream is = null;
	static String json2 = "";

	int questionId;

	int i_g = 0;

	int categoryId = -1;

	private View backView;

	CommonDialog dialog2;
	View sureLy;
	String warningMsg = "";
	TextView warningMsgTextView = null;
	
	TextView addQuestionDetailTextView ;
	TextView addToCategoryTextView ;
	TextView addQuestionTextView;
	View addQuestionDetailLy ;
	View addToCategoryLy ;
	View addQuestionLy;
	
	 
	 
	 	/*private void alertQuestionContent() {
	 		Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle("提示")
	 				.setMessage("未输入问题")
	 				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	 					public void onClick(DialogInterface dialog, int which) {
	 						//picPath = null;
	 					}
	 				}).create();
	 		dialog.show();
	 	}
	 	
	 	private void alertCategory() {
	 		Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle("提示")
	 				.setMessage("未选择分类")
	 				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	 					public void onClick(DialogInterface dialog, int which) {
	 						//picPath = null;
	 					}
	 				}).create();
	 		dialog.show();
	 	}
	 	*/
	 private void alertQuestionContent()
		{
			AlertDialog.Builder customDia=new AlertDialog.Builder(getActivity());
			final View viewDia=LayoutInflater.from(getActivity()).inflate(R.layout.add_question_answer_warning, null);
			//customDia.setTitle("新建收藏夹");
			TextView titleTextView = (TextView) viewDia.findViewById(R.id.title);
			TextView warnningTextView = (TextView) viewDia.findViewById(R.id.warnning_txt);
			
			titleTextView.setText("提交错误");
			warnningTextView.setText("未添加问题");
			
			customDia.setView(viewDia);
			customDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			
	
			customDia.create().show();
		}
	 
	 private void alertCategory()
		{
			AlertDialog.Builder customDia=new AlertDialog.Builder(getActivity());
			final View viewDia=LayoutInflater.from(getActivity()).inflate(R.layout.add_question_answer_warning, null);
			//customDia.setTitle("新建收藏夹");
			TextView titleTextView = (TextView) viewDia.findViewById(R.id.title);
			TextView warnningTextView = (TextView) viewDia.findViewById(R.id.warnning_txt);
			
			titleTextView.setText("提交错误");
			warnningTextView.setText("未选择分类");
			
			customDia.setView(viewDia);
			customDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			
	
			customDia.create().show();
		}
	
	
View.OnClickListener onClickListener = new View.OnClickListener() {
	    
        public void onClick(View v) {

        	
             if (v.getId() == R.id.add_question_detail_ly) {

            	 
            	 FragmentManager fm = getFragmentManager();
                 FragmentTransaction ft = fm.beginTransaction();
                 
                 if (oldAddDetailFragment != null)
                	 addDetailFragment = oldAddDetailFragment;
                 else
                	 addDetailFragment = new AddQuestionDetailFragment();
                 
                 ft.replace(R.id.add_content, addDetailFragment, AddQuestionFragment.TAG);
                 ft.commit();
                 oldAddDetailFragment = addDetailFragment;
                 addPicLy.setVisibility(View.VISIBLE);

            	addQuestionDetailLy.setBackgroundColor(Color.parseColor("#ffffff"));
            	addToCategoryLy.setBackgroundColor(Color.parseColor("#0077d9"));
            	addQuestionLy.setBackgroundColor(Color.parseColor("#0077d9"));
            	addQuestionDetailTextView.setTextColor(Color.parseColor("#0077d9"));
            	addToCategoryTextView.setTextColor(Color.parseColor("#ffffff"));
            	addQuestionTextView.setTextColor(Color.parseColor("#ffffff"));
             }
             if (v.getId() == R.id.add_to_category_ly) {

 				/*Intent intent = new Intent(thisActivity, AddQuestionToCategoryActivity.class);

 				startActivityForResult(intent, QUESTION_START_CATEGORY_REQUEST);*/
            	 
            	 FragmentManager fm = getFragmentManager();
                 FragmentTransaction ft = fm.beginTransaction();

                 if (oldAddCategoryFragment != null)
                	 addCategoryFragment = oldAddCategoryFragment;
                 else
                	 addCategoryFragment = new AddQuestionToCategoryFragment();
                 
                 ft.replace(R.id.add_content, addCategoryFragment, AddQuestionFragment.TAG);
                 ft.commit();
                 oldAddCategoryFragment = addCategoryFragment;
                 addPicLy.setVisibility(View.GONE);
                 
             	addToCategoryLy.setBackgroundColor(Color.parseColor("#ffffff"));
             	addQuestionDetailLy.setBackgroundColor(Color.parseColor("#0077d9"));
             	addQuestionLy.setBackgroundColor(Color.parseColor("#0077d9"));
            	addToCategoryTextView.setTextColor(Color.parseColor("#0077d9"));
             	addQuestionDetailTextView.setTextColor(Color.parseColor("#ffffff"));
             	addQuestionTextView.setTextColor(Color.parseColor("#ffffff"));
              }
             if (v.getId() == R.id.add_question_ly) {
            
  				/*Intent intent = new Intent(thisActivity, AddQuestionToCategoryActivity.class);

  				startActivityForResult(intent, QUESTION_START_CATEGORY_REQUEST);*/
             	 
             	  FragmentManager fm = getFragmentManager();
                  FragmentTransaction ft = fm.beginTransaction();
                  
                  if (oldAddQuestionFragment != null)
                	  addQuestionFragment = oldAddQuestionFragment;
                  else
                	  addQuestionFragment = new AddQuestionFragment2();
                  
                
                  ft.replace(R.id.add_content, addQuestionFragment, AddQuestionFragment.TAG);
                  ft.commit();   
                  oldAddQuestionFragment = addQuestionFragment;
                  addPicLy.setVisibility(View.GONE);
                  
                  addQuestionLy.setBackgroundColor(Color.parseColor("#ffffff"));
                  addToCategoryLy.setBackgroundColor(Color.parseColor("#0077d9"));
                  addQuestionDetailLy.setBackgroundColor(Color.parseColor("#0077d9"));
                  addQuestionTextView.setTextColor(Color.parseColor("#0077d9"));
                  addToCategoryTextView.setTextColor(Color.parseColor("#ffffff"));
                  addQuestionDetailTextView.setTextColor(Color.parseColor("#ffffff"));
             
           	 
               }
             
             
             if (v.getId() == R.id.back_ly) {
 				
 				TestURLActivity fca = (TestURLActivity) getActivity();
 				fca.toggle();
 			  }
             if (v.getId() == R.id.add_pic_ly) {
  				
            	 	Intent intent = new Intent();
        			intent.setType("image/*");
        			intent.setAction(Intent.ACTION_GET_CONTENT);
        			startActivityForResult(intent, 1);
        		
             
               	// Toast.makeText(getApplicationContext(), answerContentEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            	           
               	//	int selectionStart = Selection.getSelectionStart(answerContentEditText.getText());
               	//Toast.makeText(getApplicationContext(), "x ="+x+", y = "+y, Toast.LENGTH_SHORT).show();
  			  }
             if (v.getId() == R.id.sure_ly) {
                 
        		   dialog2.dismiss();
        			
        			//mMessage.setText("加载中...");
               	           
                   }
     
               if (v.getId() == R.id.finish_add_question_LY) {
            	   
            	   if (addQuestionFragment != null)
            		   questionContentStr =  addQuestionFragment.getQuestionEditText().getText().toString();
            	   if (addDetailFragment != null)
            		   questionDetailStr =   addDetailFragment.getDetailEditText().getText().toString();
            	   if (addCategoryFragment != null)
            		   categoryId = addCategoryFragment.getChosedCategoryId();
            	   
            		Toast.makeText(thisActivity, "click add question", Toast.LENGTH_SHORT).show();
            	   
                	 if (addQuestionFragment==null || questionContentStr.equals(""))
                	 {
                		// alertQuestionContent();
                		 warningMsgTextView.setText("未输入问题");
                		  dialog2.show();//显示Dialog

                	 }
                	 else if (addCategoryFragment == null || categoryId == -1)
                	 {
                		 warningMsgTextView.setText("未选择分类");
               		  	dialog2.show();//显示Dialog
                	 }
                	 else
                	 {
    	            	 // new net deal thread to connect to server for push the date
    	            	// Toast.makeText(getApplicationContext(), questionContentStr, Toast.LENGTH_SHORT).show();
    	            	 new ApacheHttpThread().start();	
                		 
                		 // new urlThread().start();
    	            	  
    	             	// finish();      
                	 }
            	   
            	
		   	
              }
             
             
          
        }

    };
    
    
    
	
    public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	        
	          
	         if (msg.what == 0x15) {
	     			Bundle bundle = msg.getData();
	     			//Toast.makeText(thisActivity, "@id:"+bundle.getString("text1"), Toast.LENGTH_SHORT).show();
	     			
	     			if (picPathList !=null && !picPathList.isEmpty())
	     			{
	     				// Toast.makeText(thisActivity, "#@@# id:"+bundle.getString("text1"), Toast.LENGTH_SHORT).show();
	     				new urlThread().start();
	     			}
	     			else
	     			{
	     				// thisActivity.finish();
	     				
	     				 // thisActivity.finish();
	    			     Fragment homeContent = null;
	    				 homeContent = new homeFragment();
	    				 
	    				 TestURLActivity fca = (TestURLActivity) getActivity();
	    				 if (fca != null &&  homeContent != null)
	    					 fca.switchContent(homeContent);
	     			}
	          
	         }
	         if (msg.what == 0x16) {
	        	 Bundle bundle = msg.getData();
 				// Toast.makeText(thisActivity, bundle.getString("text4"), Toast.LENGTH_SHORT).show();
	  		
	         }
	         
	         if (msg.what == 0x23) {
	        	 Fragment homeContent = null;
				 homeContent = new homeFragment();
				 
				 TestURLActivity fca = (TestURLActivity) getActivity();
				 fca.switchContent(homeContent);
	  		
	         }

	     }

	 };   


	public AddQuestionFragment() {
		// TODO Auto-generated constructor stub
	}


	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		thisActivity =	getActivity();
		
		View view = inflater.inflate(R.layout.add_question, container, false);
	    
	    mRootView = view;
	    
	    SharedPreferences sharedPreferences = thisActivity.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    userID = sharedPreferences.getInt("user_id", 1);
	        
	    userName = sharedPreferences.getString("name", "");
		
		addQuestionDetailTextView =(TextView) view.findViewById(R.id.add_question_detail);
		addToCategoryTextView = (TextView) view.findViewById(R.id.add_to_category);
		addQuestionTextView = (TextView) view.findViewById(R.id.add_question);

		View finishAddQuestionBtn = (View)view.findViewById(R.id.finish_add_question_LY);
		finishAddQuestionBtn.setOnClickListener(onClickListener);
		backView = (View) view.findViewById(R.id.back_ly);
		backView.setOnClickListener(onClickListener);
		addPicLy = (View)view.findViewById(R.id.add_pic_ly);
		addPicLy.setOnClickListener(onClickListener);


		addQuestionDetailLy = view.findViewById(R.id.add_question_detail_ly);
		addQuestionDetailLy.setOnClickListener(onClickListener);
		addToCategoryLy = view.findViewById(R.id.add_to_category_ly);
		addToCategoryLy.setOnClickListener(onClickListener);
		addQuestionLy = view.findViewById(R.id.add_question_ly);
		addQuestionLy.setOnClickListener(onClickListener);
		addQuestionLy.performClick();
		addPicLy.setVisibility(View.GONE);

		//new ApacheHttpThread().start();	
		dialog2 = new CommonDialog(thisActivity, 250, 100, R.layout.common_dialog, R.style.Theme_dialog);
		warningMsgTextView = (TextView) dialog2.findViewById(R.id.warning_message);
		
		sureLy = (View) dialog2.findViewById(R.id.sure_ly);
		sureLy.setOnClickListener(onClickListener);
		
		bitMapList = new ArrayList<Bitmap> ();
		picPathList =	new ArrayList<String> () ;
		
       /* addQuestionLy.setBackgroundColor(Color.parseColor("#ffffff"));
        addToCategoryLy.setBackgroundColor(Color.parseColor("#0077d9"));
        addQuestionDetailLy.setBackgroundColor(Color.parseColor("#0077d9"));
        addQuestionTextView.setTextColor(Color.parseColor("#0077d9"));
        addToCategoryTextView.setTextColor(Color.parseColor("#ffffff"));
        addQuestionDetailTextView.setTextColor(Color.parseColor("#ffffff"));*/
		
		return mRootView;
	}
	

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	
		if(requestCode == QUESTION_START_DETAIL_REQUEST && resultCode == QUESTION_END_DETAIL_RESULT)
        {
			 Bundle bundle = data.getExtras();
			   
			   // 获取Bundle中的数据，注意类型和key
			 questionDetailStr = bundle.getString("questionDetail");
			 
			 picPathList = bundle.getStringArrayList("picpatchlist");

			Toast.makeText(thisActivity, "@@@@"+questionDetailStr, Toast.LENGTH_SHORT).show();
        }
		if(requestCode == QUESTION_START_CATEGORY_REQUEST && resultCode == QUESTION_END_CATEGORY_REQUEST)
        {
			 Bundle bundle = data.getExtras();
			   
		
			 categoryId = bundle.getInt("category_id");
			 Toast.makeText(thisActivity, String.valueOf(categoryId), Toast.LENGTH_SHORT).show();
        }
		
	
	}
*/
	
	 @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	   		if (resultCode == Activity.RESULT_OK) {
	   			/**
	   			 * 当选择的图片不为空的话，在获取到图片的途径
	   			 */
	   			Uri uri = data.getData();
	   			Log.e(TAG, "uri = " + uri);
	   			try {
	   				String[] pojo = { MediaStore.Images.Media.DATA };

	   				Cursor cursor = thisActivity.managedQuery(uri, pojo, null, null, null);
	   				if (cursor != null) {
	   					ContentResolver cr = thisActivity.getContentResolver();
	   					int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	   					cursor.moveToFirst();
	   					String path = cursor.getString(colunm_index);
	   					/***
	   					 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
	   					 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
	   					 */
	   					if (path.endsWith("jpg") || path.endsWith("png")) {
	   						picPath = path;
	   						Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
	   						bitMapList.add(bitmap);
	   						picPathList.add(picPath);
	   					
	   						
	   						addDetailFragment.getDetailEditText().insertDrawable(bitmap, 0, 0, "[attach]" + picPath.substring(picPath.lastIndexOf("/") + 1)+ "[/attach]");  
	   						// imageView.setImageBitmap(bitmap);
	   					} else {
	   						alert();
	   					}
	   				} else {
	   					alert();
	   				}

	   			} catch (Exception e) {
	   			}
	   		}

	   		super.onActivityResult(requestCode, resultCode, data);
	   	}
	
		private void alert() {
	   		Dialog dialog = new AlertDialog.Builder(thisActivity).setTitle("提示")
	   				.setMessage("您选择的不是有效的图片")
	   				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	   					public void onClick(DialogInterface dialog, int which) {
	   						picPath = null;
	   					}
	   				}).create();
	   		dialog.show();
	   	}
	   	


	public class ApacheHttpThread extends Thread {
		String strs = "";

		public ApacheHttpThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {

				HttpClient client = new DefaultHttpClient();
				// 如果是Get提交则创建HttpGet对象，否则创建HttpPost对象
				// HttpGet httpGet = new
				// HttpGet("http://192.168.1.100:8080/myweb/hello.jsp?username=abc&pwd=321");
				// post提交的方式
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/account/ajax/login_process/");
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl + "/test/ajax/add_question/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// 如果是Post提交可以将参数封装到集合中传递
				List dataList = new ArrayList();
				
			
				dataList.add(new BasicNameValuePair("questionContent", questionContentStr));
				dataList.add(new BasicNameValuePair("questionDetail", questionDetailStr));
				dataList.add(new BasicNameValuePair("uid", String.valueOf(userID)));
				dataList.add(new BasicNameValuePair("categoryId", String.valueOf(categoryId)));
				
				// UrlEncodedFormEntity用于将集合转换为Entity对象
				httpPost.setEntity(new UrlEncodedFormEntity(dataList, HTTP.UTF_8));
				// 获取相应消息
				HttpResponse httpResponse = client.execute(httpPost);
				// 获取消息内容
				HttpEntity entity = httpResponse.getEntity();
				// 把消息对象直接转换为字符串
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				is = entity.getContent();     
				
				sleep(1000);
				
			/*	
				InputStreamReader isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String result = br.readLine();
				JSONObject json = new JSONObject(result);*/

				  BufferedReader reader = new BufferedReader(new InputStreamReader(
		                    is, "UTF-8"));
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            is.close();
		      
				JSONObject json = new JSONObject(sb.toString());

				
				if (true)
				{
					Message msg = new Message();
					msg.what = 0x15;
	
					Bundle bundle = new Bundle();
					bundle.clear();
	
					// bundle.putString("recv_server", new String(buffer));
					
					questionId = Integer.valueOf(json.getString("value"));
					bundle.putString("text1", String.valueOf(questionId));
	
					msg.setData(bundle);
	
					myHandler.sendMessage(msg);
				}

			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	// solutions 
		// solution 1: finish img & text data upload ,than update the tag info in text data
		// solution 2: finsh text data upload first ,update the txt tag info when finsh one img data upload one by one 
		// solution 3: finsih img date upload first ,rechange the text tag info one by one through search the attachment table and fin
		
		
/*
	// thread for img upload  
	public class urlThread extends Thread {
	
		int i = 0;

			public urlThread() {
				// TODO Auto-generated constructor stub
				
			
			}

			@Override
			public void run() {
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "******";
				try {
					URL url = new URL(URLHelper.SERVER_URl + "/test/ajax/upload_question_img/?question_id="+questionId+"&user_id="+userID);
					while (i < picPathList.size())
					{
						HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
						// 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
						// 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
						httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
						// 允许输入输出流
						httpURLConnection.setDoInput(true);
						httpURLConnection.setDoOutput(true);
						httpURLConnection.setUseCaches(false);
						// 使用POST方法
						httpURLConnection.setRequestMethod("GET");
						httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
						httpURLConnection.setRequestProperty("Charset", "UTF-8");
						httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
						
					
						DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
						dos.writeBytes(twoHyphens + boundary + end);
						dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""	+ picPathList.get(i).substring(picPathList.get(i).lastIndexOf("/") + 1) + "\""+ end);
						dos.writeBytes(end);
	
						// get the pic file by picNameList pic name
						// FileInputStream fis = new FileInputStream(picNameList.get(i));
						FileInputStream fis = new FileInputStream(picPathList.get(i));
						byte[] buffer = new byte[8192]; // 8k
						int count = 0;
						// 读取文件
						while ((count = fis.read(buffer)) != -1) {
							dos.write(buffer, 0, count);
						}
						fis.close();
	
						dos.writeBytes(end);
						dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
						dos.flush();
						
						sleep(2000);
	
						InputStream is = httpURLConnection.getInputStream();
						InputStreamReader isr = new InputStreamReader(is, "utf-8");
						BufferedReader br = new BufferedReader(isr);
						String result = br.readLine();

					
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is, "UTF-8"));
						StringBuilder sb = new StringBuilder();
						String line = null;
						while ((line = reader.readLine()) != null) {
							sb.append(line + "\n");
						}
						is.close();

						JSONObject json = new JSONObject(sb.toString());

						if (true)
						{
							Message msg = new Message();
							msg.what = 0x16;
			
							Bundle bundle = new Bundle();
							bundle.clear();
			
							bundle.putString("text4", json.toString());
							// bundle.putString("text4", json.getString("value"));
			
							msg.setData(bundle);
			
							myHandler.sendMessage(msg);
						}
	
						//Toast.makeText(this, result, Toast.LENGTH_LONG).show();
						dos.close();
						is.close();
						i++;
					}
					
					Message msg = new Message();
					msg.what = 0x23;
	
					Bundle bundle = new Bundle();
					bundle.clear();
	
					bundle.putString("FINISH", "finish");
					// bundle.putString("text4", json.getString("value"));
	
					msg.setData(bundle);
	
					myHandler.sendMessage(msg);

				} catch (Exception e) {
					e.printStackTrace();
					//setTitle(e.getMessage());
				}

		}
	 }*/
	

		// thread for img upload  
		public class urlThread extends Thread {
		
			
			long requestTime;
			long responseTime = 0;
		
			private  String BOUNDARY ; // 边界标识
			Map<String, String> param;
		
																					// 随机生成
			private static final String PREFIX = "--";
			private static final String LINE_END = "\r\n";
			private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
			private static final String TAG = "UploadUtil";
			private int readTimeOut = 10 * 1000; // 读取超时
			private int connectTimeout = 10 * 1000; // 超时时间
			/***
			 * 请求使用多长时间
			 */
			private static final String CHARSET = "utf-8"; // 设置编码

			/***
			 * 上传成功
			 */
			public static final int UPLOAD_SUCCESS_CODE = 1;
			/**
			 * 文件不存在
			 */
			public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
			/**
			 * 服务器出错
			 */
			public static final int UPLOAD_SERVER_ERROR_CODE = 3;
			protected static final int WHAT_TO_UPLOAD = 1;
			protected static final int WHAT_UPLOAD_DONE = 2;

				public urlThread() {
				
					// TODO Auto-generated constructor stub
					BOUNDARY = UUID.randomUUID().toString();
					requestTime = System.currentTimeMillis();
					
					param = new HashMap<String, String>();
					
					param.put("question_id", String.valueOf(questionId));
					param.put("user_id", String.valueOf(userID));
				
				}

				@Override
				public void run() {
					String end = "\r\n";
					String twoHyphens = "--";
					String boundary = "******";
					String result ="";
				
				
					try {
						URL url = new URL(URLHelper.SERVER_URl + "/test/ajax/upload_question_img/");
						for (int i = 0; i < picPathList.size();i++)
						{	
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setReadTimeout(readTimeOut);
							conn.setConnectTimeout(connectTimeout);
							conn.setDoInput(true); // 允许输入流
							conn.setDoOutput(true); // 允许输出流
							conn.setUseCaches(false); // 不允许使用缓存
							conn.setRequestMethod("POST"); // 请求方式
							conn.setRequestProperty("Charset", CHARSET); // 设置编码
							conn.setRequestProperty("connection", "keep-alive");
							conn.setRequestProperty("user-agent",
									"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
							conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
									+ BOUNDARY);
							// conn.setRequestProperty("Content-Type",
							// "application/x-www-form-urlencoded");

							/**
							 * 当文件不为空，把文件包装并且上传
							 */
							DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
							StringBuffer sb = null;
							String params = "";

							/***
							 * 以下是用于上传参数
							 */
							if (param != null && param.size() > 0) {
								Iterator<String> it = param.keySet().iterator();
								while (it.hasNext()) {
									sb = null;
									sb = new StringBuffer();
									String key = it.next();
									String value = param.get(key);
									sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
									sb.append("Content-Disposition: form-data; name=\"")
											.append(key).append("\"").append(LINE_END)
											.append(LINE_END);
									sb.append(value).append(LINE_END);
									params = sb.toString();
									Log.i(TAG, key + "=" + params + "##");
									dos.write(params.getBytes());
									// dos.flush();
								}
							}

							sb = null;
							params = null;
							sb = new StringBuffer();
							/**
							 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
							 * filename是文件的名字，包含后缀名的 比如:abc.png
							 */
							
							sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
							sb.append("Content-Disposition:form-data; name=\"uploadedfile\"; filename=\"" + picPathList.get(i).substring(picPathList.get(i).lastIndexOf("/") + 1) + "\"" + LINE_END);
							sb.append("Content-Type:image/pjpeg" + LINE_END); // 这里配置的Content-type很重要的
																				// ，用于服务器端辨别文件的类型的
							sb.append(LINE_END);
							params = sb.toString();
							sb = null;

							dos.write(params.getBytes());
							/** 上传文件 */
							InputStream is = new FileInputStream(picPathList.get(i));
							byte[] bytes = new byte[1024];
							int len = 0;
							int curLen = 0;
							while ((len = is.read(bytes)) != -1) {
								curLen += len;
								dos.write(bytes, 0, len);
							}
							is.close();

							dos.write(LINE_END.getBytes());
							byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
									.getBytes();
							dos.write(end_data);
							dos.flush();
							//
							// dos.write(tempOutputStream.toByteArray());
							/**
							 * 获取响应码 200=成功 当响应成功，获取响应的流
							 */
							int res = conn.getResponseCode();
							responseTime = System.currentTimeMillis();
							this.requestTime = (int) ((responseTime - requestTime) / 1000);
							Log.e(TAG, "response code:" + res);
							if (res == 200) {
								Log.e(TAG, "request success");
								InputStream input = conn.getInputStream();
								StringBuffer sb1 = new StringBuffer();
								int ss;
								while ((ss = input.read()) != -1) {
									sb1.append((char) ss);
								}
								result = sb1.toString();
								Log.e(TAG, "result : " + result);
								//sendMessage(UPLOAD_SUCCESS_CODE, "上传结果：" + result);
								
								JSONObject json = new JSONObject(result);
								if (json.getString("value").equals("failed"))
									i--;
							
								
								Message msg = new Message();
								msg.what = 0x16;
				
								Bundle bundle = new Bundle();
								bundle.clear();
				
								bundle.putString("text4", result);
								// bundle.putString("text4", json.getString("value"));
				
								msg.setData(bundle);
				
								myHandler.sendMessage(msg);
								
								//return;
							} else {
								Log.e(TAG, "request error");
								//sendMessage(UPLOAD_SERVER_ERROR_CODE, "上传失败：code=" + res);
								
								Message msg = new Message();
								msg.what = 0x16;
				
								Bundle bundle = new Bundle();
								bundle.clear();
				
								bundle.putString("text4", "failed");
								// bundle.putString("text4", json.getString("value"));
				
								msg.setData(bundle);
				
								myHandler.sendMessage(msg);
								i--;
								//return;
							}
							
						}
						
						Message msg = new Message();
						msg.what = 0x23;
		
						Bundle bundle = new Bundle();
						bundle.clear();
		
						bundle.putString("text4", "finish");
						// bundle.putString("text4", json.getString("value"));
		
						msg.setData(bundle);
		
						myHandler.sendMessage(msg);

					} catch (Exception e) {
						e.printStackTrace();
						//setTitle(e.getMessage());
					}

			}
		 }
		
		
		

}
