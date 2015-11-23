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

// 根據ID獲取按鈕

Button button = (Button) this.findViewById(R.id.send);

// 註冊按鈕被點擊事件

button.setOnClickListener(new OnClickListener() {




@Override

public void onClick(View v) {

// 根據ID獲取手機號碼編輯框

EditText mobileText = (EditText) findViewById(R.id.mobile);

// 獲取手機號碼

String mobile = mobileText.getText().toString();

// 根據ID獲取訊息內容編輯框

EditText messageText = (EditText) findViewById(R.id.message);

// 獲取訊息內容

String message = messageText.getText().toString();

// 電信業者允許每次傳送的字數有限，我們可以使用Android給我們提供的簡訊工具。

if (message != null) {

SmsManager sms = SmsManager.getDefault();

// 如果簡訊沒有超過限制長度，則傳回一個長度的List。

List<String> texts = sms.divideMessage(message);

for (String text : texts) {

sms.sendTextMessage(mobile, null, text, null, null);

}

}

}

});

}

}
