package ericfo.isogon;

import ericfo.isogon.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class Activity_about extends Activity implements OnClickListener {
	
	private Context mContext; 
	
	private TextView about3 = null;
	private TextView textview = null;
	
	private String content = "         2010年4月在易利華科技-林執行長的號召下，我們組成了『飛雁手工傳奇』社群，參加數位落差計劃，進行聯合行銷，成員企業們不但接受了許多行銷教育訓練，也參加了許多網路及實體的行銷活動。讓我們的數位能力提升許多，並體驗了優良的行銷活動帶給我們實際業績的成長！" +
												  "\n\n         更重要的是許許多多來自各縣市的網友、消費者給我們的肯定與讚美，讓我們這群微型手工創業者深受感動與鼓勵！在共同努力下，我們也在2010.11月榮獲中華軟協FaceBook行銷競賽，品質組全國第一名！民國100年起，我們將社群名稱改為『手工傳奇』，以符合未來的成長！" +
												  "\n \n        並放寬未來平台成員加入的資格以讓更多手工創業者都有機會一起努力，今後不限制一定要婦女朋友，只要是手工創業者都可以加入，不分男性或女性，都是我們的好夥伴！藉由本次社群行銷深化的機會，不但擴大成只要手工創業者都可加入外，同時也建立了未來社群領導人產生的制度，使本社群組織得以邁向永續經營，為台灣手工業者建立一個共同的、優質的網路行銷平台！";

	
	private Button back; 
	//主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		
		this.mContext=getApplicationContext();
		
		setContentView(R.layout.activity_about);
		
		FindView();
		ListenerButtonView() ;
		about3.setText(content);
	}
	//監聽按鈕
	private void ListenerButtonView() {
		
		this.back.setOnClickListener(this);

	}
	//對應Layout的物件
	private void FindView() {
		
		this.about3 = (TextView) this.findViewById(R.id.about3);
		this.back = (Button) findViewById(R.id.back);

	}	
	//按扭按下所觸發的事件
	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent();
		
		switch (v.getId()) {
		case R.id.back:
			//intent.setClass(Activity_about.this , MainActivity.class );
			//startActivity(intent);
			Activity_about.this.finish();
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}

}
