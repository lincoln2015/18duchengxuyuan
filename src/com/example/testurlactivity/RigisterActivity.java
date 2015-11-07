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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.example.testurlactivity.UserDetailEditActivity.urlThread;
import com.example.testurlactivity.UserDetailEditActivity.urlThreadGetImg;
import com.example.testurlactivity.UserLoginActivity.ApacheHttpThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;



public class RigisterActivity extends Activity {
	
	static InputStream is = null;
	static String json2 = "";
	
	String name ;
	String passwd;
	String email;
	
	
	EditText nameEditText;
	EditText passwdEditText;
	EditText emailEditText;
	
	
	RadioGroup sexRadioGroup;
	RadioButton girButton;
	RadioButton boyButton;
	
	int updateSex=1;
	
	
	ImageView userImg;
	String userAvatarFile = "";
	private String picPath = null;
	private static final String TAG = "uploadImage";
	
	int currentId;
	
	
	 public Handler myHandler = new Handler() {
	     @Override
	     public void handleMessage(Message msg) {
	        
	          
	         if (msg.what == 0x15) {
	     				Bundle bundle = msg.getData();
	     				String result =  bundle.getString("text1");

	     				if (result.equals("register sucess"))
	     				{
	     					
	     					if (picPath != null)
	     						new urlThread().start();
	     					else
	     					{
	     						Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
	     						
	     						finish();
	     		      		 	overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);  
	     					}
	     					
	     				}
	     				else
	     					Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
	     		
	                 
	         }
	         if (msg.what == 0x16) {
				
	        	Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
				finish();
      		 	overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);     
	        
	         }
	     }

	 };
	 
	//判断email格式是否正确
	 public boolean isEmail(String email) {
		 String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		 Pattern p = Pattern.compile(str);
		 Matcher m = p.matcher(email);
	
		 return m.matches();
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

	
	View.OnClickListener onClickListener = new View.OnClickListener() {
	    
	    public void onClick(View v) {

	    	if (v.getId() == R.id.user_img) {
	            
	      		// new ApacheHttpThreadUpdateEmailSetting().start();
	    		 
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 1);
			
	         	           
	       }
	    	if (v.getId() == R.id.back_ly) {

				finish();
				// 设置切换动画，从右边进入，左边退出
				overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
			}
	    	if (v.getId() == R.id.submit) {
	              
    		 	// finish();
    		// new a thread to send to server to check user lgoin 
    		name = nameEditText.getText().toString();
    	    passwd = passwdEditText.getText().toString();
    	    email = emailEditText.getText().toString();
    	    
    	    //check name & passwd
    	    if (name.equals(""))
    	    	Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
    	    else if (passwd.equals(""))
    	    	Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
    	    else if (email.equals(""))
    	    	Toast.makeText(getApplicationContext(), "请输入邮箱", Toast.LENGTH_SHORT).show();
    	    else
    	    {
    	    	if (!isEmail(email))
    	    		Toast.makeText(getApplicationContext(), "邮箱格式不正确", Toast.LENGTH_SHORT).show();
    	    	if  (passwd.length() < 4)
    	    		Toast.makeText(getApplicationContext(), "密码长度要大于4", Toast.LENGTH_SHORT).show();
    	    	else
    	    		new ApacheHttpThread().start();	
    	        
    	    }
         	           
         }

	    } 

	};
	
 	private void alert() {
 		Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
 				.setMessage("您选择的不是有效的图片")
 				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
 					public void onClick(DialogInterface dialog, int which) {
 						picPath = null;
 					}
 				}).create();
 		dialog.show();
 	}
 	

	   @Override
	 	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	 		if (resultCode == Activity.RESULT_OK) {
	 			/**
	 			 * 当选择的图片不为空的话，在获取到图片的途径
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
	 					 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
	 					 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
	 					 */
	 					if (path.endsWith("jpg") || path.endsWith("png")) {
	 						picPath = path;
	 						Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
	 						//bitMapList.add(bitmap);
	 						//picPathList.add(picPath);
	 		
	 						//answerContentEditText.insertDrawable(bitmap, 0, 0, "[attach]" + picPath.substring(picPath.lastIndexOf("/") + 1)+ "[/attach]");  
	 						userImg.setImageBitmap(bitmap);
	 						
	 						// new urlThread().start();
	 						
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


	public RigisterActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


		setContentView(R.layout.register);
		
		
		nameEditText = (EditText) findViewById(R.id.name);
		passwdEditText = (EditText) findViewById(R.id.passwd);
		emailEditText = (EditText) findViewById(R.id.email);
		
		girButton = (RadioButton) findViewById(R.id.girl);
		boyButton = (RadioButton) findViewById(R.id.boy);
		sexRadioGroup = (RadioGroup) findViewById(R.id.sex_group);
		sexRadioGroup.setOnCheckedChangeListener(radiogpchange);
		boyButton.setChecked(true);
		updateSex = 1;
		
		
		userImg = (ImageView) findViewById(R.id.user_img);
		userImg.setOnClickListener(onClickListener);
		//userImg.setVisibility(View.GONE);
		
		
	    LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		    
		
		Button submitBtn = (Button) findViewById(R.id.submit);
		submitBtn.setOnClickListener(onClickListener);
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/register_process2/");
				// 如果是Post提交可以将参数封装到集合中传递
				List dataList = new ArrayList();
				
				dataList.add(new BasicNameValuePair("sex", String.valueOf(updateSex)));
				dataList.add(new BasicNameValuePair("email", email));
				dataList.add(new BasicNameValuePair("user_name", name));
				dataList.add(new BasicNameValuePair("password", passwd));
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
				msg.what = 0x15;
				Bundle bundle = new Bundle();
				bundle.clear();
				
				if (json.getString("login_result").equals("failed"))
					bundle.putString("text1", json.getString("reason"));
				else if (json.getString("login_result").equals("sucess"))
				{
					bundle.putString("text1", "register sucess");
					currentId = json.getInt("user_id");
				}
					
				msg.setData(bundle);
				myHandler.sendMessage(msg);
  
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
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
					// 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
					// 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
					httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
					// 允许输入输出流
					httpURLConnection.setDoInput(true);
					httpURLConnection.setDoOutput(true);
					httpURLConnection.setUseCaches(false);
					// 使用POST方法
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
					// 读取文件
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

				/*BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();

				JSONObject json = new JSONObject(sb.toString());
*/
					if (true) {
						Message msg = new Message();
						msg.what = 0x16;

						Bundle bundle = new Bundle();
						bundle.clear();

						 bundle.putString("text4", result);
						//bundle.putString("text4", json.getString("value"));

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


}
