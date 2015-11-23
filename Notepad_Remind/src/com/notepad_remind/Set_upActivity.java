package com.notepad_remind;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Set_upActivity extends Activity{

	Button setRing;
	public Set_upActivity seta=this;
	public Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	private String Ring_Url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_up_main);
		
		setRing = (Button) findViewById(R.id.setRing);
		
		setRing.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setRingtone();
			}
		});
		
	}
	
	
	
	 private void setRingtone(){
	        Intent intent = new Intent();
	        intent.setAction(RingtoneManager.ACTION_RINGTONE_PICKER);
	        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
	        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹玲铃声");
	        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);
	        
	        Uri pickedUri = RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_ALARM);
	        if (pickedUri!=null) {
	            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,pickedUri);
	            ringUri = pickedUri;
	        }
	        startActivityForResult(intent, 1);
	        
	        
	    }
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (resultCode!=RESULT_OK) {
	            return;
	        }else{
	        	Uri pickedURI = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
	        if(pickedURI!=null)	
	        switch (requestCode) {
	        case 1:
	            //获取选中的铃声的URI
	            
	            Log.i("111",pickedURI.toString());
	            
	            //使用以下需再Manifest加上uses-permission -->WRITE_SETTINGS
	            RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM, pickedURI);
	            SharedPreferences send=getSharedPreferences("PhoneRing",MODE_WORLD_READABLE);
		        send.edit()
				.putString("url",pickedURI.toString())
				.commit();
	            //getName(RingtoneManager.TYPE_ALARM);
	            //break;
	 
	        default:
	            break;
	        }
	      }
	    }
	 private void getName(int type){
	        Uri pickedUri = RingtoneManager.getActualDefaultRingtoneUri(this, type);
	        Log.i("222",pickedUri.toString());
	        Cursor cursor = this.getContentResolver().query(pickedUri, new String[]{MediaStore.Audio.Media.TITLE}, null, null, null);
	        if (cursor!=null) {
	            if (cursor.moveToFirst()) {
	                String ring_name = cursor.getString(0);
	                Log.i("Set_upActivity",ring_name);
	                String[] c = cursor.getColumnNames();
	                for (String string : c) {
	                    Log.i("Set_upActivity",string);
	                }
	            }
	            cursor.close();
	        }
	    }
	
}
