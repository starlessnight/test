package com.notepad_remind;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class CallAlarmText extends BroadcastReceiver
{
	private Set_upActivity a;
	Uri ringUri;
	private String url;
  @Override
  public void onReceive(Context context, Intent intent)
  {
	  
	  
	  
      //MediaPlayer mp = new MediaPlayer();
      String uri = intent.getStringExtra("ringURI");
      if (uri!=null) {
          ringUri = Uri.parse(uri);
          Log.d("CallActivity", ringUri.toString());
      }
       
      /* try {
          mp.setDataSource(context, ringUri);
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
      mp.start();*/
  
    Intent i = new Intent(context,AlarmAlert.class);
    Log.d("CallAlarmText-text","日期格式@@@@:000000000000");
    Bundle bundleRet = new Bundle();
    bundleRet.putString("STR_CALLER", "");
    bundleRet.putString("ringURI", uri);
    i.putExtras(bundleRet);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);
  }
}
