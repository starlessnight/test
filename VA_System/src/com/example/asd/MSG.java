package com.example.asd;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MSG extends Activity{
	
	private Button 	mBtnLoadResult,BkSos,clr;
	private String NO="nULL",getED,getNmb,Mg,Nb,Send,s119,ssg,getX,getY,ms;
	private TextView view1,view2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.msg);
		//����x�s=>
		view1=(TextView)findViewById(R.id.btnShowResult);
		view2=(TextView)findViewById(R.id.btnShowResult2);
		
		clr=(Button)findViewById(R.id.btnLoadResult2);
		mBtnLoadResult=(Button)findViewById(R.id.btnLoadResult);
		BkSos=(Button)findViewById(R.id.BkSos);
		BkSos.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				
				intent.setClass(MSG.this,SosMain.class);
				
				startActivity(intent);
				MSG.this.finish();
			}
		});
		mBtnLoadResult.setOnClickListener(btnLoadResultLis);
		clr.setOnClickListener(btnClearResultLis);
		//END
		
		
		//�۰ʰe²�T
		
		
}
	/*public void setM(){
		SharedPreferences MsgResultData=getSharedPreferences("MSG_RESULT",0);
		ssg=MsgResultData.getString("text1", NO);
		  		Mg=MsgResultData.getString("text", ssg);
		  		s119=MsgResultData.getString("nmb2", NO);
				Nb=MsgResultData.getString("nmb", s119);
				
	}
	*/
	
	
	private Button.OnClickListener btnLoadResultLis=new Button.OnClickListener(){
		public void onClick(View v){
						
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
			if(	Send!=null){
			SmsManager smsManager=SmsManager.getDefault();
			try{
				
				
				//�إ�PendingIntent �@�� sentIntent�Ѽ�
				PendingIntent mPI=PendingIntent.getBroadcast(
						MSG.this,0,new Intent(),0);
				
				//�����ǰe²�T
				smsManager.sendTextMessage(Mg,null,Nb,mPI,null);
				Toast.makeText(MSG.this,
						getResources().getText(R.string.str_cmd_sending).toString()+Mg,Toast.LENGTH_SHORT
						).show();
				
				
			}catch(Exception e){
				
			}
			
			}
			
			SharedPreferences MsgResultData=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
			getED=MsgResultData.getString("text", "我出車禍了");
			view1.setText(getED);
			getNmb=MsgResultData.getString("nmb", "119");
			view2.setText(getNmb);
			getX=MsgResultData.getString("gpsX", "找不到X");
			getY=MsgResultData.getString("gpsY", "找不到Y");
			ms=getED+"\n"+getX+"\n"+getY;
			Toast.makeText(MSG.this, "現在位置\n"+getX+"\n"+getY,Toast.LENGTH_SHORT).show();
			
			
			
			
			String mobile = getNmb;

			
			// ���T�����e

			

			// �q�H�~�̤��\�C���ǰe���r�Ʀ����A�ڭ̥i�H�ϥ�Android���ڭ̴��Ѫ�²�T�u��C

			if (ms != null) {

			SmsManager sms = SmsManager.getDefault();

			// �p�G²�T�S���W�L�����סA�h�Ǧ^�@�Ӫ�ת�List�C

			List<String> texts = sms.divideMessage(ms);
			System.out.println(texts);
			for (String text : texts) {

			sms.sendTextMessage(mobile, null, text, null, null);

			}

			}
			
		}
	};
	public static boolean iswithin70(String text) {
		if (text.length() >0&text.length()<= 70) {
			return true;
		}
		return false;
		
	}
	private Button.OnClickListener btnClearResultLis=new Button.OnClickListener(){
		public void onClick(View v){
			SharedPreferences MsgResultData=getSharedPreferences("MSG_RESULT",0);
			MsgResultData.edit().remove("text").remove("nmb").commit();
			
		}
	};
}