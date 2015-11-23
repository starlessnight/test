package com.changcheng.Activity;

import java.util.List;

import android.app.Activity;

import android.os.Bundle;

import android.telephony.SmsManager;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.Button;

import android.widget.EditText;




public class SendMessage extends Activity {

/** Called when the activity is first created. */

@Override

public void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState);

setContentView(R.layout.activity_send_message);

// �ھ�ID������s

Button button = (Button) this.findViewById(R.id.send);

// ���U���s�Q�I���ƥ�

button.setOnClickListener(new OnClickListener() {




@Override

public void onClick(View v) {

// �ھ�ID���������X�s���

EditText mobileText = (EditText) findViewById(R.id.mobile);

// ���������X

String mobile = mobileText.getText().toString();

// �ھ�ID����T�����e�s���

EditText messageText = (EditText) findViewById(R.id.message);

// ����T�����e

String message = messageText.getText().toString();

// �q�H�~�̤��\�C���ǰe���r�Ʀ����A�ڭ̥i�H�ϥ�Android���ڭ̴��Ѫ�²�T�u��C

if (message != null) {

SmsManager sms = SmsManager.getDefault();

// �p�G²�T�S���W�L������סA�h�Ǧ^�@�Ӫ��ת�List�C

List<String> texts = sms.divideMessage(message);

for (String text : texts) {

sms.sendTextMessage(mobile, null, text, null, null);

}

}

}

});

}

}
