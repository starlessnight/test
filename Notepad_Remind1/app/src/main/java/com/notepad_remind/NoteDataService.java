package com.notepad_remind;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.IBinder;

public class NoteDataService extends Service {

	private Notification baseNS;
	private NotificationManager nm;
	private Handler handler=new Handler();
	private SQLite_set mySql;
	private Cursor  myData;
	private SQLiteDatabase NoteSQL;
	private String _id,Notetitle,Notecontent,Notedate,Notetime,Notekind;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent,int startID){
		
		SharedPreferences send=getSharedPreferences("transport",MODE_WORLD_READABLE);
		_id=send.getString("id",null);
		System.out.println("ididid"+_id);
		
		
		mySql=new SQLite_set(NoteDataService.this,"table_name", null, 0);
		NoteSQL=mySql.getReadableDatabase();
		
		myData=NoteSQL.rawQuery("select note_title,note_content,note_date,note_time,note_kind form table_name where _id=?  ",new String[]{_id});
		while (myData.moveToNext()) {
			Notetitle=myData.getString(0);
			Notecontent = myData.getString(1);
			Notedate = myData.getString(2);
			Notetime = myData.getString(3);
			Notekind=myData.getString(4);
		    }
	}
	
}
