package com.example.testurlactivity;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;






import com.example.testurlactivity.AddQuestionFragment.urlThread;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.Layout;
import android.text.Selection;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAnwserActivity extends Activity {
	
	MyEditText answerContentEditText;

	String answerContentStr = "";
	
	String questionID;
	
	int ADD_START_REQUEST = 1000;
	int ADD_END_REQUEST = 1001;
	int currentUserId;
	
	EditText xPos;
	EditText yPos;

	private String uploadFile = "/sdcard/testimg.jpg";
	private String srcPath = "/sdcard/testimg.jpg";
	private String srcPathDir = "/sdcard/";
	// �������Ͻ����ļ��Ĵ���ҳ�棬���������Ҫ�����Լ���
	private String actionUrl = "http://192.168.42.183/wecenter/recv_img.php";
	
	
	ArrayList<String> picNameList;
	
	ArrayList<String> picPathList;
	
	 ArrayList<Bitmap> bitMapList;

	static InputStream is = null;
	static String json2 = "";

	int addAnsweredId;

	private String picPath = null;

	String result;
	private static final String TAG = "uploadImage";
	
	CommonDialog dialog2 = null;
	View sureLy;
	String warningMsg = "";
	TextView warningMsgTextView = null;
	 

	public AddAnwserActivity() {
		// TODO Auto-generated constructor stub
	}
	

	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	        
	          
	         if (msg.what == 0x15) {
	     			Bundle bundle = msg.getData();
	     			// Toast.makeText(getApplicationContext(), "answer_id:"+bundle.getString("text1")+"@@@ user_id:"+currentUserId, Toast.LENGTH_SHORT).show();
	     	

		     			if (picPathList !=null && !picPathList.isEmpty())
		     			{
		     				// Toast.makeText(getApplicationContext(), "#@@# id:"+bundle.getString("text1"), Toast.LENGTH_SHORT).show();
		     				 new urlThread().start();
		     			}
		     			else
		     			{
			            	 Intent intent = new Intent();
				             AddAnwserActivity.this.setResult(ADD_END_REQUEST, intent);
				             AddAnwserActivity.this.finish();
		     			}
		          
	     				
	     			 //	showTextView.setText(bundle.getString("text1"));
	                 
	         }
	         if (msg.what == 0x16) {
		     		Bundle bundle = msg.getData();
		     		// Toast.makeText(getApplicationContext(), bundle.getString("text4") , Toast.LENGTH_SHORT).show();
		     			
		     		// new urlThread(picNameList).start();
		     				
		     		// showTextView.setText(bundle.getString("text1"));
		                 
		         }
	         if (msg.what == 0x23) {
	        	
	        	 Intent intent = new Intent();
	             AddAnwserActivity.this.setResult(ADD_END_REQUEST, intent);
	             AddAnwserActivity.this.finish();
	         }
		  
	        
	         
	     }

	 };
	 
		
		private int getCurrrentLine(EditText editText)
		{
			int selectionStart = Selection.getSelectionStart(editText.getText());
			
			
			Layout layout = editText.getLayout();
			
			// int line = selectionStart / layout.getLineCount();
			
			int line = selectionStart / layout.getWidth();
			
			
			
			/*if (selectionStart != -1)
				return layout.getLineForOffset(selectionStart) +1;
			
			layout.getOffsetToLeftOf(selectionStart);
			return -1;*/
			
			return line;
		}
		
		private int getCurrrentColum(EditText editText)
		{
			int selectionStart = Selection.getSelectionStart(editText.getText());
			
			
			Layout layout = editText.getLayout();
			
			
			int colum = selectionStart %  layout.getWidth();
		
			
			
			return colum;
			
			/*if (selectionStart != -1)
				return layout.getOffsetToLeftOf(selectionStart) + 1;
			
			
			return -1;*/
		}
		
		public int getLWidth(EditText editText)
		{
			//int selectionStart = Selection.getSelectionStart(editText.getText());
			Layout layout = editText.getLayout();
			
			
			return  layout.getWidth();	
		}
	
		View.OnClickListener onClickListener = new View.OnClickListener() {
	    
        public void onClick(View v) {
        	
            if (v.getId() == R.id.sure_ly) {
                
            	dialog2.dismiss();
     			
     			//mMessage.setText("������...");
            	           
                }
        	
        	   if (v.getId() == R.id.cancel_ly) {
              
        		 	 finish();
               	           
                   }
   
             if (v.getId() == R.id.publish_answer_ly) {
            	 
            	 // for client
            	 // step 1:parse the input data ,prudce the  img data & text data
            	 // step 2:send the img data to the server 
            	 // step 3:send the txt data to the server (include the img tag in the text)
            	 
            	 
            	 //for server
            	 //step 1:implement a func to recv img data from client ,save the img data to server & add tag info to server database
            	 //step 2:implement a func to recv text data from client ,save the text data to server 
            	 
            	 
            	Toast.makeText(getApplicationContext(), "click add question", Toast.LENGTH_SHORT).show();
            	 answerContentStr = answerContentEditText.getText().toString();
            	 
            	
              	 if (answerContentStr.equals(""))
              	 {
              		// alertQuestionContent();
              		  warningMsgTextView.setText("δ�����");
              		  if (dialog2 != null)
              			  dialog2.show();//��ʾDialog

              	 }
              	 else
              	 {
            	 
	            	 // new net deal thread to connect to server for push the date
	            	// Toast.makeText(getApplicationContext(), questionContentStr, Toast.LENGTH_SHORT).show();
	            	 // upload img data
	            	 // upload text data
	            	 new ApacheHttpThread().start();	
	     
              	 }
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
             
            
             
        }

    };
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			/**
			 * ��ѡ���ͼƬ��Ϊ�յĻ����ڻ�ȡ��ͼƬ��;��
			 */
			Uri uri = data.getData();
			Log.e(TAG, "uri = " + uri);
			try {
				String[] pojo = { MediaStore.Images.Media.DATA };

				Cursor cursor = managedQuery(uri, pojo, null, null, null);
				if (cursor != null) {
					ContentResolver cr = this.getContentResolver();
					int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path = cursor.getString(colunm_index);
					/***
					 * ���������һ���ж���Ҫ��Ϊ�˵����������ѡ�񣬱��磺ʹ�õ��������ļ��������Ļ�����ѡ����ļ��Ͳ�һ����ͼƬ�ˣ�
					 * �����Ļ��������ж��ļ��ĺ�׺�� �����ͼƬ��ʽ�Ļ�����ô�ſ���
					 */
					if (path.endsWith("jpg") || path.endsWith("png")) {
						picPath = path;
						Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
						bitMapList.add(bitmap);
						picPathList.add(picPath);
					
						answerContentEditText.insertDrawable(bitmap, 0, 0, "[attach]" + picPath.substring(picPath.lastIndexOf("/") + 1)+ "[/attach]");  
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
		Dialog dialog = new AlertDialog.Builder(this).setTitle("��ʾ")
				.setMessage("��ѡ��Ĳ�����Ч��ͼƬ")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						picPath = null;
					}
				}).create();
		dialog.show();
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE); 

		setContentView(R.layout.add_answer);

		Intent intent = getIntent();
		questionID = intent.getStringExtra("question_id");

	    SharedPreferences sharedPreferences = this.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    currentUserId = sharedPreferences.getInt("user_id", 1);
        
      
		answerContentEditText = (MyEditText) findViewById(R.id.answer_content);
		// answerContentEditText.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
		/*answerContentEditText.setSingleLine(false);
		answerContentEditText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);*/
		
		answerContentEditText.setSelection(0);

		View cancleBtn = findViewById(R.id.cancel_ly);
		cancleBtn.setOnClickListener(onClickListener);
		

		View publishAnswerBtn = findViewById(R.id.publish_answer_ly);
		publishAnswerBtn.setOnClickListener(onClickListener);
		
		View addPicBtn = findViewById(R.id.add_pic_ly);
		addPicBtn.setOnClickListener(onClickListener);
		

		dialog2 = new CommonDialog(AddAnwserActivity.this, 250, 100, R.layout.common_dialog, R.style.Theme_dialog);
		warningMsgTextView = (TextView) dialog2.findViewById(R.id.warning_message);
		sureLy = (View) dialog2.findViewById(R.id.sure_ly);
		sureLy.setOnClickListener(onClickListener);
		

		 picPathList = new ArrayList<String>();
		 
		 bitMapList = new ArrayList<Bitmap>() ;
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
				// �����Get�ύ�򴴽�HttpGet���󣬷��򴴽�HttpPost����
				// HttpGet httpGet = new
				// HttpGet("http://192.168.1.100:8080/myweb/hello.jsp?username=abc&pwd=321");
				// post�ύ�ķ�ʽ
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/account/ajax/login_process/");
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl + "/test/ajax/add_answer/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// �����Post�ύ���Խ�������װ�������д���
				List dataList = new ArrayList();
				
			
				dataList.add(new BasicNameValuePair("answer_content", answerContentStr));
				dataList.add(new BasicNameValuePair("question_id", questionID));
				dataList.add(new BasicNameValuePair("user_id", String.valueOf(currentUserId)));
				
				// UrlEncodedFormEntity���ڽ�����ת��ΪEntity����
				httpPost.setEntity(new UrlEncodedFormEntity(dataList, HTTP.UTF_8));
				// ��ȡ��Ӧ��Ϣ
				HttpResponse httpResponse = client.execute(httpPost);
				// ��ȡ��Ϣ����
				HttpEntity entity = httpResponse.getEntity();
				// ����Ϣ����ֱ��ת��Ϊ�ַ���
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				is = entity.getContent();     
				
				sleep(1000);
				
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
					msg.what = 0x15;
	
					Bundle bundle = new Bundle();
					bundle.clear();
	
					// bundle.putString("recv_server", new String(buffer));
					
					addAnsweredId = Integer.valueOf(json.getString("value"));
					bundle.putString("text1", String.valueOf(addAnsweredId));
	
					msg.setData(bundle);
	
					myHandler.sendMessage(msg);
				}
				
				

				// ����url�����н���
				// webView.loadUrl(url);
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
	
	
	// thread for img upload  

	/*public class urlThread extends Thread {
	
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
					URL url = new URL(URLHelper.SERVER_URl + "/test/ajax/upload_answer_img/?answer_id="+addAnsweredId+"&user_id="+currentUserId);
					while (i < picPathList.size())
					{
						HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
						// ����ÿ�δ��������С��������Ч��ֹ�ֻ���Ϊ�ڴ治�����
						// �˷���������Ԥ�Ȳ�֪�����ݳ���ʱ����û�н����ڲ������ HTTP �������ĵ�����
						httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
						// �������������
						httpURLConnection.setDoInput(true);
						httpURLConnection.setDoOutput(true);
						httpURLConnection.setUseCaches(false);
						// ʹ��POST����
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
						// ��ȡ�ļ�
						while ((count = fis.read(buffer)) != -1) {
							dos.write(buffer, 0, count);
						}
						fis.close();
	
						dos.writeBytes(end);
						dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
						dos.flush();
						
						{
							Message msg = new Message();
							msg.what = 0x16;
			
							Bundle bundle = new Bundle();
							bundle.clear();
			
							bundle.putString("text4", "before read");
							// bundle.putString("text4", json.getString("value"));
			
							msg.setData(bundle);
			
							myHandler.sendMessage(msg);
						}
	
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
			
							bundle.putString("text4", result);
							// bundle.putString("text4", json.getString("value"));
			
							msg.setData(bundle);
			
							myHandler.sendMessage(msg);
						}
	
						//Toast.makeText(this, result, Toast.LENGTH_LONG).show();
						dos.close();
						is.close();
						i++;
					}

				} catch (Exception e) {
					e.printStackTrace();
					//setTitle(e.getMessage());
				}

		}
	 }
*/
	
	// thread for img upload  
	public class urlThread extends Thread {
	
		
		long requestTime;
		long responseTime = 0;
	
		private  String BOUNDARY ; // �߽��ʶ
		Map<String, String> param;
	
																				// �������
		private static final String PREFIX = "--";
		private static final String LINE_END = "\r\n";
		private static final String CONTENT_TYPE = "multipart/form-data"; // ��������
		private static final String TAG = "UploadUtil";
		private int readTimeOut = 10 * 1000; // ��ȡ��ʱ
		private int connectTimeout = 10 * 1000; // ��ʱʱ��
		/***
		 * ����ʹ�ö೤ʱ��
		 */
		private static final String CHARSET = "utf-8"; // ���ñ���

		/***
		 * �ϴ��ɹ�
		 */
		public static final int UPLOAD_SUCCESS_CODE = 1;
		/**
		 * �ļ�������
		 */
		public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
		/**
		 * ����������
		 */
		public static final int UPLOAD_SERVER_ERROR_CODE = 3;
		protected static final int WHAT_TO_UPLOAD = 1;
		protected static final int WHAT_UPLOAD_DONE = 2;

			public urlThread() {
			
				// TODO Auto-generated constructor stub
				BOUNDARY = UUID.randomUUID().toString();
				requestTime = System.currentTimeMillis();
				
				param = new HashMap<String, String>();
				
				param.put("answer_id", String.valueOf(addAnsweredId));
				param.put("user_id", String.valueOf(currentUserId));
			
			}

			@Override
			public void run() {
				String end = "\r\n";
				String twoHyphens = "--";
				String boundary = "******";
				String result ="";
			
			
				try {
					URL url = new URL(URLHelper.SERVER_URl + "/test/ajax/upload_answer_img/");
					for (int i = 0; i < picPathList.size();i++)
					{	
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setReadTimeout(readTimeOut);
						conn.setConnectTimeout(connectTimeout);
						conn.setDoInput(true); // ����������
						conn.setDoOutput(true); // ���������
						conn.setUseCaches(false); // ������ʹ�û���
						conn.setRequestMethod("POST"); // ����ʽ
						conn.setRequestProperty("Charset", CHARSET); // ���ñ���
						conn.setRequestProperty("connection", "keep-alive");
						conn.setRequestProperty("user-agent",
								"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
						conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
								+ BOUNDARY);
						// conn.setRequestProperty("Content-Type",
						// "application/x-www-form-urlencoded");

						/**
						 * ���ļ���Ϊ�գ����ļ���װ�����ϴ�
						 */
						DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
						StringBuffer sb = null;
						String params = "";

						/***
						 * �����������ϴ�����
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
						 * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ�
						 * filename���ļ������֣�������׺���� ����:abc.png
						 */
						
						sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
						sb.append("Content-Disposition:form-data; name=\"uploadedfile\"; filename=\"" + picPathList.get(i).substring(picPathList.get(i).lastIndexOf("/") + 1) + "\"" + LINE_END);
						sb.append("Content-Type:image/pjpeg" + LINE_END); // �������õ�Content-type����Ҫ��
																			// �����ڷ������˱���ļ������͵�
						sb.append(LINE_END);
						params = sb.toString();
						sb = null;

						dos.write(params.getBytes());
						/** �ϴ��ļ� */
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
						 * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ����
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
							//sendMessage(UPLOAD_SUCCESS_CODE, "�ϴ������" + result);
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
							//sendMessage(UPLOAD_SERVER_ERROR_CODE, "�ϴ�ʧ�ܣ�code=" + res);
							
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
