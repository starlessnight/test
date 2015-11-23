package com.example.asd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.asd.R;
import com.example.asd.SosMain;

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

public class SosMain extends Activity{
	
	private Button mBtnSaveResult,
					mBtnLoadResult,
					mBtnClearResult;
	private EditText data,nmb;
	private String NO="nULL",getED,getNmb;
	private TextView view1,view2;

	private Button 	SsBack, sosSet, goLook;
	private EditText SosMessage, CellPhone;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.sos);
		SsBack = (Button) findViewById(R.id.SsBack);
		sosSet = (Button) findViewById(R.id.sosSet);
		SosMessage = (EditText) findViewById(R.id.sosMessage);
		CellPhone = (EditText) findViewById(R.id.CellPhone);
		
		//�x�s�T��
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		SosMessage.setText("輸入求救訊息");
		CellPhone.setText("輸入求救人電話");
		SsBack.setOnClickListener(new Button.OnClickListener()

		{
			@Override
			public void onClick(View v) {
				SosMain.this.finish();
				

			}
		});
		SosMessage.setOnClickListener(new EditText.OnClickListener() {
			@Override
			public void onClick(View v) {
				SosMessage.setText("");
			}
		});

		CellPhone.setOnClickListener(new EditText.OnClickListener() {
			@Override
			public void onClick(View v) {
				CellPhone.setText("");
			}
		});

		sosSet.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				
				
				
				String strDestAddress = CellPhone.getText().toString();
				String strMessage = SosMessage.getText().toString();
				SmsManager smsManager = SmsManager.getDefault();
				if (phoneNumberValid(strDestAddress) == true
						&& iswithin70(strMessage) == true) {
					SharedPreferences MsgResultData=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
					
					MsgResultData.edit()
					.putString("text",SosMessage.getEditableText().toString())
					.putString("nmb",CellPhone.getEditableText().toString())
					.putString("IF", "true")
					.commit();
					
					Toast.makeText(SosMain.this, "儲存成功",Toast.LENGTH_SHORT).show();
					/*try {
						PendingIntent mPI = PendingIntent.getBroadcast(
								SosMain.this, 0, new Intent(), 0);
						smsManager.sendTextMessage(strDestAddress, null,
								strMessage, mPI, null);

					} catch (Exception e) {
						e.printStackTrace();

					}*/
					//Toast.makeText(SosMain.this, "�o�e���\",	Toast.LENGTH_SHORT).show();
					SosMessage.setText("");

				}

				else {
					if (phoneNumberValid(strDestAddress) == false) {
						if (iswithin70(strMessage) == false) {
							Toast.makeText(SosMain.this,
									"字數太多",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(SosMain.this, "電話輸入不正確",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(SosMain.this, "電話輸入不正確",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		// �ˬd�T����q�ܮ榡

	}

	public static boolean phoneNumberValid(String phoneNumber) {
		boolean isValid = false;

		String expression1 = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
		String expression2 = "^\\(?(\\d{2})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";

		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression1);

		Matcher matcher = pattern.matcher(inputStr);

		Pattern pattern2 = Pattern.compile(expression2);

		Matcher matcher2 = pattern.matcher(inputStr);

		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;

	}

	public static boolean iswithin70(String text) {
		if (text.length() >0&text.length()<= 70) {
			return true;
		}
		return false;
		
	}
	
	private Button.OnClickListener btnSaveResultLis=new Button.OnClickListener(){
		public void onClick(View v){
			
		}
	};
	
	private Button.OnClickListener btnClearResultLis=new Button.OnClickListener(){
		public void onClick(View v){
			SharedPreferences MsgResultData=getSharedPreferences("MSG_RESULT",0);
			MsgResultData.edit().clear().commit();
			
		}
	};
	
	
	public void jumpSos() {
		setContentView(R.layout.msg);
	}
	

	}
	

