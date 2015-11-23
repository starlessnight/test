package com.example.asd;



import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.android.maps.GeoPoint;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;

public class MainActivity extends Activity implements OnClickListener {
	private Button SendButton ;
	private EditText User;
	private EditText Passwd;
	private TextView UserName=null;
	private String urlApi="http://192.168.137.83/datatest/DB.php";
	private String massage="";
	GeoPoint gp;
	Back_thing back_thing=new Back_thing();
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
				 	Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
				
					
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//取得畫面資訊
		findView();
		
		//判斷是否有開啟網路
		boolean InternetTF =ChackInternetAndSeting();
		
		if(User != null ){
			
			SendButton.setOnClickListener(this);
		
		}else{
			ChackInternetAndSeting();
		}
		
		
	}
		
	private void findView(){
		SendButton=(Button) findViewById(R.id.btnEnter);
		User=(EditText)findViewById(R.id.editText1);
		Passwd=(EditText)findViewById(R.id.editText2);
		
	}
	

	
	private boolean ChackInternetAndSeting(){	
		if(haveInternet()){
			massage="網路已連線!!";
			Toast.makeText(MainActivity.this, massage, Toast.LENGTH_SHORT).show();
			return true;
		}else{
			//未連接到網路將開啟設定畫面
			InternetChack.setNetworkMethod(MainActivity.this);
			
			massage="網路未連線!!";
			Toast.makeText(MainActivity.this, massage, Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	private boolean haveInternet(){
    	boolean result = false;
    	ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); 
    	NetworkInfo info=connManager.getActiveNetworkInfo();
    	//取得是否有網路
    	if (info == null || !info.isConnected()){
    		result = false;
    	}else{
    		result = true;
    	}
        return result;
    }
	
	

	//******************************************************************
	@Override
	public void onClick(View v)	{
		if (v == SendButton){
			if (User != null && Passwd!= null)	{
				
				 Intent intent = new Intent();

	             intent.setClass(MainActivity.this, AllMaina.class);

	             //呼叫一個新的activity

	            
				// 擷取文字框上的文字
				final String user = User.getEditableText().toString();
				final String passwd = Passwd.getEditableText().toString();
				// 啟動一個Thread(執行緒)，將要傳送的資料放進Runnable中，讓Thread執行
				Thread t = new Thread(new sendPostRunnable(user , passwd));
				t.start(); 
				/*startActivity(intent);

	             MainActivity.this.finish();*/
			}
		}
	}

	class sendPostRunnable implements Runnable
	{	
			Matcher mat;
			Pattern pat;
			Boolean found;
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
			
			pat = Pattern.compile("pass"); 
			mat = pat.matcher(result);  
			found = mat.find(); 
			
			//test
			if(found){
				System.out.println("@@@@@@@@@@@@@@@@@@@@@");
				SharedPreferences MsgResultData=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
				
				MsgResultData.edit()
				.putString("text1","我出車禍了")
				.putString("nmb2","119")
				.putString("IF", "true")
				.commit();
				
				
				// TODO Auto-generated method stub

	             //new 一個intent物件，並指定要啟動的class

	             Intent intent = new Intent();

	             intent.setClass(MainActivity.this, AllMaina.class);

	             //呼叫一個新的activity

	             startActivity(intent);

	             MainActivity.this.finish();
	             }
			
			}
		
	}

	private String sendPostDataToInternet(String User , String Passwd){

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost(urlApi);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("account", User));
		params.add(new BasicNameValuePair("password", Passwd));

		try{
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()	.execute(httpRequest);
			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200){
				/* 取出回應字串 */
				String strResult="123";
				 strResult = EntityUtils.toString(httpResponse.getEntity());
				
				
				strResult=strResult.substring(0, strResult.indexOf("<html>"));
				
				
				
				//String strResult = "已送出!!";
				// 回傳回應字串
				return strResult;
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	







	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
}
