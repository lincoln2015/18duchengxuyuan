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
import java.util.List;

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
import org.json.JSONObject;

import com.example.testurlactivity.AccountEmailSettingActivity.ApacheHttpThreadUpdateEmailSetting;
import com.example.testurlactivity.NotificationSetttingActivity.ApacheHttpThread;

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
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserDetailEditActivity extends Activity  implements OnTouchListener{
	
	// ��ָ���һ���ʱ����С�ٶ�
	private static final int XSPEED_MIN = 200;
	// ��ָ���һ���ʱ����С����
	private static final int XDISTANCE_MIN = 150;
	// ��¼��ָ����ʱ�ĺ����ꡣ
	private float xDown;
	// ��¼��ָ�ƶ�ʱ�ĺ����ꡣ
	private float xMove;
	// ���ڼ�����ָ�������ٶȡ�
	private VelocityTracker mVelocityTracker;
	
	
	int currentId;
	
	
	static InputStream is = null;
	static String json2 = "";

	private String questionID;

	User user;

	EditText userNameEdit;

	EditText userIntroduceEdit;
	
	EditText userEmailEdit;
	
	EditText userDepartmentEdit;

	RadioGroup sexRadioGroup;
	
	
	RadioButton girButton;
	RadioButton boyButton;

	String updateUserName;

	String updateSignature;
	
	String updateEmail;
	
	int updateSex=1;
	
	
	private String uploadFile = "/sdcard/testimg.jpg";
	private String srcPath = "/sdcard/testimg.jpg";
	private String srcPathDir = "/sdcard/";
	// �������Ͻ����ļ��Ĵ���ҳ�棬���������Ҫ�����Լ���
	private String actionUrl = "http://192.168.42.183/wecenter/recv_img.php";
	
	
	ArrayList<String> picNameList;
	
	ArrayList<String> picPathList;
	
	 ArrayList<Bitmap> bitMapList;
	 
	 String picPathWillUpload = "";


	int addAnsweredId;

	private String picPath = null;

	String result;
	private static final String TAG = "uploadImage";

	ImageView userImg;

	String userAvatarFile = "";

	private Bitmap bitmap = null;

	int USER_EDIT_DETAIL_START_REQUEST = 1006;
	int USER_EDIT_DETAIL_START_END = 1007;
	

	 
	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	    
	         if (msg.what == 0x17) {
				Bundle bundle = msg.getData();

				// showTextView.setText(bundle.getString("text2"))
		
				userImg.setImageBitmap(bitmap);
				
				userImg.setVisibility(View.VISIBLE);

		
	         }
	         if (msg.what == 0x16) {
					Bundle bundle = msg.getData();
					
					userNameEdit.setText(user.getUserName());
					
					userIntroduceEdit.setText(user.getSignature());
					
					userEmailEdit.setText(user.getEmail());
					
					
					if (user.getSex() == "��")
						boyButton.setChecked(true);
					else
						girButton.setChecked(true);
					
					userAvatarFile = user.getAvatarFile();
					userAvatarFile = userAvatarFile.replace("localhost", URLHelper.IP_URL);
					
					if (userAvatarFile != "" && userAvatarFile != "null")
						new urlThreadGetImg().start();
					else
						userImg.setVisibility(View.VISIBLE);
					
					
					// Toast.makeText(getApplicationContext(), userAvatarFile, Toast.LENGTH_SHORT).show();
	         }
	         
	    if (msg.what == 0x19) {
					Bundle bundle = msg.getData();
					
					//showTextView.setText(bundle.getString("text3"));

					// Toast.makeText(getApplicationContext(), bundle.getString("text3") , Toast.LENGTH_LONG).show();
			
	          
	  }
	  
	         
	      
	        
	     }

	 };

	
	 View.OnClickListener onClickListener = new View.OnClickListener() {
	    
       public void onClick(View v) {
   
       if (v.getId() == R.id.answer_count) {
               
   		 	// finish();
	    		// new a thread to send to server to check  lgoin 
       		 finish();
          	           
        }
       if (v.getId() == R.id.back_ly) {
           
    		// new a thread to update the qusetion item foucu_count
			finish();
			// �����л����������ұ߽��룬����˳�
			overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
         	           
       }


       if (v.getId() == R.id.submit_ly) {
               
       		// new ApacheHttpThreadUpdateEmailSetting().start();
    	    updateUserName = userNameEdit.getText().toString();
    		
			updateSignature = userIntroduceEdit.getText().toString();
		
			updateEmail = userEmailEdit.getText().toString();
			
			new  ApacheHttpThreadUpdateProfileSetting().start();
			
			
            Intent intent = new Intent();
            UserDetailEditActivity.this.setResult(USER_EDIT_DETAIL_START_END,intent);
         	 /*������Activity*/
            UserDetailEditActivity.this.finish();
			
       		// finish();
          	           
        }
       if (v.getId() == R.id.user_img) {
           
      		// new ApacheHttpThreadUpdateEmailSetting().start();
    		 
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, 1);
		
         	           
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
 						//bitMapList.add(bitmap);
 						//picPathList.add(picPath);
 		
 						//answerContentEditText.insertDrawable(bitmap, 0, 0, "[attach]" + picPath.substring(picPath.lastIndexOf("/") + 1)+ "[/attach]");  
 						userImg.setImageBitmap(bitmap);
 						
 						new urlThread().start();
 						
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

		setContentView(R.layout.user_detail_edit);

		Intent intent = getIntent();

		SharedPreferences sharedPreferences = getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
		currentId = sharedPreferences.getInt("user_id", 1);

		new ApacheHttpThread().start();
		
		
		View submitLy = (View) findViewById(R.id.submit_ly);

		submitLy.setOnClickListener(onClickListener);
		
		
		userNameEdit = (EditText) findViewById(R.id.user_name_edit);
		
		userIntroduceEdit = (EditText) findViewById(R.id.user_introduce);
		
		userEmailEdit = (EditText) findViewById(R.id.user_email);
		
		userDepartmentEdit = (EditText) findViewById(R.id.user_department);
		
		
		userImg = (ImageView) findViewById(R.id.user_img);
		userImg.setOnClickListener(onClickListener);
		userImg.setVisibility(View.GONE);
		
		girButton = (RadioButton) findViewById(R.id.girl);
		boyButton = (RadioButton) findViewById(R.id.boy);
		
		sexRadioGroup = (RadioGroup) findViewById(R.id.sex_group);
		
		sexRadioGroup.setOnCheckedChangeListener(radiogpchange);
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.user_detail_ly);
		ll.setOnTouchListener(this);

		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		
	
	}
	
	public RadioGroup.OnCheckedChangeListener radiogpchange = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			
			if (checkedId == girButton.getId())
			{
				// Toast.makeText(getApplicationContext(), "girl click", Toast.LENGTH_SHORT).show();
				updateSex = 2;
				
			}
			else if (checkedId == boyButton.getId())
			{
				// Toast.makeText(getApplicationContext(), "boy click", Toast.LENGTH_SHORT).show();
				updateSex = 1;
			}
			
		}
	};

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
		  JSONObject json;

		public ApacheHttpThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {
			 
				HttpClient client = new DefaultHttpClient();
				
				// may be save in localcondition would be better ,need to be consider deeply
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_current_user_info/?user_id="+currentId);
				
				// ��ȡ��Ӧ��Ϣ
				HttpResponse httpResponse = client.execute(httpGet);
				// ��ȡ��Ϣ����
				HttpEntity entity = httpResponse.getEntity();
				 
				is = entity.getContent();     
				
				
				/////////////////////*************////////////////////
				
				try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(
		                    is, "UTF-8"));
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            is.close();
		            json2 = sb.toString();
		            
		        
		          // JSONArray json = new JSONObject(sb.toString()).getJSONArray("value"); 
		         json = new JSONObject(new JSONObject(sb.toString()).getString("value")); 
		         user  = new User(json);
		
				Message msg = new Message();
				msg.what = 0x16;

				Bundle bundle = new Bundle();
				bundle.clear();

				// bundle.putString("recv_server", new String(buffer));
				 bundle.putString("text1", json2);
				// bundle.putString("text1", content);

				msg.setData(bundle);

				myHandler.sendMessage(msg);


				// ����url�����н���
				// webView.loadUrl(url);
			} catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	            Log.d("json", json2.toString());
	        }
			
			
			/////////////////////*************////////////////////

			
		
			
			
			// ����url�����н���
			// webView.loadUrl(url);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		}
	
	}
	

	public class ApacheHttpThreadUpdateProfileSetting extends Thread {
		String strs = "";

		public ApacheHttpThreadUpdateProfileSetting() {
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
				// HttpPost httpPost = new
				// HttpPost("http://192.168.0.104/wecenter2/?/account/ajax/login_process/");
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl + "/test/ajax/profile_setting/");
				HttpResponse httpResponse = null;
				// HttpPost httpPost = new
				// HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new
				// HttpPost("http://192.168.0.104/testlogin/index.php");
				// �����Post�ύ���Խ�������װ�������д���
				
	
				String keyStr = "";
				
			
				
				List dataList = new ArrayList();

				dataList.add(new BasicNameValuePair("user_name", updateUserName));
				dataList.add(new BasicNameValuePair("current_uid", String.valueOf(currentId)));
				dataList.add(new BasicNameValuePair("signature", updateSignature));
				dataList.add(new BasicNameValuePair("user_email", updateEmail));
				
				dataList.add(new BasicNameValuePair("user_sex", String.valueOf(updateSex)));
				
				
				// UrlEncodedFormEntity���ڽ�����ת��ΪEntity����
				httpPost.setEntity(new UrlEncodedFormEntity(dataList, HTTP.UTF_8));
				// ��ȡ��Ӧ��Ϣ
				httpResponse = client.execute(httpPost);

				// ��ȡ��Ϣ����
				HttpEntity entity = httpResponse.getEntity();

				is = entity.getContent();

				new ApacheHttpThread().start();
				// ///////////////////*************////////////////////

				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					json2 = sb.toString();

					JSONObject json = new JSONObject(json2);

					Message msg = new Message();
					msg.what = 0x19;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new String(buffer));
					bundle.putString("text3", json.toString());

					msg.setData(bundle);

					myHandler.sendMessage(msg);

				} catch (Exception e) {
					Log.e("Buffer Error",
							"Error converting result " + e.toString());
					Log.d("json", json2.toString());
				}

				// ����url�����н���
				// webView.loadUrl(url);
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
	
	
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
				URL url = new URL(URLHelper.SERVER_URl	+ "/test/ajax/avatar_upload_2/?user_id=" + currentId);

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
				httpURLConnection
						.setRequestProperty("Connection", "Keep-Alive");
				httpURLConnection.setRequestProperty("Charset", "UTF-8");
				httpURLConnection.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);

				DataOutputStream dos = new DataOutputStream(
						httpURLConnection.getOutputStream());
				dos.writeBytes(twoHyphens + boundary + end);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""	+ picPath.substring(picPath.lastIndexOf("/") + 1)
						+ "\""
						+ end);
				dos.writeBytes(end);

				// get the pic file by picNameList pic name
				// FileInputStream fis = new
				// FileInputStream(picNameList.get(i));
				FileInputStream fis = new FileInputStream(picPath);
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

				InputStream is = httpURLConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String result = br.readLine();

				/*
				 * BufferedReader reader = new BufferedReader( new
				 * InputStreamReader(is, "UTF-8")); StringBuilder sb = new
				 * StringBuilder(); String line = null; while ((line =
				 * reader.readLine()) != null) { sb.append(line + "\n"); }
				 * is.close();
				 * 
				 * 
				 * JSONObject json = new JSONObject(sb.toString());
				 */

				if (true) {
					Message msg = new Message();
					msg.what = 0x16;

					Bundle bundle = new Bundle();
					bundle.clear();

					bundle.putString("text4", result);
					// bundle.putString("text4", json.getString("value"));

					msg.setData(bundle);

					myHandler.sendMessage(msg);
				}

				// Toast.makeText(this, result, Toast.LENGTH_LONG).show();
				dos.close();
				is.close();

			} catch (Exception e) {
				e.printStackTrace();
				// setTitle(e.getMessage());
			}

		}
	}
	
	
	 
	   public class urlThreadGetImg extends Thread {
		   
		

			public urlThreadGetImg() {
				// TODO Auto-generated constructor stub
			
			}

			@Override
			public void run() {
			// TODO Auto-generated method stub
			// super.run();
			int i = 0;
			try {
				// URL url = new
				// URL("http://192.168.1.100:8080/myweb/image.jpg");

				// URL url = new
				// URL("http://ecshopxax.sinaapp.com/favicon.ico");
				
				

				URL url = new URL(userAvatarFile);
				InputStream is = url.openStream();
				bitmap = BitmapFactory.decodeStream(is);

				Message msg = new Message();
				msg.what = 0x17;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
		

		// ת����˵��������http://blog.csdn.net/ff20081528/article/details/17845753
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				createVelocityTracker(event);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					xDown = event.getRawX();
					Log.d("@@down", "xdown:"+xDown);
					break;
				case MotionEvent.ACTION_MOVE:
					xMove = event.getRawX();
					//��ľ���
					int distanceX = (int) (xMove - xDown);
					//��ȡ˳ʱ�ٶ�
					int xSpeed = getScrollVelocity();
					//�������ľ�����������趨����С�����һ�����˲���ٶȴ��������趨���ٶ�ʱ�����ص���һ��activity
					Log.d("@@move", "xmove:"+xMove +",speed :"+xSpeed);
					if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
						finish();
						//�����л����������ұ߽��룬����˳�
						overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.d("@@up", "xDown:"+xDown+ ",xMove ="+xMove);
					recycleVelocityTracker();
					break;
				default:
					break;
				}
				return true;
			}
			
			/**
			 * ����VelocityTracker���󣬲�������content����Ļ����¼����뵽VelocityTracker���С�
			 * 
			 * @param event
			 *        
			 */
			private void createVelocityTracker(MotionEvent event) {
				if (mVelocityTracker == null) {
					mVelocityTracker = VelocityTracker.obtain();
				}
				mVelocityTracker.addMovement(event);
			}
			
			/**
			 * ����VelocityTracker����
			 */
			private void recycleVelocityTracker() {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			
			/**
			 * ��ȡ��ָ��content���滬�����ٶȡ�
			 * 
			 * @return �����ٶȣ���ÿ�����ƶ��˶�������ֵΪ��λ��
			 */
			private int getScrollVelocity() {
				mVelocityTracker.computeCurrentVelocity(1000);
				int velocity = (int) mVelocityTracker.getXVelocity();
				return Math.abs(velocity);
			}

}
