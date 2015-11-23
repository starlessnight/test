package ericfo.isogon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import ericfo.isogon.R;

import ericfo.isogon.Login_member.sendPostRunnable;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
	public class Activity_prodchange extends Activity implements OnClickListener{
		private Context mContext;
		private Button changeCK, cancel;

		private EditText CGquantity, Passwd, UserMember, Phone, Cellphone, Address;

		private RadioButton rd1, rd2;
		private RadioGroup sex;
		private String gender;
		private String quantity;
		private CheckBox PDcancel;
		private String urlApi = "http://211.72.86.193/Isogon_M/main/ShoppingCart.aspx";
		private boolean TF,CKcancel;
		private String PHPSESSID="";
		private static String __VIEWSTATE;
		private static String __EVENTVALIDATION;
		private static String Item;
		protected static final int REFRESH_DATA = 0x00000001;
		//等待網路資料的抓取完成
		Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				// 顯示網路上抓取的資料
				case REFRESH_DATA:
					String result = null;
					if (msg.obj instanceof String)
						result = (String) msg.obj;
					if (result != null)
						// 印出網路回傳的文字
						//Toast.makeText(Activity_addmember.this, result,
								//Toast.LENGTH_LONG).show();
					break;
				}
			}
		};
			
			
			

			
				
				//主程式的執行
				@Override
				protected void onCreate(Bundle savedInstanceState) {
					
					super.onCreate(savedInstanceState);
					setContentView(R.layout.activity_prodchange);
					
					Bundle bundle = this.getIntent().getExtras();
					__VIEWSTATE = bundle.getString("__VIEWSTATE");
					__EVENTVALIDATION = bundle.getString("__EVENTVALIDATION");
					Item = bundle.getString("Item");
					SharedPreferences MemCookie=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
					PHPSESSID=MemCookie.getString("Cookie",null);
					Button n1=(Button)findViewById(R.id.changeCK);
					n1.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							
							Intent intent = new Intent();
			        		intent.setClass(Activity_prodchange.this , Activity_prodclass4.class );
			        		
			        		startActivity(intent);
			        		Activity_prodchange.this.finish();
						}
						});

					this.mContext = getApplicationContext();
					this.PDcancel=(CheckBox)findViewById(R.id.PDcancel);
					
					PDcancel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							// TODO Auto-generated method stub
							
								CKcancel=isChecked;
							
								
						}
					});
					

					// 取得畫面資訊
					FindView();
					// 監聽按鈕資訊
					ListenerButtonView();
				}
				//對應Layout中的物件
				private void FindView() {
					this.CGquantity = (EditText) findViewById(R.id.CGquantity);
					
					
					
					this.changeCK = (Button) findViewById(R.id.changeCK);
					this.cancel = (Button) findViewById(R.id.cancel);

				}
				//對需要的按鍵進行監聽
				private void ListenerButtonView() {
					this.changeCK.setOnClickListener(this);
					this.cancel.setOnClickListener(this);
					
				}
				
				
				
				//按下各按鈕所執行的事件
				@Override
				public void onClick(View v) {
					

					Intent intent = new Intent();

					switch (v.getId()) {
					//帳號密碼是否為空
					case R.id.changeCK:
							// 擷取文字框上的文字
							quantity = CGquantity.getEditableText().toString();
							
							this.PDcancel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
								
								            @Override
								
								            public void onCheckedChanged(CompoundButton buttonView,
								
								                    boolean isChecked) {
								
								                // TODO Auto-generated method stub
								            	CKcancel=isChecked;
								                
								
								            }
								
								        });
							// 啟動一個Thread(執行緒)，將要傳送的資料放進Runnable中，讓Thread執行
							Thread t = new Thread(new sendPostRunnable(quantity,TF));
									t.start();
							Thread n = new Thread(new sendPostRunnable1());
									n.start();		
									intent.setClass(Activity_prodchange.this, Activity_prodclass4.class);
									startActivity(intent);
									Activity_prodclass4.Activity_class4.finish();
									Activity_prodchange.this.finish();
							
						
						break;

					case R.id.cancel:

						//intent.setClass(Activity_prodchange.this, Activity_prodclass4.class);
						//startActivity(intent);
						Activity_prodchange.this.finish();

						break;
					}
				}
				//執行傳送資料的動作並將資料放進執行緒
				class sendPostRunnable implements Runnable {
					
					String quantity;
					boolean TF;
					// 建構子，設定要傳的字串
					public sendPostRunnable(String quantity,boolean TF) {
						this.quantity=quantity;
						this.TF=TF;
					}

					@Override
					public void run() {

						String result = sendPostDataToInternet(quantity,TF);
						mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget();
					}
				}
				
				class sendPostRunnable1 implements Runnable {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						sendPostDataToInternet1();
					}
				
				}
				
				//傳送資料至網頁
				private String sendPostDataToInternet(String quantity ,boolean TF) {

					/* 建立HTTP Post連線 */
					HttpPost httpRequest = new HttpPost(urlApi);
					/*
					 * Post運作傳送變數必須用NameValuePair[]陣列儲存
					 */
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					
					
					params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$DataList_shop$ctl0"+Item+"$tb_qty",quantity));
					
					//params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$DataList_shop$ctl0"+Item+"$ImageButton_Del","取消"));
					if(CKcancel)
					params.add(new BasicNameValuePair("__EVENTTARGET","ctl00$ContentPlaceHolder1$DataList_shop$ctl0"+Item+"$ImageButton_Del"));	
					params.add(new BasicNameValuePair("__EVENTTARGET","ctl00$ContentPlaceHolder1$Imagebtn_ok"));
					params.add(new BasicNameValuePair("__EVENTARGUMENT",""));
					params.add(new BasicNameValuePair("__VIEWSTATE",__VIEWSTATE));
					params.add(new BasicNameValuePair("__EVENTVALIDATION",__EVENTVALIDATION));
					/*params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$DataList_shop$ctl01$CheckBox_Del",	sendInfo[1]));*/
					

					try {
						/* 發出HTTP request */
						httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
						if(PHPSESSID!=null)
							httpRequest.setHeader("Cookie","ASP.NET_SessionId="+PHPSESSID);
						/* 取得HTTP response */
						HttpResponse httpResponse = new DefaultHttpClient()
								.execute(httpRequest);

						/* 若狀態碼為200 ok */
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							/* 取出回應字串 */
							String strResult = EntityUtils.toString(httpResponse
									.getEntity());
							//showToast(strResult);
							
							System.out.println(strResult+"=====OK");
							//showToast(strResult);

							// 回傳回應字串
							return strResult;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
				private String sendPostDataToInternet1() {

					/* 建立HTTP Post連線 */
					HttpPost httpRequest = new HttpPost(urlApi);
					/*
					 * Post運作傳送變數必須用NameValuePair[]陣列儲存
					 */
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					if(CKcancel){
					params.add(new BasicNameValuePair("__EVENTTARGET","ctl00$ContentPlaceHolder1$DataList_shop$ctl0"+Item+"$ImageButton_Del"));	
					params.add(new BasicNameValuePair("__EVENTARGUMENT",""));
					params.add(new BasicNameValuePair("__VIEWSTATE",__VIEWSTATE));
					params.add(new BasicNameValuePair("__EVENTVALIDATION",__EVENTVALIDATION));
					}
					/*params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$DataList_shop$ctl01$CheckBox_Del",	sendInfo[1]));*/
					

					try {
						/* 發出HTTP request */
						httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
						if(PHPSESSID!=null)
							httpRequest.setHeader("Cookie","ASP.NET_SessionId="+PHPSESSID);
						/* 取得HTTP response */
						HttpResponse httpResponse = new DefaultHttpClient()
								.execute(httpRequest);

						/* 若狀態碼為200 ok */
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							/* 取出回應字串 */
							String strResult = EntityUtils.toString(httpResponse
									.getEntity());
							//showToast(strResult);
							
							//System.out.println(strResult+"=====OK");
							//showToast(strResult);

							// 回傳回應字串
							return strResult;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}

				public void showToast(final String msg) {

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(Activity_prodchange.this, msg, Toast.LENGTH_LONG)
									.show();
						}
					});
				}

				@Override
				public boolean onCreateOptionsMenu(Menu menu) {
					// Inflate the menu; this adds items to the action bar if it is present.
					getMenuInflater().inflate(R.menu.activity_addmember, menu);
					return true;
				}

			}

