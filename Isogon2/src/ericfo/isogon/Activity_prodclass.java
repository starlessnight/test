package ericfo.isogon;

import ericfo.isogon.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_prodclass extends Activity implements OnClickListener {
	static Activity  Activity_prodclass;
	private Button Login,ProdClass01_button, ProdClass02_button, ProdClass03_button,	ProdClass04_button, ProdClass05_button,ProdClass06_button;
	private String PHPSESSID;
	private final static int REQUESTCODE = 1;// 返回的结果码
	//主程式
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prodclass);
		Activity_prodclass=this;
		FindView();
		ListenerButtonView();
		
	}
	//設定按鈕監聽
	private void ListenerButtonView() {
		this.Login.setOnClickListener(this);
		this.ProdClass01_button.setOnClickListener(this);
		this.ProdClass02_button.setOnClickListener(this);
		this.ProdClass03_button.setOnClickListener(this);
		this.ProdClass04_button.setOnClickListener(this);
		this.ProdClass05_button.setOnClickListener(this);
		this.ProdClass06_button.setOnClickListener(this);
	}
	//對應Layout物件
	private void FindView() {
		this.Login = (Button) findViewById(R.id.Login);
		this.ProdClass01_button = (Button) findViewById(R.id.ProdClass01);
		this.ProdClass02_button = (Button) findViewById(R.id.ProdClass02);
		this.ProdClass03_button = (Button) findViewById(R.id.ProdClass03);
		this.ProdClass04_button = (Button) findViewById(R.id.ProdClass04);
		this.ProdClass05_button = (Button) findViewById(R.id.ProdClass05);
		this.ProdClass06_button = (Button) findViewById(R.id.ProdClass06);

	}
	
	
	@Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
            // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTCODE){
            if(resultCode==2){
                //setTitle("Cancel****");
            }else 
                if(resultCode==1){
//              String  Name=data.getStringExtra("username");
                Bundle bundle = data.getExtras();
                //PHPSESSID = bundle.getString("PHPSESSID");
                //Log.d("prodclass", PHPSESSID);
            }
        }
    }
	//按鈕所觸發的事件	
	@Override
	public void onClick(View v) {
		
		Bundle bundle = new Bundle(); 
		Intent intent = new Intent();
		//MainActivity.this.finish();
		
		switch (v.getId()) {
		case R.id.Login:
			intent.setClass(Activity_prodclass.this, Login_member.class);
			startActivityForResult(intent,REQUESTCODE);
			//startActivity(intent);
			//Activity_prodclass.this.finish();
		break;
		case R.id.ProdClass01:
			intent.setClass(Activity_prodclass.this, Activity_prodclass2.class);
    		bundle.putInt("UrlID", 1); 
    		bundle.putString("UrlStr", "風味美食");
    		//bundle.putString("PHPSESSID", PHPSESSID);
    		intent.putExtras(bundle); 
			startActivity(intent);
			//Activity_prodclass.this.finish();
			break;
		case R.id.ProdClass02:
			bundle.putInt("UrlID", 2); 
			bundle.putString("UrlStr", "品味生活");
			//bundle.putString("PHPSESSID", PHPSESSID);
    		intent.putExtras(bundle); 
			intent.setClass(Activity_prodclass.this, Activity_prodclass2.class);
			startActivity(intent);
			//Activity_prodclass.this.finish();
			break;
		case R.id.ProdClass03:
			bundle.putInt("UrlID", 3); 
			bundle.putString("UrlStr", "居家環保");
			//bundle.putString("PHPSESSID", PHPSESSID);
    		intent.putExtras(bundle); 
			intent.setClass(Activity_prodclass.this, Activity_prodclass2.class);
			startActivity(intent);
			//Activity_prodclass.this.finish();
			break;
		case R.id.ProdClass04:
			bundle.putInt("UrlID", 4); 
			bundle.putString("UrlStr", "健康點心");
			//bundle.putString("PHPSESSID", PHPSESSID);
    		intent.putExtras(bundle); 
			intent.setClass(Activity_prodclass.this, Activity_prodclass2.class);
			startActivity(intent);
			//Activity_prodclass.this.finish();
			break;
		case R.id.ProdClass05:
			bundle.putInt("UrlID", 5); 
			bundle.putString("UrlStr", "文化創藝");
			//bundle.putString("PHPSESSID", PHPSESSID);
    		intent.putExtras(bundle); 
			intent.setClass(Activity_prodclass.this, Activity_prodclass2.class);
			startActivity(intent);
			//Activity_prodclass.this.finish();
			break;
		case R.id.ProdClass06:
			bundle.putInt("UrlID", 6); 
			bundle.putString("UrlStr", "精緻禮盒");
			//bundle.putString("PHPSESSID", PHPSESSID);
    		intent.putExtras(bundle); 
			intent.setClass(Activity_prodclass.this, Activity_prodclass2.class);
			startActivity(intent);
			//Activity_prodclass.this.finish();
			break;
		}
	}
	
	public void showToast(final String msg) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(Activity_prodclass.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_prodclass, menu);
		return true;
	}

}
