package ericfo.isogon;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import ericfo.isogon.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_member extends Activity implements OnClickListener{
	
	private Context mContext;
	private Button Loginmember,CloseLogin,addmember;
	private ProgressDialog dialog;
	private EditText User;
	private EditText Passwd;
	private String PHPSESSID = null,user_name;
	public List<Cookie> cookies;
	
	private String urlApi="http://211.72.86.193/Isogon_M/Customer/MemLogin.aspx";

	protected static final int REFRESH_DATA = 0x00000001;
	
	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg){
			switch (msg.what){
			// 顯示網路上抓取的資料
			case REFRESH_DATA:
				String result = null;
				if (msg.obj instanceof String)
					result = (String) msg.obj;
				if (result != null)
					// 印出網路回傳的文字
					//Toast.makeText(Login_member.this, result, Toast.LENGTH_LONG).show();
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.mContext = getApplicationContext();
		SharedPreferences MemCookie=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
		PHPSESSID=MemCookie.getString("Cookie",null);
		user_name=MemCookie.getString("user",null);
		if(PHPSESSID!=null){
			Toast.makeText(Login_member.this, "歡迎"+user_name, Toast.LENGTH_LONG)
            .show();
			Intent intent=new Intent();
			intent.setClass(Login_member.this, Activity_prodclass4.class);
			startActivity(intent);
			Login_member.this.finish();
			}
		setContentView(R.layout.activity_login_member);
		
		this.addmember=(Button)findViewById(R.id.addmember);
		addmember.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Login_member.this, Activity_addmember.class);
				startActivity(intent);
			}
			
		});
		// 取得畫面資訊
		FindView();
		// 監聽按鈕資訊
		ListenerButtonView();
	}
	
	private void FindView() {
		this.User=(EditText)findViewById(R.id.edituser);
		this.Passwd=(EditText)findViewById(R.id.editpasswd);
		this.Loginmember = (Button)findViewById(R.id.Loginmember);
		this.CloseLogin = (Button)findViewById(R.id.CloseLogin);
		
	}
	
	private void ListenerButtonView() {
		
		this.Loginmember.setOnClickListener(this);
		this.CloseLogin.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent();
		
		switch (v.getId()) {
		
		case R.id.Loginmember:
			if (User != null && Passwd!= null)	{
				// 擷取文字框上的文字
				final String user = User.getEditableText().toString();
				final String passwd = Passwd.getEditableText().toString();
				// 啟動一個Thread(執行緒)，將要傳送的資料放進Runnable中，讓Thread執行
				// prepare the dialog box
				dialog = new ProgressDialog(this);

				// make the progress bar cancelable
				dialog.setCancelable(false);

				// set a message text
				dialog.setMessage("Loading...");

				// show it
				dialog.show();
				
				Thread t = new Thread(new sendPostRunnable(user , passwd));
				t.start();
				
			}
			break;
			
		case R.id.CloseLogin:
			
			//intent.setClass(Login_member.this , MainActivity.class );
			//startActivity(intent);
			setResult(2,intent);
			Login_member.this.finish();
			break;
			
			
		}
	}
	
	class sendPostRunnable implements Runnable
	{
		String user = null;
		String passwd = null;
		// 建構子，設定要傳的字串
		public sendPostRunnable(String User , String Passwd)	{
			this.user = User;
			this.passwd=Passwd;
		}
		@Override
		public void run(){
	
			String result = sendPostDataToInternet(user , passwd);
			mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget();
		}
	}

	
	private String sendPostDataToInternet(String User , String Passwd){

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost(urlApi);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("__EVENTTARGET", "ctl00$ContentPlaceHolder1$LinkButton1"));
	    params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
	    params.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwULLTEyNDUzMjM5MTQPZBYCZg9kFgICAw9kFgQCAw8WAh4HVmlzaWJsZWdkAgUPFgIfAGgWAgIDDw8WAh4LTmF2aWdhdGVVcmwFLH4vbWFpbi9TaG9wcGluZ0NhcnQuYXNweD9jX2lwPTYwLjI0OS4xNDYuMjE1ZGRkirRcUYph5jbBqDzXz8TlUGonqQY="));
	    params.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWBgKsj+qoDAKGrIuYCgK/7a/7AgKO6PmbAQKN6PmbAQKP6PmbAeROonDXnbmvM8ELu8KcJiS7810L"));
		
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txt_id",User ));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txt_pass", Passwd));
		try{
			
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			Thread.sleep(3000);
			/* 取得HTTP response */
			
			DefaultHttpClient HttpClient=new DefaultHttpClient();
			HttpResponse httpResponse = HttpClient.execute(httpRequest);
			getCookie(HttpClient);
			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200){
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				
				//判斷是否登入
				
				if(strResult.indexOf("HI，")>0){
					strResult=strResult.substring(strResult.indexOf("ctl00_lbl_Name"),strResult.indexOf("歡迎您"));
					strResult=strResult.substring(strResult.indexOf(">")+1,strResult.indexOf("</"));
					//System.out.println(strResult);
					showToast("歡迎 "+strResult +" 登入!!");
					SharedPreferences MemCookie=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
		            MemCookie.edit()
					.putString("user",strResult)
					.commit();
					Intent intent = new Intent();
					intent.setClass(Login_member.this, Activity_prodclass4.class);
					/*Bundle myBundle = new Bundle();
					myBundle.putString("PHPSESSID", PHPSESSID);
					Log.d("login", PHPSESSID);
					intent.putExtras(myBundle);*/
					setResult(1,intent);
					startActivity(intent);
					Login_member.this.finish();
					
				}else{
					strResult=strResult.substring(strResult.indexOf("帳號或密碼"),(strResult.indexOf("重新輸入")+4));
					//System.out.println(strResult);
					showToast(strResult);
				}
				
				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
    private void getCookie(DefaultHttpClient httpClient) {
        cookies = httpClient.getCookieStore().getCookies();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            PHPSESSID=cookie.getValue();
            SharedPreferences MemCookie=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
            MemCookie.edit()
			.putString("Cookie",PHPSESSID)
			.commit();
            
            MemCookie=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
			PHPSESSID=MemCookie.getString("Cookie",null);
			System.out.println("9696969696"+PHPSESSID);
			
            //System.out.println(PHPSESSID);
            //String cookieName = cookie.getName();
           // String cookieValue = cookie.getValue();
           //System.out.println(cookieName);
          // System.out.println(cookieValue);
           
       }
        
  }
	
	public void showToast(final String msg) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
            	dialog.dismiss();
                Toast.makeText(Login_member.this, msg, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_member, menu);
		return true;
	}

}
