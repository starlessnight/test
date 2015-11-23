package ericfo.isogon;

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
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Activity_addmember extends Activity implements OnClickListener {

	private Context mContext;
	private Button SendAddMember, CloseMember;

	private EditText User, Passwd, UserMember, Phone, Cellphone, Address;

	private RadioButton rd1, rd2;
	private RadioGroup sex;
	private String gender;
	private String[] info = new String[7];
	

	private String urlApi = "http://www.isogon.com.tw/Isogon_M/Customer/Ins_Customer.aspx";

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
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);

		this.mContext = getApplicationContext();

		setContentView(R.layout.activity_addmember);

		// 開始對應物件
		FindView();
		// 監聽按鈕資訊
		ListenerButtonView();
	}
	//對應Layout中的物件
	private void FindView() {
		this.User = (EditText) findViewById(R.id.edituser);
		this.Passwd = (EditText) findViewById(R.id.editpasswd);
		this.UserMember = (EditText) findViewById(R.id.editusermember);
		// -------------------------------------------------------
		this.rd1 = (RadioButton) findViewById(R.id.boy);
		this.rd2 = (RadioButton) findViewById(R.id.girl);
		this.sex = (RadioGroup) findViewById(R.id.radiogroup);
		// -------------------------------------------------------
		this.Phone = (EditText) findViewById(R.id.editphone);
		this.Cellphone = (EditText) findViewById(R.id.editcellphone);
		this.Address = (EditText) findViewById(R.id.editaddress);

		this.SendAddMember = (Button) findViewById(R.id.SendAddMember);
		this.CloseMember = (Button) findViewById(R.id.CloseMember);

	}
	//對需要的按鍵進行監聽
	private void ListenerButtonView() {
		sex.setOnCheckedChangeListener(radiogrouplistener);
		this.SendAddMember.setOnClickListener(this);
		this.CloseMember.setOnClickListener(this);

	}
	//對性別選項進行監聽
	private RadioGroup.OnCheckedChangeListener radiogrouplistener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if (checkedId == rd1.getId()) {
				// 男
				gender = "RadioButton_M";
			} else if (checkedId == rd2.getId()) {
				// 女
				gender = "RadioButton_W";
			}
		}
	};
	//按下各按鈕所執行的事件
	@Override
	public void onClick(View v) {

		Intent intent = new Intent();

		switch (v.getId()) {
		//帳號密碼是否為空
		case R.id.SendAddMember:
			if (this.User != null && this.Passwd != null
					&& this.UserMember != null && this.Phone != null
					&& this.Address != null) {
				// 擷取文字框上的文字
				
				boolean tf_info=false;
				int i;
				info[0] = User.getEditableText().toString();
				info[1] = Passwd.getEditableText().toString();
				info[2]  = UserMember.getEditableText().toString();
				info[3] = gender;
				info[4] = Phone.getEditableText().toString();
				info[5] = Cellphone.getEditableText().toString();
				info[6] = Address.getEditableText().toString();
				//驗證是否有資料
				for(i=0;i<info.length;i++){
					if(info[i].length() == 0){
						tf_info=false;
						break;
					}else{
						tf_info=true;
					}
					
				}
				// 判斷是否有填入的資料是否為空				
				if(tf_info){
						Thread t = new Thread(new sendPostRunnable(info));
						t.start();
				}else{
					switch (i){
						case 0 :
							showToast("您未填寫 E-mail !!");
						break;
						case 1 :
							showToast("您未填寫 密碼 !!");
						break;
						case 2 :
							showToast("您未填寫 會員姓名 !!");
						break;
						case 3 :
							showToast("您未填寫 性別 !!");
						break;
						case 4 :
							showToast("您未填寫 電話 !!");
						break;
						case 5 :
							showToast("您未填寫 行動電話 !!");
						break;
						case 6 :
							showToast("您未填寫 聯絡地址 !!");
						break;
					}
					
				}
				
			}
			break;

		case R.id.CloseMember:

			//intent.setClass(Activity_addmember.this, MainActivity.class);
			//startActivity(intent);
			Activity_addmember.this.finish();

			break;
		}
	}
	//執行傳送資料的動作並將資料放進執行緒
	class sendPostRunnable implements Runnable {
		
		String[] sendInfo={};
		// 建構子，設定要傳的字串
		public sendPostRunnable(String[] info) {
			sendInfo=info;
		}

		@Override
		public void run() {

			String result = sendPostDataToInternet(sendInfo);
			mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget();
		}
	}
	//傳送資料至網頁
	private String sendPostDataToInternet(String[] sendInfo) {

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost(urlApi);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("__EVENTTARGET","ctl00$ContentPlaceHolder1$LinkButton1"));
		params.add(new BasicNameValuePair("__EVENTARGUMENT",""));
		params.add(new BasicNameValuePair("__VIEWSTATE","/wEPDwULLTE3NTM1NTQ2MjIPZBYCZg9kFgICAw9kFgQCAw8WAh4HVmlzaWJsZWdkAgUPFgIfAGgWAgIDDw8WAh4LTmF2aWdhdGVVcmwFLH4vbWFpbi9TaG9wcGluZ0NhcnQuYXNweD9jX2lwPTYwLjI0OS4xNDYuMjE1ZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgMFJ2N0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkUmFkaW9CdXR0b25fTQUnY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRSYWRpb0J1dHRvbl9XBSdjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJFJhZGlvQnV0dG9uX1ea3tk4inMtHpxdDqRN19jV5s6GCA=="));
		params.add(new BasicNameValuePair("__EVENTVALIDATION",	"/wEWDQLJhN79CQLkufyvAgKA4sljAoO6sPcNAu25sMQPAtaXvKECAqyWvKECAqGa3ZgMArX1vf4KAonGovkNAtP1qbMMAo/o+ZsBAo7o+ZsB8+35zXxw37K0kSGCMbRJ2Fzo/c4="));

		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$tb_mail",	sendInfo[0]));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$tb_pass",	sendInfo[1]));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$tb_name",sendInfo[2]));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$sex",	sendInfo[3]));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$tb_tel",sendInfo[4]));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$tb_mobile", sendInfo[5]));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$tb_addr",	sendInfo[6]));

		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				//showToast(strResult);
				if (strResult.indexOf("script") > 0) {
					strResult = strResult.substring(strResult.indexOf("alert") + 9,strResult.indexOf(";")-4);
					//System.out.println(strResult);
					if(!(strResult.equals("該帳號已有人正在使用"))){
						
						showToast(strResult);
						
						Intent intent = new Intent();
						intent.setClass(Activity_addmember.this,MainActivity.class);
						startActivity(intent);
						Activity_addmember.this.finish();
						
					}else{
						showToast(strResult);
					}
					
					
					
				}
				System.out.println(strResult);
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
				Toast.makeText(Activity_addmember.this, msg, Toast.LENGTH_LONG)
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
