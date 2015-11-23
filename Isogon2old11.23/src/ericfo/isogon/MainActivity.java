package ericfo.isogon;

import ericfo.isogon.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	static Activity Activity_Main;
	private String massage = "";
	private Context mContext;
	private LinearLayout linearlayout_add_login;
	private Button about_button, salenews_button, prodclass_button,
			store_button, addmember, Login;

	//private final static int REQUESTCODE = 1;// 返回的结果码

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.mContext = getApplicationContext();

		setContentView(R.layout.activity_main);
		Activity_Main=this;
		// 取得畫面資訊
		FindView();
		// 判斷是否有開啟網路
		boolean InternetTF = ChackInternetAndSeting();

		if (InternetTF) {
			// SendButton.setOnClickListener(this);
		} else {
			InternetTF = ChackInternetAndSeting();
		}
		// 監聽按鈕資訊
		ListenerButtonView();
		
		SharedPreferences MemCookie=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
        MemCookie.edit()
		.putString("Cookie",null)
		.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void ListenerButtonView() {
		this.addmember.setOnClickListener(this);
		this.about_button.setOnClickListener(this);
		this.salenews_button.setOnClickListener(this);
		this.prodclass_button.setOnClickListener(this);
		this.store_button.setOnClickListener(this);
	}

	private void FindView() {
		this.linearlayout_add_login = (LinearLayout) findViewById(R.id.linearlayout_add_login);
		this.addmember = (Button) findViewById(R.id.addmember);
		this.about_button = (Button) findViewById(R.id.about);
		this.salenews_button = (Button) findViewById(R.id.salenews);
		this.prodclass_button = (Button) findViewById(R.id.prodclass);
		this.store_button = (Button) findViewById(R.id.store);

	}

	private boolean ChackInternetAndSeting() {
		if (haveInternet()) {
			massage = "您已連線到網路中!!";
			Toast.makeText(MainActivity.this, massage, Toast.LENGTH_LONG)
					.show();
			return true;
		} else {
			// 未連接到網路將開啟設定畫面
			InternetChack.setNetworkMethod(MainActivity.this);

			massage = "您未連線到網路中!!";
			Toast.makeText(MainActivity.this, massage, Toast.LENGTH_LONG)
					.show();
			return false;
		}
	}

	private boolean haveInternet() {
		boolean result = false;
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		// 取得是否有網路
		if (info == null || !info.isConnected()) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE) {

			if (resultCode == 1) {

				linearlayout_add_login.removeView(Login);
				linearlayout_add_login.removeView(addmember);
				LoginTextView.setTextSize(30);
				LoginTextView.setVisibility(View.VISIBLE);

				Button logout = new Button(this);
				logout.setText("登出");
				logout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, MainActivity.class);
						Toast.makeText(mContext, "您已登出!!", Toast.LENGTH_SHORT).show();
						startActivity(intent);
						MainActivity.this.finish();
					}
				});
				
				linearlayout_add_login.addView(logout);

				Bundle bundle = data.getExtras();
				String Name = bundle.getString("username");
				LoginTextView.setText("歡迎：" + Name + " 登入!!");
			}
		}
	}
*/
	@Override
	public void onClick(View v) {

		Intent intent = new Intent();
		//MainActivity.this.finish();
		
		switch (v.getId()) {
		case R.id.addmember:
			intent.setClass(MainActivity.this, Activity_addmember.class);
			startActivity(intent);
			break;
		case R.id.about:
			intent.setClass(MainActivity.this, Activity_about.class);
			startActivity(intent);
			break;
		case R.id.salenews:
			intent.setClass(MainActivity.this, Activity_salenews.class);
			startActivity(intent);
			break;
		case R.id.prodclass:
			intent.setClass(MainActivity.this, Activity_prodclass.class);
			startActivity(intent);
			break;
		case R.id.store:
			intent.setClass(MainActivity.this, Activity_Store.class);
			startActivity(intent);
			break;
		}
	}
}
