
package com.example.testurlactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
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
import org.json.JSONObject;








import com.example.testurlactivity.AddAnwserActivity.ApacheHttpThread;
import com.example.testurlactivity.UserDetailEditActivity.urlThreadGetImg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class AnswerContentDetailActivity extends Activity  implements OnTouchListener{
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

	QuestionAnswer questionAnswer;

	int currentUserId;
	int inviteUserId;
	int userID;
	String currentUserName;

	User user;

	int answerID;
	String answerType;

	static InputStream is = null;
	static String json2 = "";

	Activity thisActivity;
	private View mRootView;

	String questionID;

	SharedPreferences sharedPreferences;

	TextView questonContentTxt;
	TextView userNameTxt;
	TextView agreeCountTxt;
	TextView anserContentTxt;

	private ImageView imgeview;

	ArrayList<Bitmap> bitMapList;

	ArrayList<String> picUrlList;

	int flag = 0;

	String answerContent;

	int indexBitmapList = 0;

	ImageView userImg;

	String userAvatarFile = "";

	private Bitmap bitmap = null;
	
	boolean isAnswerThanks = false;
	
	boolean isAnswerVote = false;
	int voteValue = 0;
	
	
	// �Զ���ĵ�������
	AnswerOperationPopupwindow menuWindow = null;
		
		public Handler myHandler = new Handler() {
		     @Override
		     public void handleMessage(Message msg) {
		    	 
		    	 if (msg.what == 0x19) {
						Bundle bundle = msg.getData();

						// showTextView.setText(bundle.getString("text2"))
							if (bundle.getString("text3").equals("add"))
							{
								// Toast.makeText(getApplicationContext(), "thanks sucess", Toast.LENGTH_SHORT).show();
								isAnswerThanks = true;
								menuWindow.setIsAnswerThanksState(getApplicationContext(), isAnswerThanks);
							}
							else
								Toast.makeText(getApplicationContext(), "thanks failed", Toast.LENGTH_SHORT).show();
			         }
		       	 if (msg.what == 0x20) {
						Bundle bundle = msg.getData();

						// showTextView.setText(bundle.getString("text2"))
							if (bundle.getString("text3").equals("agree"))
							{
								// Toast.makeText(getApplicationContext(), "agree sucess", Toast.LENGTH_SHORT).show();
								
								isAnswerVote = true;
								voteValue = 1;
								menuWindow.setIsAnswerVoteState(getApplicationContext(),isAnswerVote ,voteValue);
							}
							else if (bundle.getString("text3").equals("disagree"))
							{
								// Toast.makeText(getApplicationContext(), "disagree sucess", Toast.LENGTH_SHORT).show();
								
								isAnswerVote = true;
								voteValue = -1;
								menuWindow.setIsAnswerVoteState(getApplicationContext(),isAnswerVote ,voteValue);
							}
			         }
		
		    	 
		    	 if (msg.what == 0x18) {
						Bundle bundle = msg.getData();

						// showTextView.setText(bundle.getString("text2"))
				
						userImg.setImageBitmap(bitmap);
						
						userImg.setVisibility(View.VISIBLE);
			
			         }
		    	 
		    			       
		         if (msg.what == 0x17) {
		        	 
						Bundle bundle = msg.getData();
		        	 
		        		questonContentTxt.setText( questionAnswer.getQuestionContent());
						userNameTxt.setText(questionAnswer.getUserName());
						agreeCountTxt.setText(String.valueOf(questionAnswer.getAgreeCount()));
						
						// should parse the content img tag and then get local img replace the img tag 
						// 
						answerContent = questionAnswer.getAnserContent();
						
						RegexUtil regexUtil = new RegexUtil();
						
						picUrlList = new ArrayList<String>();
						
						answerContent = regexUtil.geTtextOfHtml(answerContent, picUrlList);
						Log.d(answerContent, answerContent);
					    
						int i = 0;  
						while ( i < picUrlList.size())
						{
							picUrlList.set(i, picUrlList.get(i).replace("localhost", URLHelper.IP_URL));
							i++;
						}
						new  urlThread(picUrlList).start();			
					
						userAvatarFile = user.getAvatarFile();
						userAvatarFile = userAvatarFile.replace("localhost", URLHelper.IP_URL);
						
						// Toast.makeText(getApplicationContext(), userAvatarFile, Toast.LENGTH_SHORT).show();
						
						if (userAvatarFile != "" && userAvatarFile !="null")
							new urlThreadGetImg().start();
						else
							userImg.setVisibility(View.VISIBLE);
						
						
						 
		         }
		         
		         if (msg.what == 0x14) {
				
					// Toast.makeText(getApplicationContext(), "ox14 message recv", Toast.LENGTH_SHORT).show(); 
					
					
					 CharSequence html = Html.fromHtml(answerContent,new ImageGetter()
				        {
				    
				      @Override
						public Drawable getDrawable(String source) {
				    	  
							if (true) {
							
								// Toast.makeText(getApplicationContext(), source, Toast.LENGTH_SHORT).show();		
								BitmapDrawable bitDrawable = new BitmapDrawable(bitMapList.get(indexBitmapList));
								indexBitmapList++;
								bitDrawable.setBounds(0, 0,	bitDrawable.getIntrinsicHeight() + 300, bitDrawable.getIntrinsicHeight() +300);

								return bitDrawable;
							} else {
								// ���ϵͳ��Դ��ͼƬ��
								// Bitmap bitmap = null;
								Drawable drawable = getResources().getDrawable(getResourceId(source));
							

								drawable.setBounds(0, 0, drawable.getIntrinsicHeight(),	drawable.getIntrinsicHeight());

								// ���ﻹ���Զ���Դ����һ���Ĵ���
								return drawable;
							}
						}
					}, null);

					anserContentTxt.setText(html);
					
		         }
		
		
		        
		     }

		 };
		 
		 
	public int getResourceId(String name) {
		try {
			// ������Դ��ID�ı��������Field�Ķ��� ʹ�÷��������ʵ�ֵ�
			java.lang.reflect.Field field = R.drawable.class.getField(name);
			// ȡ�ò�������Դ��id���ֶΣ���̬��������ֵ,ʹ�÷������
			return Integer.parseInt(field.get(null).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
		
	View.OnClickListener onClickListener = new View.OnClickListener() {
		    
	        public void onClick(View v) {

	        	if (v.getId() == R.id.cancel) { 
	        		 	 finish();	           
	              }
	             if (v.getId() == R.id.back_ly) {
		 				// new a thread to update the qusetion item foucu_count
		 				finish();
		 				// �����л����������ұ߽��룬����˳�
		 				overridePendingTransition(R.anim.in_from_left,	R.anim.out_to_right);
	 			}
	        	 if (v.getId() == R.id.share_ly) {
		 				
	            	 Toast.makeText(getApplication(),  getString(R.string.version_not_support) +"����", Toast.LENGTH_SHORT).show();
		 		 }
	             
	        }

	    };
	    
	 // Ϊ��������ʵ�ּ�����
		private OnClickListener itemsOnClick = new OnClickListener() {
			public void onClick(View v) {
				// menuWindow.dismiss();

	             if (v.getId() == R.id.add_to_favor_ly) {
	            	 
	            	
	            	   menuWindow.setFavorTouch(getApplicationContext(),R.drawable.shouchang_blu_icon_white_background);
			            	 
						Intent intent = new Intent(getApplicationContext(),
								AddAnswerToFavorTagActivity.class);
		
						Bundle bundle = new Bundle();
						bundle.putString("answer_id", String.valueOf(answerID));
						intent.putExtras(bundle);
						startActivity(intent);
						overridePendingTransition(R.anim.in_from_right,
								R.anim.out_to_left);
						
						//menuWindow.setFavorTouch(getApplicationContext(),R.drawable.shouchang_grey_icon_white_backgrund);
	                }

	             if (v.getId() == R.id.commentLy) {
	            	 
	            	 	menuWindow.setCommentTouch(getApplicationContext(),R.drawable.comment_blu_icon_white_background);
	         
				 		Intent intent = new Intent(getApplicationContext(),AnswerCommentsShowActivity.class);
				 		Bundle bundle = new Bundle();
		            	bundle.putString("answer_id", String.valueOf(answerID));
		            	intent.putExtras(bundle);
				 		startActivity(intent);
				 		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				 		
				 		//menuWindow.setCommentTouch(getApplicationContext(),R.drawable.comment_grey_icon_white_background);
	                }
	               if (v.getId() == R.id.agree_ly) {
	            	 	new ApacheHttpThreadAgree(1).start();
		 			}
	             if (v.getId() == R.id.disagree_ly) {
	            	 new ApacheHttpThreadAgree(-1).start();
		 			}
	             if (v.getId() == R.id.thanks_ly) {
	            	 	new ApacheHttpThreadThanks().start();
		 			}
	         
	             
	        
			}
		};
		

	public AnswerContentDetailActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.answer_content_detail);
		
		
		Intent intent = getIntent();
		answerID = Integer.valueOf(intent.getStringExtra("answer_id"));
		
		questonContentTxt = (TextView) findViewById(R.id.queston_content);
		userNameTxt = (TextView) findViewById(R.id.answer_user_name);
		agreeCountTxt = (TextView) findViewById(R.id.agree_count);
		anserContentTxt = (TextView) findViewById(R.id.anser_content);
		
		anserContentTxt.setMovementMethod(ScrollingMovementMethod.getInstance());
		anserContentTxt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ʵ����SelectPicPopupWindow
				menuWindow = new AnswerOperationPopupwindow(AnswerContentDetailActivity.this,
						itemsOnClick);
				
				menuWindow.setIsAnswerThanksState(getApplicationContext(), isAnswerThanks);

				menuWindow.setIsAnswerVoteState(getApplicationContext(),isAnswerVote ,voteValue);
			
				// ��ʾ����
				menuWindow.showAtLocation(
						AnswerContentDetailActivity.this.findViewById(R.id.bottom_main),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
			}
		});
	
				
	    SharedPreferences sharedPreferences = this.getSharedPreferences("ljq123", Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	    currentUserId = sharedPreferences.getInt("user_id", 1);
        
        currentUserName = sharedPreferences.getString("name", "");
        
        
        userImg = (ImageView) findViewById(R.id.user_img);
		userImg.setVisibility(View.GONE);
		
		/*LinearLayout ll = (LinearLayout) findViewById(R.id.answer_content_ly);
		ll.setOnTouchListener(this);*/
		
		LinearLayout backLy = (LinearLayout) findViewById(R.id.back_ly);
		backLy.setOnClickListener(onClickListener);
		LinearLayout shareLy = (LinearLayout) findViewById(R.id.share_ly);
		shareLy.setOnClickListener(onClickListener);
		

        new ApacheHttpThread().start();
        new ApacheHttpThreadGetThanks().start();
        new ApacheHttpThreadGetVote().start();
        
        
       //  new ApacheHttpThreadGetUser().start();
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
		
		if (menuWindow != null)
		{
			menuWindow.setFavorTouch(getApplicationContext(),R.drawable.shouchang_grey_icon_white_backgrund);
			
			menuWindow.setCommentTouch(getApplicationContext(),R.drawable.comment_grey_icon_white_background);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	public class ApacheHttpThread extends Thread {
		String strs = "";
		int len;
		  JSONObject json;
	
		  Iterator it ;
		  String searchQuestionName;
		public ApacheHttpThread() {
			// TODO Auto-generated constructor stub
			
		}

		@Override
		public void run() {
			try {


				HttpClient client = new DefaultHttpClient();
				
				 HttpGet httpGet = new HttpGet(URLHelper.SERVER_URl + "/test/ajax/get_answer_info/?answer_id="+answerID);
			
				HttpResponse httpResponse = client.execute(httpGet);
				// ��ȡ��Ϣ����
				HttpEntity entity = httpResponse.getEntity();
				 
				is = entity.getContent();     
				
	
				
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
		            
		        
					JSONObject json = new JSONObject(json2);
					if (json.has("value")) {

						questionAnswer = new QuestionAnswer(new JSONObject(json.getString("value")));
						
						user = questionAnswer.getPulishAnswerUser();

					}

					Message msg = new Message();
					msg.what = 0x17;

					Bundle bundle = new Bundle();
					bundle.clear();

					// bundle.putString("recv_server", new
					// String(buffer));
					bundle.putString("text3", String.valueOf(answerID));

					msg.setData(bundle);

					myHandler.sendMessage(msg);
						
					
		          
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		            Log.d("json", json2.toString());
		        }
	
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
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
			
				URL url = new URL(userAvatarFile);
				InputStream is = url.openStream();
				bitmap = BitmapFactory.decodeStream(is);

				Message msg = new Message();
				msg.what = 0x18;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
		
	
	  
	   
  public class urlThread extends Thread {
		   
		   ArrayList<String> urlList;

			public urlThread(ArrayList<String> picUrlList) {
				// TODO Auto-generated constructor stub
				urlList = picUrlList;
				
				bitMapList = new  ArrayList<Bitmap>();
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

				while (i < urlList.size()) {
					URL url = new URL(urlList.get(i));
					InputStream is = url.openStream();
					bitmap = BitmapFactory.decodeStream(is);

					if (bitmap != null) {
						bitMapList.add(bitmap);

					}
					i++;
				}
				Message msg = new Message();
				msg.what = 0x14;

				myHandler.sendMessage(msg);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		}
  
  public class ApacheHttpThreadGetVote extends Thread {
		String strs = "";

		public ApacheHttpThreadGetVote() {
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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/get_answer_vote/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// �����Post�ύ���Խ�������װ�������д���
				List dataList = new ArrayList();
	
				dataList.add(new BasicNameValuePair("answer_id", String.valueOf(answerID) ));
				dataList.add(new BasicNameValuePair("current_uid", String.valueOf(currentUserId)));	
				
				// UrlEncodedFormEntity���ڽ�����ת��ΪEntity����
				httpPost.setEntity(new UrlEncodedFormEntity(dataList));
				// ��ȡ��Ӧ��Ϣ
				HttpResponse httpResponse = client.execute(httpPost);
				// ��ȡ��Ϣ����
				HttpEntity entity = httpResponse.getEntity();
				// ����Ϣ����ֱ��ת��Ϊ�ַ���
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				is = entity.getContent();     
				
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
		            
		            
		            JSONObject json = new JSONObject(json2); 
			       
		           /* Message msg = new Message();
					msg.what = 0x19;
					Bundle bundle = new Bundle();
					bundle.clear();
					bundle.putString("text3", json.getString("value"));				
					msg.setData(bundle);

					myHandler.sendMessage(msg);
		            */
		            
		            if (json.getString("alread_vote").equals("yes"))
		            {
		            	isAnswerVote = true;
		            	
		            	JSONObject subJson = new JSONObject(json.getString("vote_info")); 
		            	
		            	voteValue = subJson.getInt("vote_value");
		            }
		            else if (json.getString("alread_vote").equals("no"))
		            	isAnswerVote = false;
		            
		          
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		            Log.d("json", json2.toString());
		        }
		
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
  
  public class ApacheHttpThreadGetThanks extends Thread {
		String strs = "";

		public ApacheHttpThreadGetThanks() {
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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/get_answer_thanks/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// �����Post�ύ���Խ�������װ�������д���
				List dataList = new ArrayList();
	
				dataList.add(new BasicNameValuePair("answer_id", String.valueOf(answerID) ));
				dataList.add(new BasicNameValuePair("current_uid", String.valueOf(currentUserId)));
				dataList.add(new BasicNameValuePair("type", "thanks"));
				
				
				// UrlEncodedFormEntity���ڽ�����ת��ΪEntity����
				httpPost.setEntity(new UrlEncodedFormEntity(dataList));
				// ��ȡ��Ӧ��Ϣ
				HttpResponse httpResponse = client.execute(httpPost);
				// ��ȡ��Ϣ����
				HttpEntity entity = httpResponse.getEntity();
				// ����Ϣ����ֱ��ת��Ϊ�ַ���
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				is = entity.getContent();     
				
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
		            
		            
		            JSONObject json = new JSONObject(json2); 
			       
		           /* Message msg = new Message();
					msg.what = 0x19;
					Bundle bundle = new Bundle();
					bundle.clear();
					bundle.putString("text3", json.getString("value"));				
					msg.setData(bundle);

					myHandler.sendMessage(msg);
		            */
		            
		            if (json.getString("value").equals("already_thanks"))
		            	isAnswerThanks = true;
		            else if (json.getString("value").equals("no_thanks"))
		            	isAnswerThanks = false;
		            
		          
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		            Log.d("json", json2.toString());
		        }
		
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
  
  public class ApacheHttpThreadThanks extends Thread {
		String strs = "";

		public ApacheHttpThreadThanks() {
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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/question_answer_rate/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// �����Post�ύ���Խ�������װ�������д���
				List dataList = new ArrayList();
	
				dataList.add(new BasicNameValuePair("answer_id", String.valueOf(answerID) ));
				dataList.add(new BasicNameValuePair("current_uid", String.valueOf(currentUserId)));
				dataList.add(new BasicNameValuePair("type", "thanks"));
				dataList.add(new BasicNameValuePair("user_name", currentUserName ));
				
				// UrlEncodedFormEntity���ڽ�����ת��ΪEntity����
				httpPost.setEntity(new UrlEncodedFormEntity(dataList));
				// ��ȡ��Ӧ��Ϣ
				HttpResponse httpResponse = client.execute(httpPost);
				// ��ȡ��Ϣ����
				HttpEntity entity = httpResponse.getEntity();
				// ����Ϣ����ֱ��ת��Ϊ�ַ���
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				is = entity.getContent();     
				
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
		            
		            
		            JSONObject json = new JSONObject(json2); 
			       
		            Message msg = new Message();
					msg.what = 0x19;
					Bundle bundle = new Bundle();
					bundle.clear();
					bundle.putString("text3", json.getString("value"));				
					msg.setData(bundle);

					myHandler.sendMessage(msg);

		          
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		            Log.d("json", json2.toString());
		        }
		
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
	
  
  public class ApacheHttpThreadAgree extends Thread {
		String strs = "";
		
		int agree_value;

		public ApacheHttpThreadAgree(int value) {
			// TODO Auto-generated constructor stub
			agree_value = value;
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
				HttpPost httpPost = new HttpPost(URLHelper.SERVER_URl +"/test/ajax/answer_vote/");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/wecenter2/?/test/ajax/list");
				// HttpPost httpPost = new HttpPost("http://192.168.0.104/testlogin/index.php");
				// �����Post�ύ���Խ�������װ�������д���
				List dataList = new ArrayList();
	
				dataList.add(new BasicNameValuePair("answer_id", String.valueOf(answerID) ));
				dataList.add(new BasicNameValuePair("current_uid", String.valueOf(currentUserId)));
				dataList.add(new BasicNameValuePair("value", String.valueOf(agree_value)));
		
				
				// UrlEncodedFormEntity���ڽ�����ת��ΪEntity����
				httpPost.setEntity(new UrlEncodedFormEntity(dataList));
				// ��ȡ��Ӧ��Ϣ
				HttpResponse httpResponse = client.execute(httpPost);
				// ��ȡ��Ϣ����
				HttpEntity entity = httpResponse.getEntity();
				// ����Ϣ����ֱ��ת��Ϊ�ַ���
				// String content = EntityUtils.toString(entity);
				// showTextView.setText(content);
				is = entity.getContent();     
				
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
		            
		            
		            JSONObject json = new JSONObject(json2); 
			       
		            Message msg = new Message();
					msg.what = 0x20;
					Bundle bundle = new Bundle();
					bundle.clear();
					bundle.putString("text3", json.getString("value"));				
					msg.setData(bundle);

					myHandler.sendMessage(msg);

		          
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		            Log.d("json", json2.toString());
		        }
		
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

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
