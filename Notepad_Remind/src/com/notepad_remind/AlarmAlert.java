package com.notepad_remind;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class AlarmAlert extends Activity
{
	private String uri,nm,ct,id;
	Uri ringUri;
	private SQLite_set db=new SQLite_set(AlarmAlert.this);
	private SQLiteDatabase AlarmDB;
	private Cursor myCursor;
	private MediaPlayer mp=new MediaPlayer();
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alarm_activity);
    AlarmDB=db.getReadableDatabase();
    SharedPreferences send=getSharedPreferences("PhoneRing",MODE_WORLD_READABLE);
    id=send.getString("Alarm", "1");
    myCursor = AlarmDB.rawQuery(
			"select note_title,note_content from table_name where note_id=?;",
			new String[]{id});
    myCursor.moveToFirst();

	if(myCursor.getCount()>0)
	{
		myCursor.moveToFirst();
		nm = myCursor.getString(0);
		ct = myCursor.getString(1);
	}
    Bundle bn=this.getIntent().getExtras();
    uri=bn.getString("ringURI");
    if (uri!=null) {
        ringUri = Uri.parse(uri);
        System.out.println(uri);
    }
    else
    	uri="content://media/internal/audio/media/17";
    try {
    	 mp.setDataSource(uri);
    	 mp.setLooping(true); 
         mp.prepare();
         
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
    } catch (SecurityException e) {
        e.printStackTrace();
    } catch (IllegalStateException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    mp.start();
    new AlertDialog.Builder(AlarmAlert.this)
        .setIcon(R.drawable.clock)
        .setTitle(nm)
        .setMessage(ct)
        .setPositiveButton("停止",
         new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int whichButton)
          {
        	  mp.stop();
        	  System.out.println(uri);
            AlarmAlert.this.finish();
          }
        })
        .show();
    
    
    
  } 
 /*
  @Override
  protected void onDestroy(){
	  if(mp.isLooping())
	  mp.stop();
  }
  */
}
