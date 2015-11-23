package com.notepad_remind;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class AlarmAlert extends Activity
{
	String uri;
	Uri ringUri;
	private MediaPlayer mp=new MediaPlayer();
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alarm_activity);
    Bundle bn=this.getIntent().getExtras();
    uri=bn.getString("ringURI");
    if (uri!=null) {
        ringUri = Uri.parse(uri);
        System.out.println(uri);
    }
    try {
    	 mp.setDataSource(uri);
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
        .setTitle("000!!")
        .setMessage("0000!!!")
        .setPositiveButton("000000",
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
 
  
  
}
