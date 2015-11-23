package com.alarmtest;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;

public class CallAlarmText extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent)
  {
    Intent i = new Intent(context, AlarmAlert.class);
    Log.d("CallAlarmText-text","日期格式@@@@:000000000000");
    Bundle bundleRet = new Bundle();
    bundleRet.putString("STR_CALLER", "");
    i.putExtras(bundleRet);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);
  }
}
